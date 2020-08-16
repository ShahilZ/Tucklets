package com.tucklets.app.services;

import com.tucklets.app.entities.Child;
import com.tucklets.app.entities.Donation;
import com.tucklets.app.entities.Newsletter;
import com.tucklets.app.entities.Sponsor;
import com.tucklets.app.utils.ContainerUtils;
import com.tucklets.app.utils.S3Utils;
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

    @Autowired
    SimpleS3Service simpleS3Service;

    public void sendConfirmationEmail(Sponsor sponsor, List<Child> children, Donation donation) {
        // from recipient is defined in application.properties
        MimeMessagePreparator messagePreparator = message -> {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(sponsor.getEmail());
            helper.setSubject("Tucklets - Thank you sponsoring a child! ");
            String emailContent = buildThankYouContent(sponsor, children, donation);
            helper.setText(emailContent, true);

        };
        javaMailSender.send(messagePreparator);
    }

    private String buildThankYouContent (Sponsor sponsor, List<Child> children, Donation donation) {
        Context context = new Context();
        context.setVariable("children", children);
        context.setVariable("sponsor", sponsor);
        context.setVariable("donation", donation);
        context.setVariable("localeContainer", ContainerUtils.createLocaleContainer());

        return templateEngine.process("emails/confirmation-email", context);
    }

    /**
     * Sends newsletter email with the newsletter attached.
     */
    public void sendNewsletterEmail(List<Sponsor> sponsors, Newsletter newsletter) {
        String newsletterLink = S3Utils.computeS3Key(newsletter.getFilename(), S3Utils.S3_NEWSLETTERS_BUCKET_BASE_URL);

        for (Sponsor sponsor : sponsors) {
            MimeMessagePreparator messagePreparator = message -> {
                MimeMessageHelper helper = new MimeMessageHelper(message, true);
                helper.setTo(sponsor.getEmail());
                helper.setSubject("Tucklets - Please check out our new newsletter! ");
                String emailContent = buildNewsletterEmail(sponsor, newsletterLink);
                helper.setText(emailContent, true);
            };
            javaMailSender.send(messagePreparator);
        }

    }

    /**
     * Builds the actual content of the email to send out using the newsletter template.
     * @param sponsor that the email will be sent to.
     * @return htmlString used to the email content.
     */
    private String buildNewsletterEmail (Sponsor sponsor, String newsletterLink) {
        Context context = new Context();
        context.setVariable("sponsor", sponsor);
        context.setVariable("newsletterLink", newsletterLink);

        return templateEngine.process("emails/newsletter-email", context);
    }
}