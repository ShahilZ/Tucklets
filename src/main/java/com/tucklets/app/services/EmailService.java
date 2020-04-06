package com.tucklets.app.services;

import com.tucklets.app.entities.Child;
import com.tucklets.app.entities.Sponsor;
import com.tucklets.app.utils.ContainerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import java.util.List;

@Service
public class EmailService {

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    TemplateEngine templateEngine;

    public void sendConfirmationEmail(Sponsor sponsor, List<Child> children) {
        // from recipent is defined in application.properties
        MimeMessagePreparator messagePreparator = message -> {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(sponsor.getEmail());
            helper.setSubject("Tucklets - Thank you sponsoring a child! ");
            String emailContent = buildThankYouContent(sponsor, children);
            helper.setText(emailContent, true);

        };
        javaMailSender.send(messagePreparator);
    }

    private String buildThankYouContent (Sponsor sponsor, List<Child> children) {
        Context context = new Context();
        context.setVariable("children", children);
        context.setVariable("sponsor", sponsor);
        context.setVariable("localeContainer", ContainerUtils.createLocaleContainer());

        return templateEngine.process("confirmation-email", context);
    }
}