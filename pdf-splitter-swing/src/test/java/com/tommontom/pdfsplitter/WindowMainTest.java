/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tommontom.pdfsplitter;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import javax.swing.JMenuBar;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Admin
 */
public class WindowMainTest {
    
    public WindowMainTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of doMerge method, of class WindowMain.
     */
    @Test
    public void testDoMerge() throws Exception {
        System.out.println("doMerge");
        List<InputStream> list = null;
        OutputStream outputStream = null;
        WindowMain.doMerge(list, outputStream);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of main method, of class WindowMain.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        WindowMain.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createMenuBar method, of class WindowMain.
     */
    @Test
    public void testCreateMenuBar() {
        System.out.println("createMenuBar");
        WindowMain instance = new WindowMain();
        JMenuBar expResult = null;
        JMenuBar result = instance.createMenuBar();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of pdfEven method, of class WindowMain.
     */
    @Test
    public void testPdfEven() throws Exception {
        System.out.println("pdfEven");
        WindowMain instance = new WindowMain();
        instance.pdfEven();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of pdfMerge method, of class WindowMain.
     */
    @Test
    public void testPdfMerge() throws Exception {
        System.out.println("pdfMerge");
        File[] files = null;
        WindowMain instance = new WindowMain();
        instance.pdfMerge(files);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of pdfSplit method, of class WindowMain.
     */
    @Test
    public void testPdfSplit() throws Exception {
        System.out.println("pdfSplit");
        WindowMain instance = new WindowMain();
        instance.pdfSplit();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of pdfSplitDrop method, of class WindowMain.
     */
    @Test
    public void testPdfSplitDrop() throws Exception {
        System.out.println("pdfSplitDrop");
        File[] files = null;
        WindowMain instance = new WindowMain();
        instance.pdfSplitDrop(files);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
