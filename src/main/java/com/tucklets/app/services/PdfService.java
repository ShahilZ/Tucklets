package com.tucklets.app.services;

import com.google.zxing.WriterException;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.tucklets.app.entities.Child;
import com.tucklets.app.utils.CalculationUtils;
import com.tucklets.app.utils.Constants;
import com.tucklets.app.utils.PdfUtils;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class PdfService {

    /**
     * Generates a PDF containing all active children and their information.
     * Includes their picture, QR code, and information.
     */
    public ByteArrayOutputStream generateActiveChildrenPdf(List<Child> children) throws WriterException, IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        //Initialize writer
        PdfWriter writer = new PdfWriter(byteArrayOutputStream);

        //Initialize document
        PdfDocument pdfDocument = new PdfDocument(writer);
        Document doc = new Document(pdfDocument);

        //Add paragraph to the document
        doc.add(new Paragraph("Hello World!"));

        for (Child child : children) {


            Table table = new Table(2);
            doc.add(new Paragraph(child.getFirstName()));
            doc.add(new Paragraph(child.getLastName()));
            doc.add(new Paragraph(Integer.toString(child.getGrade())));
            doc.add(new Paragraph(Integer.toString(CalculationUtils.calculateAge(child.getBirthYear()))));
            doc.add(new Paragraph(child.getInformation()));

            BufferedImage bufferedImage = PdfUtils.generateQRCode(Constants.CHILD_QR_CODE_BASE.concat(Long.toString(child.getChildId())));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", baos);
            Image iTextImage = new Image(ImageDataFactory.create(baos.toByteArray()));
            doc.add(iTextImage);
        }

        doc.add(new Paragraph("Hello, World!"));

        //Close document
        doc.close();
        return byteArrayOutputStream;
    }
}
