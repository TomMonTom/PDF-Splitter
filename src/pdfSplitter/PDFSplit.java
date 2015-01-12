package pdfSplitter;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

/**
 *
 * @author tthompson
 */
public class PDFSplit extends windowMain {

    String DEFAULT_PATH = "C:\\";

    public void pdfSplit() throws IOException, DocumentException {

        // TODO Instead of hard code path, pass in as argument
        String path;
        path = DEFAULT_PATH;
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

            String fileNameFirst = fileNames[0];
            String fileNameSecond = fileNames[1];
            System.out.println("First lot number: " + fileNameFirst + " Second lot number: " + fileNameSecond);
            String[] fileNameFirstParts = fileNameFirst.split("-");
            String[] fileNameSecondParts = fileNameSecond.split("-");

            // Project num is always the 1st part
            String projectNum = fileNameFirstParts[0];
            if (!projectNum.equals(fileNameSecondParts[0])) {
                throw new RuntimeException("Filename needs to be renamed to the correct format");
            }

            // Strip off the first and second lot number, parse into integers
            int firstLotNum;
            int secondLotNum;
            firstLotNum = Integer.parseInt(fileNameFirstParts[1]);
            secondLotNum = Integer.parseInt(fileNameSecondParts[1]);

            // Determine number of pages by difference of lot numbers
            // Read in the source document
            // Example file name: 16034-212234 16034-212236.pdf > 16034-212234.pdf, 16034-212235.pdf, 16034-212236.pdf
            PdfReader pdfFileReader = new PdfReader(file.getPath());
            Document document = new Document(); /*instantiates a new document to be made*/

            int numPages = secondLotNum - firstLotNum;
            // Create a copy of the orignal source file. We will pick specific pages out below
            document.open();
            for (int j = 1; j < numPages + 1; j++) {
                firstLotNum++;
                String FileName = projectNum + "-" + (firstLotNum - 1) + ".pdf"; /* Dynamic file name */
                document = new Document();
                PdfCopy copy = new PdfCopy(document, new FileOutputStream(path + "\\" + FileName));
                document.open();
                copy.addPage(copy.getImportedPage(pdfFileReader, j)); /* Import pages from original document */

                document.close();
            }
            System.out.println("Number of Documents Created:" + numPages);
        }
    }
}
