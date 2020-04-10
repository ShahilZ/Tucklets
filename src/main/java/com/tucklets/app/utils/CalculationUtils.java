package com.tucklets.app.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;

public class CalculationUtils {
    private CalculationUtils(){}

    /**
     * Determines the age given a (birth) year.
     */
    public static int calculateAge(int year) {
        return LocalDate.now().getYear() - year;
    }

    /**
     * Generates the QR Code for the given string.
     */
    public static BufferedImage generateQRCode(String qrText) throws WriterException, IOException {
        QRCodeWriter qrWriter = new QRCodeWriter();
        BitMatrix bitMatrix =
            qrWriter.encode(qrText, BarcodeFormat.QR_CODE, 100, 100);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
        return MatrixToImageWriter.toBufferedImage(bitMatrix);
//        return outputStream;
    }
}
