/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tommontom.pdfsplitter;

import java.io.File;
import java.io.FileOutputStream;
/**
 *
 * @author tthompson
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.SimpleBookmark;
import java.beans.PropertyChangeEvent;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

public class PdfMerge extends SwingWorker<Integer, Integer> {

    public String newFileListing = "";
    //Counters for various operations

    private final ArrayList<HashMap<String, Object>> bookmarks = new ArrayList<>(); //an array list is instantiated for bookmarks to be saved
    File[] files;
    int counterDir;
    int over;
    public PdfMerge(File[] files, int counterDir, int over) {
        this.files = files;
        this.counterDir = counterDir;
        this.over = over;
    }

    protected Integer doInBackground() throws Exception {
        pdfMerge(files, counterDir);
        return null;
    }

    protected void done() {

    }

    public void pdfMerge(File[] files, int counterDir) throws InterruptedException {
        File newFiles = files[0];
        String DEFAULT_PATH = newFiles.getParent();
        File[] listOfFiles = files;
        String[] listImages = new String[listOfFiles.length];
        String[] filesBook = new String[listOfFiles.length];

        Arrays.sort(listOfFiles); // Sorts the files according to numeral filenames. (eg: Page 1, pg1, etc.)
        int i = 0;
        try {
            //add the file info to a list with the path and filename in place, then outputs the information to the doMerge method.
            for (File f : listOfFiles) {

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
                if (f.getName().toLowerCase().endsWith(".tiff")) {
                    listImages[i] = f.getPath();

                }
                if (f.getName().toLowerCase().endsWith(".jpeg")) {
                    listImages[i] = f.getPath();

                }

                if (f.exists() && !f.getName().startsWith(".", 0)) {
                    newFileListing += ("Files Merged:" + DEFAULT_PATH + "/" + f + "\n");
                }
                i++;
            }

            String filenameOutput = listOfFiles[0].getName();
            newFileListing += ("File Made:" + DEFAULT_PATH + "/" + "(" + "Merged PDFs" + ")" +over+ ".pdf" + "\n");
            doMerge(files, filesBook, listImages, counterDir);

        } catch (DocumentException ex) {
            Logger.getLogger(PdfMerge.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PdfMerge.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void doMerge(File[] files, String[] filesBook, String[] imageList, int counterDir)
            throws DocumentException, IOException, InterruptedException {
        int n = 0;
        int o = 0;
        int counter = 0;
        int pdfcounter = 0;
        Document document = new Document(PageSize.LETTER, 0, 0, 0, 0);
        for (int j = 0; j < files.length; j++) {
            int processed = ((j * 100) / files.length);
            setProgress(processed);
            if (files[j].getName().toLowerCase().endsWith(".pdf")) {
                pdfcounter++;
            }
        }

        PdfCopy copy = null; //instantiation of a pdf object is made as the copy version
        if (pdfcounter > 0) {
            if (counterDir == 0) {
                copy = new PdfCopy(document, new FileOutputStream(files[0].getParent() + "/" + "(" + "Merged PDFs" + ")" + over + ".pdf"));

            } else if (counterDir > 0) {
                String basePath = files[0].getPath().toString().substring(0, files[0].getPath().toString().lastIndexOf("/"));
                int i = basePath.lastIndexOf('/');
                if (i > 0) {
                    i = basePath.lastIndexOf('/', i);
                }
                String tail = (i >= 0) ? basePath.substring(0, i + 1) : null;
                copy = new PdfCopy(document, new FileOutputStream(tail + "/" + "(" + "Merged PDFs" + ")"+ over + ".pdf"));
            }
            // a counter is made to detect if a pdf exists in the file list.
        }
        Image img; // object for images to be merged
        List<HashMap<String, Object>> tmp; //for generated bookmarks a temporary hashtable object is made
        document.open();

        for (int j = 0; j < files.length; j++) {
            if (files[j].getName().toLowerCase().endsWith(".pdf")) {
                counter++;
                PdfReader encrypt = new PdfReader(files[j].getPath());
                if (encrypt.isEncrypted()) {
                    newFileListing += ("File: " + files[j].getName() + " is encrypted, please decrypt file before splitting" + "\n");
                    return;

                }
                PdfReader reader = new PdfReader(files[j].getPath());
                reader.consolidateNamedDestinations();
                tmp = SimpleBookmark.getBookmark(reader);
                if (SimpleBookmark.getBookmark(reader) == null) {
                    HashMap<String, Object> newTmp = new HashMap<>(); //to generate bookmarks from images, another object is made
                    newTmp.put("Title", filesBook[j]);
                    newTmp.put("Action", "GoTo");
                    newTmp.put("Page", String.format("%d Fit", o++));
                    bookmarks.add(newTmp);

                }
                if (!reader.isEncrypted()) {
                    copy.addDocument(reader);
                    if (tmp != null) {
                        bookmarks.addAll(tmp);
                    }
                    SimpleBookmark.shiftPageNumbers(tmp, n, null);
                    n += reader.getNumberOfPages();
                }

            }
        }
        OutputStream out = null;
        Document documentimg = null;
        PdfWriter imageWriter = null;
        if (pdfcounter == 0) {
            out = new FileOutputStream(new File(imageList[0] + "(" + "Merged" + ")" + ".pdf"));
            documentimg = new Document(PageSize.LETTER);
            documentimg.setMargins(0, 0, 0, 0);
            imageWriter = PdfWriter.getInstance(documentimg, out);
        }
        /*Go through the list of images. If the filelist contains only images then copy those images 
         to a new pdf document. If the file list contains both images and pdf documents then copy
         to memory and place into the merged pdf file at the end of the pdf.
         */
        for (int i = 0; i < imageList.length; i++) {

            if (imageList[i] != null) {
                File tmpImg = new File(imageList[i]);
                Path path = tmpImg.toPath();
                byte[] data = Files.readAllBytes(path);
                img = Image.getInstance(data);
                if (img.getPlainHeight() < img.getPlainWidth()) {
                    img.setRotationDegrees(-90);
                }
                Rectangle resize = new Rectangle(PageSize.LETTER);
                img.scaleToFit(resize);
                if (counter == 0) {
                    documentimg.open();
                    documentimg.newPage();
                    documentimg.add(img);
                    documentimg.newPage();
                }
                if (counter > 0) {
                    Document imageDocument = new Document(PageSize.LETTER);
                    imageDocument.setMargins(0, 0, 0, 0);
                    ByteArrayOutputStream imageDocumentOutputStream = new ByteArrayOutputStream();
                    PdfWriter imageDocumentWriter = PdfWriter.getInstance(imageDocument, imageDocumentOutputStream);
                    imageDocument.open();
                    imageDocument.newPage();
                    imageDocument.add(img);
                    imageDocument.close();
                    PdfReader imageDocumentReader = new PdfReader(imageDocumentOutputStream.toByteArray());
                    imageDocumentWriter.close();
                    copy.addPage(copy.getImportedPage(imageDocumentReader, 1));
                    imageDocumentReader.close();

                }

            }

        }
        bookmarkGen(imageList, n);
        if (!bookmarks.isEmpty() && pdfcounter > 0) {
            copy.setOutlines(bookmarks);

        } else {
            imageWriter.setOutlines(bookmarks);
        }

        if (counter > 0) {
            document.close();
        } else if (counter == 0) {
            documentimg.close();
        }
        setProgress(100);
        setProgress(0);
    }

    private void bookmarkGen(String[] imageList, int n) {
        

        for (int i = 0; i < imageList.length; i++) {
            System.out.println("Trigger Bookmark Images\n");
            if (imageList[i] != null) {
                if (n==0){
                    n=1;
                }
                HashMap<String, Object> newTmp = new HashMap<>(); //to generate bookmarks from images, another object is made
                newTmp.put("Title", imageList[i].substring(imageList[i].lastIndexOf("/"), imageList[i].lastIndexOf(".")));
                newTmp.put("Action", "GoTo");
                newTmp.put("Page", String.format("%d Fit", n++));
                bookmarks.add(newTmp);
                System.out.println("N equals:" + n);
                System.out.println("HashMap ="+newTmp);
            }
            
        }

    }
}
