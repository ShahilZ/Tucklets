package com.tucklets.app.services;

import com.tucklets.app.configs.AwsConfig;
import com.tucklets.app.configs.AppConfig;
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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Service
public class EmailService {

    @Autowired
    AwsConfig awsConfig;

    @Autowired
    AppConfig appConfig;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    TemplateEngine templateEngine;

    /**
     * Generic thank you email for donors.
     */
    @Async
    public void sendGenericConfirmationEmail(Sponsor sponsor, Donation donation, String toEmailAddress) {
        MimeMessagePreparator messagePreparator = message -> {
            MimeMessageHelper helper = createGenericConfirmationMessageHelper(message, sponsor, donation);
            helper.setFrom(appConfig.getFromAddressForTransactionalEmails());
            helper.setTo(toEmailAddress);
        };
        javaMailSender.send(messagePreparator);
    }

    @Async
    public void sendConfirmationEmail(Sponsor sponsor, List<Child> children, Donation donation, String toEmailAddress) {
        MimeMessagePreparator messagePreparator = message -> {
            MimeMessageHelper helper = createChildrenConfirmationMessageHelper(message, sponsor, children, donation);
            helper.setTo(toEmailAddress);
            helper.setFrom(appConfig.getFromAddressForTransactionalEmails());

        };
        javaMailSender.send(messagePreparator);
    }

    private String buildThankYouContentWithChildren (Sponsor sponsor, List<Child> children, Donation donation) {
        Context context = new Context();
        context.setVariable("children", children);
        context.setVariable("sponsor", sponsor);
        context.setVariable("donation", donation);
        context.setVariable("localeContainer", ContainerUtils.createLocaleContainer());

        return templateEngine.process("emails/confirmation-email", context);
    }

    /**
     * Generic message for donors.
     */
    private String buildThankYouContent (Sponsor sponsor, Donation donation) {
        Context context = new Context();
        context.setVariable("sponsor", sponsor);
        context.setVariable("donation", donation);
        context.setVariable("localeContainer", ContainerUtils.createLocaleContainer());

        return templateEngine.process("emails/confirmation-email-generic", context);
    }

    /**
     * Sends newsletter email with the newsletter attached.
     */
    public void sendNewsletterEmail(List<Sponsor> sponsors, Newsletter newsletter) {
        String newsletterLink = S3Utils.computeS3Key(newsletter.getFilename(), awsConfig.getS3NewslettersBucketUrl());
        String unsubscribeLink = appConfig.getTuckletsBaseUrl() + "unsubscribe";

        for (Sponsor sponsor : sponsors) {
            MimeMessagePreparator messagePreparator = message -> {
                MimeMessageHelper helper = new MimeMessageHelper(message, true);
                helper.setTo(sponsor.getEmail());
                helper.setTo(appConfig.getFromAddressForTransactionalEmails());
                helper.setSubject("Tucklets - Please check out our new newsletter! ");
                String emailContent = buildNewsletterEmail(sponsor, newsletterLink, unsubscribeLink);
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
    private String buildNewsletterEmail (Sponsor sponsor, String newsletterLink, String unsubscribeLink) {
        Context context = new Context();
        context.setVariable("sponsor", sponsor);
        context.setVariable("newsletterLink", newsletterLink);
        context.setVariable("unsubscribeLink", unsubscribeLink);

        return templateEngine.process("emails/newsletter-email", context);
    }

    /**
     * Creates a message helper for the confirmation email (for sponsoring a child).
     */
    private MimeMessageHelper createChildrenConfirmationMessageHelper(
            MimeMessage message, Sponsor sponsor, List<Child> children, Donation donation)
            throws MessagingException
    {
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setSubject("Tucklets - Thank you for sponsoring a child! ");
        String emailContent = buildThankYouContentWithChildren(sponsor, children, donation);
        helper.setText(emailContent, true);
        return helper;
    }

    /**
     * Creates a message helper for the confirmation email (generic confirmation email).
     */
    private MimeMessageHelper createGenericConfirmationMessageHelper(
            MimeMessage message, Sponsor sponsor, Donation donation)
            throws MessagingException
    {
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setSubject("Tucklets - Thank you for sponsoring a child! ");
        String emailContent = buildThankYouContent(sponsor, donation);
        helper.setText(emailContent, true);
        return helper;
    }
}