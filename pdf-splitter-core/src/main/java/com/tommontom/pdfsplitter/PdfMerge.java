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

public class PdfMerge {

    /**
     * Merge multiple pdfs into one pdf
     *
     * @param list
     *            of pdf input stream
     * @param outputStream
     *            output file output stream
     * @throws DocumentException
     * @throws IOException
     */
    private void doMerge(List<InputStream> list, OutputStream outputStream) throws DocumentException, IOException {
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

    public File merge(File folder) throws DocumentException, IOException {

        FileNameFilter FileFilter = new FileNameFilter();

        // Stores the listing of the files
        File[] listOfFiles = folder.listFiles(FileFilter);
        String path = folder.getAbsolutePath();
        File outputFile = new File(path + File.separator + "Merge.pdf");

        merge(outputFile, listOfFiles);

        return outputFile;
    }

    public void merge(File outputFile, File[] listOfFiles) throws DocumentException, IOException {
        // Source pdfs
        List<InputStream> list = new ArrayList<InputStream>();
        for (int i = 0; i < listOfFiles.length; i++) {
            list.add(new FileInputStream(listOfFiles[i].getName()));
        }

        // Resulting pdf
        OutputStream out = new FileOutputStream(outputFile);

        doMerge(list, out);
    }

    public File merge(String folderPath) throws DocumentException, IOException {
        File folder = new File(folderPath);

        return merge(folder);
    }
}
