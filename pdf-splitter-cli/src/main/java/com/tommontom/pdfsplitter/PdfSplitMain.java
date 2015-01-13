package com.tommontom.pdfsplitter;

import java.io.IOException;

import com.itextpdf.text.DocumentException;

public abstract class PdfSplitMain {
    private static final String DEFAULT_PATH = "C:\\";

    public static void main(String[] argv) throws IOException, DocumentException {
        String path;
        if (argv == null || argv.length == 0) {
            System.out.println("Did not specify an argument for folder dir. Using default: " + DEFAULT_PATH);
            path = DEFAULT_PATH;
        } else {
            path = argv[0];
        }

        PdfSplit pdfSplit = new PdfSplit();
        pdfSplit.split(path);
    }
}
