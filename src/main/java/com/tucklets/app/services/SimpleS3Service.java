package com.tucklets.app.services;

import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
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

    /**
     * Upload given file to the appropriate bucket in S3.
     * @param fileName   the name of the file to be uploaded
     * @param contents   the file that will be uploaded
     * @param bucketName destination in S3
     */
    public void uploadFile(String fileName, File contents, String bucketName) {
        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder
                .standard()
                // No longer need this line because .standard().build() will automatically try
                // all authentication methods. We don't want just access key access for production.
//                .withCredentials(new AWSStaticCredentialsProvider(createAwsCreds()))
                .build();

            s3Client.putObject(bucketName, fileName, contents);
        } catch (SdkClientException e) {
            // Log error.
            e.printStackTrace();
        }
    }
}
