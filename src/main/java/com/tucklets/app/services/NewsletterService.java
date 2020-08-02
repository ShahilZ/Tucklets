package com.tucklets.app.services;

import com.tucklets.app.db.repositories.NewsletterRepository;
import com.tucklets.app.entities.Newsletter;
import com.tucklets.app.utils.S3Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class NewsletterService {

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
        return newsletterRepository.fetchAllAvailableeNewsletters();
    }

    /**
     * Uploads the provided file into S3 and stores its meta information in the database.
     */
    public void uploadAndSaveNewsletter(MultipartFile file) throws IOException {
        // Upload file.
        simpleS3Service.uploadFile(
            file.getOriginalFilename(),
            S3Utils.convertMultiPartToFile(file),
            S3Utils.S3_NEWSLETTERS_BUCKET_NAME);

        // Store metadata in db.
        addNewsletter(new Newsletter(file.getOriginalFilename(), new Date()));
    }
}
