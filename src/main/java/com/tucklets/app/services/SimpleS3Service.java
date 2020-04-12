package com.tucklets.app.services;

import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class SimpleS3Service {

    /**
     * Create AWS credentials.
     */
    private AWSCredentials createAwsCreds() {
        return new BasicAWSCredentials(
            System.getenv("AWS_ACCESS_KEY"),
            System.getenv("AWS_SECRET_KEY")
        );
    }

    public void uploadFile(String fileName, File contents) {
        String bucketName = System.getenv("S3_BUCKET_NAME");

        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(createAwsCreds()))
                .withRegion(Regions.US_WEST_1)
                .build();

            s3Client.putObject(bucketName, fileName, contents);
        } catch (SdkClientException e) {
            // Log error.
            e.printStackTrace();
        }
    }
}
