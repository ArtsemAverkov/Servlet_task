package ru.clevertec.services.pdfWriter;


import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class PdfWriteApiService implements PdfWriteService {

    @Override
    public void writeToPdfFile(List<Object> check) {
        try {
            PdfReader reader = new PdfReader("Clevertec_Template.pdf");
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream("updated_Clevertec_Template.pdf"));
            PdfContentByte canvas = stamper.getUnderContent(1);

            BaseFont font = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            canvas.setFontAndSize(font, 8);
            int yOffset = 500;
            for (Object obj : check) {
                System.out.println("obj = " + obj);
                canvas.beginText();
                canvas.setTextMatrix(20, yOffset);
                canvas.showText(obj.toString());
                canvas.endText();
                yOffset -= 20;
            }
            stamper.close();
            reader.close();

        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
    }
}
