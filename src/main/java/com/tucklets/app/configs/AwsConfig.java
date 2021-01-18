package com.tucklets.app.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsConfig {

    private final String s3BucketName;
    private final String s3ImagesBucketUrl;
    private final String s3NewslettersBucketUrl;

    /**
     * Centralize AWS properties here from application-{profile}.properties
     * so they can be used regardless of the active profile.
     */
    public AwsConfig(
            @Value("${aws.s3.bucket.name}") String s3BucketName,
            @Value("${aws.s3.bucket.url.images}") String s3ImageBucketUrl,
            @Value("${aws.s3.bucket.url.newsletters}") String s3NewslettersBucketUrl)
    {
        this.s3BucketName = s3BucketName;
        this.s3ImagesBucketUrl = s3ImageBucketUrl;
        this.s3NewslettersBucketUrl = s3NewslettersBucketUrl;
    }

    public String getS3ImagesBucketUrl() {
        return s3ImagesBucketUrl;
    }

    public String getS3BucketName() {
        return s3BucketName;
    }

    public String getS3NewslettersBucketUrl() {
        return s3NewslettersBucketUrl;
    }
}
