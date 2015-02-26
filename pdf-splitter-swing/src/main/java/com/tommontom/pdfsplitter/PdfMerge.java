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
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.SimpleBookmark;
import java.awt.Toolkit;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;
import static javax.xml.ws.Endpoint.publish;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class PdfMerge extends SwingWorker <String, File> {

    public String newFileListing = "";
    public int barUpdate;
    int n = 0;
    int o = 0;
    int k = 0;
    int l = 0;
    JProgressBar progressBar = new PDFSplitter().progressBar;

    public void doMerge(java.util.List<InputStream> list, String[] filesBook, String[] imageList, OutputStream outputStream)
            throws DocumentException, IOException {
        Document document = new Document(PageSize.LETTER, 0, 0, 0, 0);
        PdfWriter writer = PdfWriter.getInstance(document, outputStream);
        writer.setFullCompression();
        document.open();
        PdfContentByte cb = writer.getDirectContent();
        Image img;
        ArrayList<HashMap<String, Object>> bookmarks = new ArrayList<>();
        List<HashMap<String, Object>> tmp;
        HashMap<String, Object> newTmp = new HashMap<>();

        for (int j = 0; j < list.size(); j++) {
            PdfReader reader = new PdfReader(list.get(j));
            reader.consolidateNamedDestinations();
            tmp = SimpleBookmark.getBookmark(reader);
            if (SimpleBookmark.getBookmark(reader) == null) {
                newTmp.put("Title", filesBook[j]);
                newTmp.put("Action", "GoTo");
                newTmp.put("Page", String.format("%d Fit", o));
                bookmarks.add(newTmp);
                o++;
                System.out.println(o);
                System.out.print(j);

            }
            if (!reader.isEncrypted()) {
                for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                    document.newPage();
                    //import the page from source pdf
                    PdfImportedPage page = writer.getImportedPage(reader, i);
                    //add the page to the destination pdf
                    cb.addTemplate(page, 0, 0);
                }
                if (tmp != null) {
                    bookmarks.addAll(tmp);
                }

                SimpleBookmark.shiftPageNumbers(tmp, n, null);
                n += reader.getNumberOfPages();
            }

        }
        for (int i = 0; i < imageList.length; i++) {
            document.newPage();
            if (imageList[i] != null) {
                img = Image.getInstance(String.format("%s", imageList[i]));
                Rectangle resize = new Rectangle(PageSize.LETTER);
                img.scaleToFit(resize);
                document.add(img);
                newTmp.put("Title", imageList[i]);
                newTmp.put("Action", "GoTo");
                newTmp.put("Page", String.format("%d Fit", n + k));
                bookmarks.add(newTmp);
                k++;
            }

        }
        if (!bookmarks.isEmpty()) {
            writer.setOutlines(bookmarks);
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
        String[] filesBook = new String[listOfFiles.length];
        Arrays.sort(listOfFiles); // Sorts the files according to numeral filenames. (eg: Page 1, pg1, etc.)
        for (int j = 0; j < listOfFiles.length; j++) {
            System.out.println(listOfFiles[j].getName());
        }
        int i = 0;
        try {
            //add the file info to a list with the path and filename in place. then output the information to the doMerge method.
            for (File f : listOfFiles) {
                if (f.getName().toLowerCase().endsWith(".pdf")) {
                    list.add(new FileInputStream(new File("/" + f)));
                    PdfReader encrypt = new PdfReader(f.getPath());
                    if (encrypt.isEncrypted()) {
                        newFileListing += ("File: " + f.getName() + " is encrypted, please decrypt file before splitting" + "\n");
                        return;

                    }
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
                if (f.getName().toLowerCase().endsWith(".jpeg")) {
                    listImages[i] = f.getPath();

                }

                if (f.exists()) {
                    newFileListing += ("Files Merged:" + DEFAULT_PATH + "/" + f + "\n");
                }
                i++;
            }

            String filenameOutput = listOfFiles[0].getName();
            OutputStream out = new FileOutputStream(new File(DEFAULT_PATH + "/" + filenameOutput.substring(0, filenameOutput.lastIndexOf('.')) + "(" + "Merged" + ")" + ".pdf"));
            newFileListing += ("File Made:" + DEFAULT_PATH + "/" + filenameOutput.substring(0, filenameOutput.lastIndexOf('.')) + "(" + "Merged" + ")" + ".pdf" + "\n");
            doMerge(list, filesBook, listImages, out);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PdfMerge.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException | IOException ex) {
            Logger.getLogger(PdfMerge.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getdatacounter() {
        return newFileListing;
    }

    protected int progress() {
        int total = (o + k / n) * 100;
        int progress = 100;
        while (progress < total) {
            //Make random progress.
            setProgress(Math.min(total, 100));
        }
        return progress;
    }

    @Override
    public void done() {
        Toolkit.getDefaultToolkit().beep();
    }

    @Override
    protected String doInBackground() throws Exception {
        int total = (o + k / n) * 100;
        int progress = 100;
        while (progress < total) {
            //Make random progress.
            setProgress(Math.min(total, 100));
        }
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
