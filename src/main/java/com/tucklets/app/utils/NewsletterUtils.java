package com.tucklets.app.utils;


import com.tucklets.app.configs.AwsConfig;
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
    public static NewslettersContainer createNewslettersContainer(
            NewsletterService newslettersService, AwsConfig awsConfig)
    {
        return new NewslettersContainer(
                newslettersService
                        .fetchAllAvailableNewsletters()
                        .stream()
                        .sorted(Comparator.comparing(Newsletter::getUploadDate).reversed())
                        .collect(Collectors.toList()));
    }
}
