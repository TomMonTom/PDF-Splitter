package com.tommontom.pdfsplitter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;
import java.awt.Toolkit;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import java.nio.file.Path;
import javax.swing.SwingWorker;

/**
 *
 * @author tthompson
 */
public class PdfSplit extends SwingWorker<Integer, Integer> {

    public Path deleteFilesPath;
    public String newFileListing;
    int j = 0;
    String string;
    int copyNum;
    boolean drop;
    boolean copyCheck;
    File[] files;
    public PdfSplit(String string, File[] files,int copyNum, boolean drop, boolean copyCheck) {
        this.copyNum = copyNum;
        this.string = string;
        this.files = files;
        this.drop = drop;
        this.copyCheck = copyCheck;
    }

    public void pdfSplit(String path) throws IOException, DocumentException, InterruptedException {
        // TODO Instead of hard code path, pass in as argument
        File folder = new File(path);
        FileNameFilter FileFilter = new FileNameFilter();
        File[] listOfFiles = folder.listFiles(FileFilter); /* Stores the listing of the files */

        for (int i = 0; i < listOfFiles.length; i++) {
            File file = listOfFiles[i];
            if (!file.isFile()) {
                continue;
            }

            // Split the source filename into its 2 parts
            String fileName = file.getName();
            String fileNameWithoutExt = fileName.substring(0, fileName.lastIndexOf('.'));
            PdfReader pdfFileReader = new PdfReader(file.getPath());
            Document document = new Document(PageSize.LETTER, 0, 0, 0, 0); /* instantiates a new document to be made */

            int numPages = pdfFileReader.getNumberOfPages();
            // Split on a space '\s'
            // Determine number of pages by difference of lot numbers
            // Read in the source document
            // Example file name: 16034-212234 16034-212236.pdf > 16034-212234.pdf, 16034-212235.pdf, 16034-212236.pdf
            // Create a copy of the orignal source file. We will pick specific pages out below
            document.open();
            if (!pdfFileReader.isEncrypted()) {
                File dir = new File(listOfFiles[i].getAbsolutePath() + listOfFiles[i].getName());
                dir.mkdir();
                for (int j = 1; j < numPages + 1; j++) {
                    String FileName = (fileNameWithoutExt); /* Dynamic file name */

                    int prog = (j / numPages) * 100;
                    setProgress(prog);
                    document = new Document(PageSize.LETTER, 0, 0, 0, 0);
                    PdfCopy copy = new PdfCopy(document, new FileOutputStream(dir + "/" + FileName + "(" + j + ")" + ".pdf"));
                    document.open();
                    copy.addPage(copy.getImportedPage(pdfFileReader, j)); /* Import pages from original document */

                    if (j == 1) {
                        newFileListing = ("Created File:" + path + FileName + "(" + j + ")" + ".pdf" + "\n");
                    } else if (j > 1) {
                        newFileListing += ("Created File:" + path + FileName + "(" + j + ")" + ".pdf" + "\n");
                    }
                    document.close();

                }
            } else {
                newFileListing = ("File:" + fileNameWithoutExt + "is encrypted, please decrypt file before splitting" + "\n");

                continue;
            }
            pdfFileReader.close();
        }
    }

    public void pdfSplitCopy(String path, int copyNum) throws IOException, DocumentException {
        // TODO Instead of hard code path, pass in as argument
        File folder = new File(path);
        FileNameFilter FileFilter = new FileNameFilter();
        File[] listOfFiles = folder.listFiles(FileFilter); /* Stores the listing of the files */

        for (int i = 0; i < listOfFiles.length; i++) {
            File file = listOfFiles[i];
            if (!file.isFile()) {
                continue;
            }

            // Split the source filename into its 2 parts
            String fileName = file.getName();
            String fileNameWithoutExt = fileName.substring(0, fileName.lastIndexOf('.'));
            PdfReader pdfFileReader = new PdfReader(file.getPath());
            Document document = new Document(PageSize.LETTER, 0, 0, 0, 0); /* instantiates a new document to be made */

            int constant = 1;
            // Split on a space '\s'
            // Determine number of pages by difference of lot numbers
            // Read in the source document
            // Example file name: 16034-212234 16034-212236.pdf > 16034-212234.pdf, 16034-212235.pdf, 16034-212236.pdf
            // Create a copy of the orignal source file. We will pick specific pages out below
            document.open();
            if (!pdfFileReader.isEncrypted()) {
                File dir = new File(listOfFiles[i].getAbsolutePath() + listOfFiles[i].getName());
                dir.mkdir();
                for (int j = 0; j < copyNum + 1; j++) {
                    String FileName = (fileNameWithoutExt); /* Dynamic file name */

                    int prog = (j / copyNum) * 100;
                    setProgress(prog);
                    document = new Document(PageSize.LETTER, 0, 0, 0, 0);
                    PdfCopy copy = new PdfCopy(document, new FileOutputStream(dir + "/" + FileName + "(" + j + 1 + ")" + ".pdf"));
                    document.open();

                    copy.addPage(copy.getImportedPage(pdfFileReader, constant)); /* Import pages from original document */

                    if (j == 1) {
                        newFileListing = ("Created File:" + path + FileName + "(" + j + ")" + ".pdf" + "\n");
                    } else if (j > 1) {
                        newFileListing += ("Created File:" + path + FileName + "(" + j + ")" + ".pdf" + "\n");
                    }
                    document.close();
                }
            } else {
                newFileListing = ("File:" + fileNameWithoutExt + "is encrypted, please decrypt file before splitting" + "\n");
                continue;
            }

            pdfFileReader.close();
        }

    }

