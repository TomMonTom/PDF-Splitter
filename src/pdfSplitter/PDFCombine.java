/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdfSplitter;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * @author tthompson
 */
public class PDFCombine {
    private static final String DEFAULT_PATH = "C:\\Users\\tthompson\\Pictures\\ControlCenter4\\Scan\\Modified\\Combine";

    public static void main(String[] argv) throws IOException, DocumentException {

        // TODO Instead of hard code path, pass in as argument
        String path;
        if (argv == null || argv.length == 0) {
            System.out.println("Did not specify an argument for folder dir. Using default: " + DEFAULT_PATH);
            path = DEFAULT_PATH;
        } else {
            path = argv[0];
        }
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
            int mod = pdfFileReader.getNumberOfPages()%2;
            if (pdfFileReader.equals(mod)){
                throw new RuntimeException("File is not an even number of pages");
            }
            Document document = new Document(); /*instantiates a new document to be made*/
            int numPages = pdfFileReader.getNumberOfPages();
            int p = 0;
            int j = 0;
                // Create a copy of the orignal source file. We will pick specific pages out below
                document.open();
                  while ( j < numPages) {
                    if (p==numPages){
                        break;
                    }
                    if (j%2==2)
                    {
                        j+=1;
                    }
                    j+=1;
                    firstLotNum++;
                    String FileName=projectNum + "-" +(firstLotNum-1) +".pdf" ; /* Dynamic file name */  
                    document = new Document();
                    PdfCopy copy = new PdfCopy(document,new FileOutputStream(path+"\\"+FileName));
                    document.open();
                    p+=2;
                    copy.addPage(copy.getImportedPage(pdfFileReader, j)); /* Import pages from original document */
                    copy.addPage(copy.getImportedPage(pdfFileReader, p));
                    document.close();
            }
           System.out.println("Number of Documents Created:" + numPages);
           System.out.println("Number of Documents Created:"+listOfFiles[i]);
        }
    }
}
