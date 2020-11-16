package com.tucklets.app.configs;

import com.tucklets.app.security.AdminUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SpringWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Autowired
    private WebApplicationContext applicationContext;

    @Autowired
    private AdminUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder())
            .and()
            .authenticationProvider(authenticationProvider())
            .jdbcAuthentication()
            .dataSource(dataSource)
            .usersByUsernameQuery("SELECT username, encrypted_password, enabled from Admin where username = ?")
            .authoritiesByUsernameQuery("SELECT username, 'ROLE_USER' FROM Admin WHERE username=?");
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        final DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .requiresChannel().anyRequest().requiresSecure()
            .and()
            .authorizeRequests()
            .antMatchers("/", "/css/**", "/js/**", "/images/**", "/**/favicon.ico", "/frontend/dist/**", "/frontend/static/img/**", "/static/img/**").permitAll()
            .antMatchers("/sponsor-a-child/**", "/sponsor-info/**", "/health", "/fetchConfigs", "/info/**", "/thank-you/").permitAll()
            .antMatchers("/admin/**").authenticated()
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginPage("/login").failureUrl("/unauthorized").defaultSuccessUrl("/admin/children-dashboard/")
            .permitAll()
            .and()
            .logout()
            .permitAll()
            .and().csrf().disable();
    }
}