    public void pdfSplitDrop(File[] files, String path) throws IOException, DocumentException, InterruptedException {
        // TODO Instead of hard code path, pass in as argument
        File[] listOfFiles = files; /* Stores the listing of the files */

        for (int i = 0; i < listOfFiles.length; i++) {
            File file = listOfFiles[i];
            if (!file.isFile()) {
                continue;
            }
            // Split the source filename into its 2 parts
            String fileName = file.getName();
            String fileNameWithoutExt = fileName.substring(0, fileName.lastIndexOf('.'));

            PdfReader pdfFileReader = new PdfReader(file.getPath());
            Document document = new Document(PageSize.LETTER, 0, 0, 0, 0); /* instantiates a new document to be made */

            int numPages = pdfFileReader.getNumberOfPages();
            // Determine number of pages by difference of lot numbers
            // Read in the source document
            // Example file name: 16034-212234 16034-212236.pdf > 16034-212234.pdf, 16034-212235.pdf, 16034-212236.pdf
            // Create a copy of the orignal source file. We will pick specific pages out below
            document.open();
            if (!pdfFileReader.isEncrypted()) {
                File dir = new File(path + fileNameWithoutExt);
                dir.mkdir();
                for (int j = 1; j < numPages + 1; j++) {
                    String FileName = fileNameWithoutExt; /* Dynamic file name */

                    int prog = (j / numPages) * 100;
                    setProgress(prog);
                    document = new Document(PageSize.LETTER, 0, 0, 0, 0);
                    PdfCopy copy = new PdfCopy(document, new FileOutputStream(dir + "/" + FileName + "(" + j + ")" + ".pdf"));
                    document.open();
                    copy.addPage(copy.getImportedPage(pdfFileReader, j)); /* Import pages from original document */

                    if (j == 1) {
                        newFileListing = ("Created File:" + dir + "(" + j + ")" + ".pdf" + "\n");
                    } else if (j > 1) {
                        newFileListing += ("Created File:" + dir + "(" + j + ")" + ".pdf" + "\n");
                    }
                    document.close();
                }
            } else {
                newFileListing = ("File:" + fileNameWithoutExt + "is encrypted, please decrypt file before splitting" + "\n");
                continue;
            }
            pdfFileReader.close();
            
        }
        drop = false;
    }

    public void pdfSplitDropCopy(File[] files, String path, int copyNum) throws IOException, DocumentException {
        // TODO Instead of hard code path, pass in as argument

        File[] listOfFiles = files; /* Stores the listing of the files */

        if ("".equals(path)) {
            path = files[0].getPath();
        }
        for (int i = 0; i < listOfFiles.length; i++) {
            File file = listOfFiles[i];
            if (!file.isFile()) {
                continue;
            }
            // Split the source filename into its 2 parts
            String fileName = file.getName();
            String fileNameWithoutExt = fileName.substring(0, fileName.lastIndexOf('.'));
            PdfReader pdfFileReader = new PdfReader(file.getPath());
            Document document; /* instantiates a new document to be made */


            int constant = 1;
            // Determine number of pages by difference of lot numbers
            // Read in the source document
            // Example file name: 16034-212234 16034-212236.pdf > 16034-212234.pdf, 16034-212235.pdf, 16034-212236.pdf
            // Create a copy of the orignal source file. We will pick specific pages out below
            if (!pdfFileReader.isEncrypted()) {
                for (j = 0; j < copyNum + 1; j++) {
                    File dir = new File(path + "/" + listOfFiles[i].getName());
                    dir.mkdir();
                    String FileName = (fileNameWithoutExt); /* Dynamic file name */

                    int prog = (j / copyNum) * 100;
                    setProgress(prog);
                    document = new Document(PageSize.LETTER, 0, 0, 0, 0);
                    PdfCopy copy = new PdfCopy(document, new FileOutputStream(dir + "(" + j + 1 + ")" + ".pdf"));
                    document.open();
                    copy.addPage(copy.getImportedPage(pdfFileReader, constant)); /* Import pages from original document */

                    if (j == 1) {
                        newFileListing = ("Created File:" + dir + "(" + j + ")" + ".pdf" + "\n");
                    } else if (j > 1) {
                        newFileListing += ("Created File:" + dir + "(" + j + ")" + ".pdf" + "\n");
                    }
                    document.close();
                }
            } else {
                newFileListing = ("File:" + fileNameWithoutExt + "is encrypted, please decrypt file before splitting" + "\n");
                continue;
            }

            pdfFileReader.close();
        }
    }

