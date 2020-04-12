package com.tucklets.app.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Misc utils necessary for S3.
 */
public class S3Utils {

    private S3Utils(){};

    public static String S3_BUCKET_BASE_URL = "https://tucklets-images.s3-us-west-1.amazonaws.com/";

    /**
     * Takes the given MultipartFile and converts that to a File.
     */
    public static File convertMultiPartToFile(MultipartFile inputFile) throws IOException {
        File file = new File(inputFile.getOriginalFilename());
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(inputFile.getBytes());
        fileOutputStream.close();
        return file;
    }

    /**
     * Computes the S3 key for the given image location.
     */
    public static String computeS3Key(String imageLocation) {
        return new StringBuilder(S3_BUCKET_BASE_URL).append(imageLocation).toString();
    }
}
