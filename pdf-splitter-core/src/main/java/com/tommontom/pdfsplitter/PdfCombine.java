/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tommontom.pdfsplitter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;

/**
 *
 * @author tthompson
 */
public class PdfCombine {

    public List<File> combine(File folder) throws IOException, DocumentException {
        if (!folder.isDirectory()) {
            throw new IllegalArgumentException("Provided folder is not a directory: " + folder);
        }
        FileNameFilter FileFilter = new FileNameFilter();
        File[] listOfFiles = folder.listFiles(FileFilter);

        /*
         * Stores the listing of the files
         */
        List<File> newFileList = new ArrayList<>();
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
            // Example file name: 16034-212234 16034-212236.pdf > 16034-212234.pdf,
            // 16034-212235.pdf, 16034-212236.pdf
            PdfReader pdfFileReader = new PdfReader(file.getPath());
            int mod = pdfFileReader.getNumberOfPages() % 2;
            if (pdfFileReader.equals(mod)) {
                throw new RuntimeException("File is not an even number of pages");
            }
            Document document = new Document();

            /*
             * instantiates a new document to be made
             */
            int numPages = pdfFileReader.getNumberOfPages();
            int p = 0;
            int j = 0;
            // Create a copy of the orignal source file. We will pick specific pages
            // out below
            document.open();
            while (j < numPages) {
                if (p == numPages) {
                    break;
                }
                if (j % 2 == 2) {
                    j += 1;
                }
                j += 1;
                firstLotNum++;

                // Dynamic filename
                String dynamicFileName = projectNum + "-" + (firstLotNum - 1) + ".pdf";
                document = new Document();
                PdfCopy copy = new PdfCopy(document, new FileOutputStream(folder.getPath() + "\\" + dynamicFileName));
                document.open();
                p += 2;

                // Import pages from original doc
                copy.addPage(copy.getImportedPage(pdfFileReader, j));
                copy.addPage(copy.getImportedPage(pdfFileReader, p));
                document.close();

                // Add to the list of compled file names
                newFileList.add(new File(dynamicFileName));
            }
            System.out.println("Number of Documents Created:" + numPages);
            System.out.println("Number of Documents Created:" + listOfFiles[i]);
        }

        return newFileList;
    }

    public List<File> combine(String folderPath) throws IOException, DocumentException {
        File folder = new File(folderPath);

        return combine(folder);
    }
}