    public void pdfEven(String path) throws IOException, DocumentException {
        File folder = new File(path);
        FileNameFilter FileFilter = new FileNameFilter();
        File[] listOfFiles = folder.listFiles(FileFilter); /* Stores the listing of the files */

        for (int i = 0; i < listOfFiles.length; i++) {
            File file = listOfFiles[i];
            if (!file.isFile()) {
                continue;
            }

            // Split the source filename into its 2 parts
            String fileName = file.getName();
            String fileNameWithoutExt = fileName.substring(0, fileName.lastIndexOf('.'));

            // Split on a space '\s'
            String[] fileNames = fileNameWithoutExt.split("\\s");
            if (fileNames.length != 2) {
                throw new RuntimeException("File name format is not in right format");
            }
            // Project num is always the 1st part
            // Strip off the first and second lot number, parse into integers
            // Determine number of pages by difference of lot numbers
            // Read in the source document
            // Example file name: 16034-212234 16034-212236.pdf > 16034-212234.pdf, 16034-212235.pdf, 16034-212236.pdf
            PdfReader pdfFileReader = new PdfReader(file.getPath());
            int mod = pdfFileReader.getNumberOfPages() % 2;
            if (pdfFileReader.equals(mod)) {
                throw new RuntimeException("File is not an even number of pages");
            }
            Document document = new Document(PageSize.LETTER, 0, 0, 0, 0); /* instantiates a new document to be made */

            int numPages = pdfFileReader.getNumberOfPages();
            int p = 0;
            int j = 1;
            // Create a copy of the orignal source file. We will pick specific pages out below
            document.open();
            if (!pdfFileReader.isEncrypted()) {
                File dir = new File(listOfFiles[i].getAbsolutePath() + listOfFiles[i].getName());
                dir.mkdir();
                while (j < numPages) {
                    int prog = (j / numPages) * 100;
                    setProgress(prog);
                    j++;
                    if (j % 2 == 1) {
                        j += 1;
                    }
                    String FileName = fileNameWithoutExt; /* Dynamic file name */

                    document = new Document(PageSize.LETTER, 0, 0, 0, 0);
                    PdfCopy copy = new PdfCopy(document, new FileOutputStream(dir + "(" + j + 1 + ")" + ".pdf"));
                    if (j == 1) {
                        newFileListing = ("Created File:" + dir + "//" + FileName + "\n");
                    } else if (j > 1) {
                        newFileListing += ("Created File:" + dir + "//" + FileName + "\n");
                    }
                    document.open();
                    p += 2;
                    copy.addPage(copy.getImportedPage(pdfFileReader, j)); /* Import pages from original document */

                    copy.addPage(copy.getImportedPage(pdfFileReader, p));
                    document.close();

                }
            } else {
                newFileListing = ("File:" + fileNameWithoutExt + "is encrypted, please decrypt file before splitting" + "\n");
                continue;
            }
        }
    }

    public String getdatacounter() {
        return newFileListing;
    }

    protected void done() {
    }

    @Override
    protected Integer doInBackground() throws Exception {
        if(drop == true && copyCheck==false){
           pdfSplitDrop(files, string); 
        }else if (drop==false && copyCheck==false){
           pdfSplit(string); 
        }
        if(copyCheck==true && drop==false){
            pdfSplitCopy(string, copyNum);
        }else if(copyCheck==true && drop==true){
            pdfSplitDropCopy(files,string,copyNum);
        }
        
        return null;
    }
}
