package com.tucklets.app.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

/**
 * Misc utils necessary for S3.
 */
public class S3Utils {

    private S3Utils(){};

    public static String S3_IMAGES_BUCKET_BASE_URL = "https://tucklets-images.s3-us-west-1.amazonaws.com/";
    public static String S3_NEWSLETTERS_BUCKET_BASE_URL = "https://tucklets-newsletters.s3-us-west-1.amazonaws.com/";
    public static String S3_IMAGES_BUCKET_NAME = "tucklets-images";
    public static String S3_NEWSLETTERS_BUCKET_NAME = "tucklets-newsletters";

    /**
     * Takes the given MultipartFile and converts that to a File.
     */
    public static File convertMultiPartToFile(MultipartFile inputFile) throws IOException {
        File file = new File(Objects.requireNonNull(inputFile.getOriginalFilename()));
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(inputFile.getBytes());
        fileOutputStream.close();
        return file;
    }

    /**
     * Computes the S3 key for the given image location.
     */
    public static String computeS3Key(String imageLocation, String baseUrl) {
        return baseUrl + imageLocation;
    }
}
