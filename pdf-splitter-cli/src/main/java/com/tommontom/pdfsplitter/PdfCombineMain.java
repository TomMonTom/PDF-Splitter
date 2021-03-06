package com.tommontom.pdfsplitter;

import java.io.IOException;

import com.itextpdf.text.DocumentException;

public abstract class PdfCombineMain{
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

        // Create the PdfCombine class to perform the operation
        PdfCombine pdfCombine = new PdfCombine();
        pdfCombine.combine(path);
    }
}
