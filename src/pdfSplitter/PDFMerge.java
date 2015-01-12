/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdfSplitter;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

/**
 *
 * @author tthompson
 */
import java.io.*;
import java.util.*;

public class PDFMerge {

    private static final String DEFAULT_PATH = "C:\\Users\\tthompson\\Pictures\\ControlCenter4\\Scan\\Modified\\Combine";
    
    public static void pdfMerge(String[] args) {
        List<InputStream> list = new ArrayList<InputStream>();
        File folder = new File(DEFAULT_PATH);
        FileNameFilter FileFilter = new FileNameFilter();
        File [] listOfFiles = folder.listFiles(FileFilter); /* Stores the listing of the files */
        try {
            for(int i=0; i <listOfFiles.length;i++){
            list.add(new FileInputStream(new File (DEFAULT_PATH+ "\\" + listOfFiles[i].getName())));
            }
            // Source pdfs

            String path;
            path = DEFAULT_PATH;


            // Resulting pdf
            OutputStream out = new FileOutputStream(new File(path + "\\" + "Merge.pdf"));

            doMerge(list, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Merge multiple pdfs into one pdf
     *
     * @param list of pdf input stream
     * @param outputStream output file output stream
     * @throws DocumentException
     * @throws IOException
     */
    public static void doMerge(List<InputStream> list, OutputStream outputStream)
            throws DocumentException, IOException {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, outputStream);
        document.open();
        PdfContentByte cb = writer.getDirectContent();

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

        outputStream.flush();
        document.close();
        outputStream.close();
    }
}
