package com.tucklets.app.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
     * Computes the S3 key for the given file location.
     */
    public static String computeS3Key(String fileLocation, String baseUrl) {
        return baseUrl + fileLocation;
    }
}
