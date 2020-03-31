package com.tucklets.app.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SpringWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .authorizeRequests()
            .antMatchers("/css/**", "/js/**", "/main").permitAll()
            .antMatchers("/sponsor-a-child/", "sponsor-info/").permitAll()
            .antMatchers(("/admin/**")).authenticated()
            .and()
            .formLogin()
            .loginPage("/login").failureUrl("/unauthorized")
            .permitAll()
            .and()
            .logout()
            .permitAll()
            .and().csrf().disable();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
        return passwordEncoder;
    }





}
