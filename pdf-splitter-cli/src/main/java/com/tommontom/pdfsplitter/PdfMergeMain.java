package com.tommontom.pdfsplitter;

import java.io.IOException;

import com.itextpdf.text.DocumentException;
import java.io.File;

public abstract class PdfMergeMain {
    private static final String DEFAULT_PATH = "C:\\Users\\tthompson\\Pictures\\ControlCenter4\\Scan\\Modified\\Combine";

    public static void main(String[] argv) throws IOException, DocumentException {
        String path;
        if (argv == null || argv.length == 0) {
            System.out.println("Did not specify an argument for folder dir. Using default: " + DEFAULT_PATH);
            path = DEFAULT_PATH;
        } else {
            path = argv[0];
        }
        File folder = new File(path);
        File [] directoryFiles = folder.listFiles();
        // Create the class to perform the operation
        PdfMerge pdfMerge = new PdfMerge();
        pdfMerge.pdfMerge(directoryFiles);
    }
}
