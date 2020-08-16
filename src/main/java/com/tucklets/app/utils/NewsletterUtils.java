package com.tucklets.app.utils;


import com.tucklets.app.containers.NewslettersContainer;
import com.tucklets.app.entities.Newsletter;
import com.tucklets.app.services.NewsletterService;

import java.util.Comparator;
import java.util.stream.Collectors;

public class NewsletterUtils {

    private NewsletterUtils(){};

    /**
     * Creates a container containing a list of all available newsletters.
     */
    public static NewslettersContainer createNewslettersContainer(NewsletterService newslettersService) {
        return new NewslettersContainer(
            newslettersService
                .fetchAllAvailableNewsletters()
                .stream()
                .map(NewsletterUtils::determineNewsletterLocation)
                .sorted(Comparator.comparing(Newsletter::getUploadDate).reversed())
                .collect(Collectors.toList()));
    }

    /**
     * Given a newsletter, return the same newsletter with the newsletterLocation field set.
     * This method will determine where the actual location of the newsletter is.
     */
    private static Newsletter determineNewsletterLocation(Newsletter newsletter) {
        newsletter.setNewsletterLocation(
            S3Utils.computeS3Key(newsletter.getFilename(), S3Utils.S3_NEWSLETTERS_BUCKET_BASE_URL));
        return newsletter;
    }
}
