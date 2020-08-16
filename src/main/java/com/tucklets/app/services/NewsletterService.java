package com.tucklets.app.services;

import com.tucklets.app.containers.admin.NewsletterStatusContainer;
import com.tucklets.app.containers.admin.NewsletterStatusContainer.NewsletterStatus;
import com.tucklets.app.db.repositories.NewsletterRepository;
import com.tucklets.app.entities.Newsletter;
import com.tucklets.app.entities.Sponsor;
import com.tucklets.app.utils.S3Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class NewsletterService {

    @Autowired
    EmailService emailService;

    @Autowired
    NewsletterRepository newsletterRepository;

    @Autowired
    SimpleS3Service simpleS3Service;

    /**
     * Adds a newsletter to the database.
     */
    public Newsletter addNewsletter(Newsletter newsletter) { return newsletterRepository.save(newsletter); }

    /**
     * Returns all available newsletters.
     */
    public List<Newsletter> fetchAllAvailableNewsletters() {
        return newsletterRepository.fetchAllAvailableNewsletters();
    }

    /**
     * Uploads the provided file into S3 and stores its meta information in the database.
     */
    public void uploadAndSaveNewsletter(MultipartFile file) throws IOException {
        // Check if file already exists. If it does, do nothing.
        String filename = file.getOriginalFilename();
        Optional<Newsletter> newsletterOptional = newsletterRepository.fetchNewsletterByFilename(filename);
        if (newsletterOptional.isPresent()) {
           // Found a newsletter with the same name.
            return;
        }
        // Upload file.
        simpleS3Service.uploadFile(
            filename,
            S3Utils.convertMultiPartToFile(file),
            S3Utils.S3_NEWSLETTERS_BUCKET_NAME);

        // Store metadata in db.
        addNewsletter(new Newsletter(file.getOriginalFilename(), new Date()));
    }

    /**
     * Hard removal of the given newsletter.
     */
    public void deleteNewsletter(long newsletterId) throws IllegalStateException {
        Optional<Newsletter> newsletterOptional = newsletterRepository.fetchNewslettersByNewsletterId(newsletterId);
        // If newsletter is valid/exists
        if (newsletterOptional.isPresent()) {
            Newsletter newsletter = newsletterOptional.get();
            String newsletterLocation = newsletter.getFilename();
            simpleS3Service.deleteFile(newsletterLocation, S3Utils.S3_NEWSLETTERS_BUCKET_NAME);
            newsletterRepository.delete(newsletter);
        }
        else {
            throw new IllegalStateException(
                String.format("Newsletter: %d could not be deleted because it does not exist.", newsletterId));
        }
    }

    /**
     * Sends email to all the provided addresses containing the newsletter that should be shared.
     */
    public NewsletterStatusContainer emailLatestNewsLetter(List<Sponsor> sponsors)
        throws IllegalStateException, MessagingException, IOException
    {
        Optional<Newsletter> newsletterOpt = newsletterRepository.findFirstByOrderByUploadDateDesc();
        if (newsletterOpt.isEmpty()) {
            return NewsletterStatusContainer.createErrorNewsletterStatusContainer("Cannot locate latest newsletter.");
        }
        Newsletter newsletter = newsletterOpt.get();
        emailService.sendNewsletterEmail(sponsors, newsletter);
        return new NewsletterStatusContainer(NewsletterStatus.SUCCESS, newsletter.getFilename());
    }

    /**
     * Sends email to all the provided addresses containing the newsletter that should be shared.
     */
    public void emailNewsLetter(long newsletterId, List<Sponsor> sponsors)
        throws IllegalStateException, MessagingException, IOException
    {
        Optional<Newsletter> newsletterOpt = newsletterRepository.fetchNewslettersByNewsletterId(newsletterId);
        if (newsletterOpt.isEmpty()) {
            throw new IllegalStateException(String.format("Cannot email out newsletter: %d", newsletterId));
        }
        Newsletter newsletter = newsletterOpt.get();
        emailService.sendNewsletterEmail(sponsors, newsletter);
    }
}
