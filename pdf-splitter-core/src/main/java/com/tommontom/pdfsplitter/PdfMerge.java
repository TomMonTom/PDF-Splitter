/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tommontom.pdfsplitter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
/**
 *
 * @author tthompson
 */
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import java.util.Arrays;

public class PdfMerge extends WindowMain{

     public void pdfMerge(File[] files) throws DocumentException, IOException {
        File newFiles = files[0]; // Takes the name of the first file within the list in the explorer and uses that file name as a base name
        String DEFAULT_PATH = newFiles.getParent();
        // if the directoryfield contains text, then use that field as the save path for combining pdfs.
        if (directoryField.getText() != null) {
            DEFAULT_PATH = directoryField.getText();
        }
        System.out.println(DEFAULT_PATH);

        // Sorts the files according to numeral filenames. (eg: Page 1, pg1, etc.)
        Arrays.sort(files);

    }
         public static void doMerge(java.util.List<InputStream> list, OutputStream outputStream) throws DocumentException, IOException {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, outputStream);
        document.open();
        PdfContentByte cb = writer.getDirectContent();
        for (InputStream in : list) {
            PdfReader reader = new PdfReader(in);
            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                document.newPage();
                // import the page from source pdf
                PdfImportedPage page = writer.getImportedPage(reader, i);
                // add the page to the destination pdf
                cb.addTemplate(page, 0, 0);
            }
        }

        outputStream.flush();
        document.close();
        outputStream.close();
    }
}
