package com.tucklets.app.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsConfig {

    private final String s3ImagesBucketName;
    private final String s3ImagesBucketUrl;
    private final String s3NewslettersBucketName;
    private final String s3NewslettersBucketUrl;

    /**
     * Centralize AWS properties here from application-{profile}.properties
     * so they can be used regardless of the active profile.
     */
    public AwsConfig(
            @Value("${aws.s3.bucket.name.images}") String s3ImageBucketName,
            @Value("${aws.s3.bucket.url.images}") String s3ImageBucketUrl,
            @Value("${aws.s3.bucket.name.newsletters}") String s3NewslettersBucketName,
            @Value("${aws.s3.bucket.url.newsletters}") String s3NewslettersBucketUrl)
    {
        this.s3ImagesBucketName = s3ImageBucketName;
        this.s3ImagesBucketUrl = s3ImageBucketUrl;
        this.s3NewslettersBucketName = s3NewslettersBucketName;
        this.s3NewslettersBucketUrl = s3NewslettersBucketUrl;
    }

    public String getS3ImagesBucketName() {
        return s3ImagesBucketName;
    }

    public String getS3ImagesBucketUrl() {
        return s3ImagesBucketUrl;
    }

    public String getS3NewslettersBucketName() {
        return s3NewslettersBucketName;
    }

    public String getS3NewslettersBucketUrl() {
        return s3NewslettersBucketUrl;
    }
}
