/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tommontom.pdfsplitter;

import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import java.lang.*;
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
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Toolkit;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PdfMerge extends Main {

    public String newFileListing;
    public int barUpdate;

    public static void doMerge(java.util.List<InputStream> list, String[] imageList, OutputStream outputStream)
            throws DocumentException, IOException {
        Document document = new Document(PageSize.LETTER,0,0,0,0);
        PdfWriter writer = PdfWriter.getInstance(document, outputStream);
        writer.setFullCompression();
        document.open();
        PdfContentByte cb = writer.getDirectContent();
        Image img;
        for (InputStream in : list) {
            PdfReader reader = new PdfReader(in);
            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                document.newPage();
                //import the page from source pdf
                PdfImportedPage page = writer.getImportedPage(reader, i);
                //add the page to the destination pdf
                cb.addTemplate(page, 0, 0);
            }

        }
        for (int i = 0; i < imageList.length; i++) {
            document.newPage();
            if (imageList[i] != null) {
                img = Image.getInstance(String.format("%s", imageList[i]));
                if (img.getScaledWidth()>img.getScaledHeight()){
                 img.rotate();
                }
                if (img.getScaledWidth() > 792 || img.getScaledHeight() > 792) {
                    img.scaleToFit(792, 792);

                }
                img.setDpi(150, 150);
                document.add(img);
            }

        }

        outputStream.flush();
        document.close();
        outputStream.close();
    }

    public void pdfMerge(File[] files) {
        File newFiles = files[0];   //Takes the name of the first file within the list in the explorer and uses that file name as a base name
        String DEFAULT_PATH = newFiles.getParent();
        //if the directoryfield contains text, then use that field as the save path for combining pdfs.
        System.out.println(DEFAULT_PATH);
        List<java.io.InputStream> list = new ArrayList<>();
        File[] listOfFiles = files; /* Stores the listing of the files */
        String[] listImages = new String[listOfFiles.length];
        Arrays.sort(listOfFiles); // Sorts the files according to numeral filenames. (eg: Page 1, pg1, etc.)
        for (int j = 0; j < listOfFiles.length; j++) {
            System.out.println(listOfFiles[j].getName());
        }
        int i = 0;
        float j = 0;
        try {
            //add the file info to a list with the path and filename in place. then output the information to the doMerge method.
            for (File f : listOfFiles) {
                if (f.getName().toLowerCase().endsWith(".pdf")) {
                    list.add(new FileInputStream(new File("/" + f)));
                }

                if (f.getName().toLowerCase().endsWith(".jpg")) {
                    listImages[i] = f.getPath();

                }
                if (f.getName().toLowerCase().endsWith(".gif")) {
                    listImages[i] = f.getPath();

                }
                if (f.getName().toLowerCase().endsWith(".png")) {
                    listImages[i] = f.getPath();

                }
                if (f.getName().toLowerCase().endsWith(".tif")) {
                    listImages[i] = f.getPath();

                }

                if (listOfFiles.length > 0) {
                    newFileListing += ("Files Merged:" + DEFAULT_PATH + "/" + f + "\n");
                }
                barUpdate = (int) (((j / listOfFiles.length) * 100));
                j++;
                i++;
                System.out.println(barUpdate);
                progressBar.setValue(barUpdate);

            }
            OutputStream out = new FileOutputStream(new File(DEFAULT_PATH + "/" + listOfFiles[0].getName() + ".pdf"));
            newFileListing += ("File Made:" + DEFAULT_PATH + "/" + listOfFiles[0].getName() + ".pdf" + "\n");
            doMerge(list, listImages, out);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PdfMerge.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException | IOException ex) {
            Logger.getLogger(PdfMerge.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getdatacounter() {
        return newFileListing;
    }
}
