/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdfSplitter;

import java.io.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.TransferHandler;
import javax.swing.text.DefaultEditorKit;
/**
 *
 * @author tthompson
 */
public class windowMain extends javax.swing.JFrame {

public boolean supplierDoc;
    /**
     * Creates new form windowMain
     */
    public windowMain() {
        initComponents();
        /* Stores the listing of the files */
    }
public JMenuBar createMenuBar () {
        edit.setMnemonic(KeyEvent.VK_E);

        cut = new JMenuItem(new DefaultEditorKit.CutAction());
        cut.setText("Cut");
        cut.setMnemonic(KeyEvent.VK_T);
        edit.add(cut);

        copy = new JMenuItem(new DefaultEditorKit.CopyAction());
        copy.setText("Copy");
        copy.setMnemonic(KeyEvent.VK_C);
        copy.add(copy);

        paste = new JMenuItem(new DefaultEditorKit.PasteAction());
        paste.setText("Paste");
        paste.setMnemonic(KeyEvent.VK_P);
        edit.add(paste);
        menuBar.add(edit);
        return menuBar;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")


    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator1 = new javax.swing.JSeparator();
        directoryLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        okButton = new javax.swing.JButton();
        titleLabel = new javax.swing.JLabel();
        instructions = new javax.swing.JLabel();
        exitButton = new javax.swing.JButton();
        directoryField = new javax.swing.JTextField();
        buttonBrowse = new javax.swing.JButton();
        combine = new javax.swing.JButton();
        dragAndDrop = new javax.swing.JPanel();
        dropLabel = new javax.swing.JLabel();
        evenPages = new javax.swing.JButton();
        supplierCheck = new javax.swing.JCheckBox();
        dragAndDropSplit = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        edit = new javax.swing.JMenu();
        copy = new javax.swing.JMenuItem();
        paste = new javax.swing.JMenuItem();
        cut = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        directoryLabel.setText("Directory:");

        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        titleLabel.setText("PDF Spliter");

        instructions.setText("Place the directory path in the field bellow. Files must be formated correctly");

        exitButton.setText("Exit");
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });

        directoryField.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                directoryFieldComponentAdded(evt);
            }
        });
        directoryField.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                directoryFieldMouseDragged(evt);
            }
        });

        buttonBrowse.setText("Browse...");
        buttonBrowse.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonBrowseMouseClicked(evt);
            }
        });
        buttonBrowse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonBrowseActionPerformed(evt);
            }
        });

        combine.setText("Combine");
        combine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combineActionPerformed(evt);
            }
        });

        dragAndDrop.setBackground(new java.awt.Color(204, 255, 51));
        dragAndDrop.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        dragAndDrop.setForeground(new java.awt.Color(25, 25, 25));
        dragAndDrop.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                dragAndDropComponentAdded(evt);
            }
        });

        dropLabel.setText("Drop Files Here For Combining");

        javax.swing.GroupLayout dragAndDropLayout = new javax.swing.GroupLayout(dragAndDrop);
        dragAndDrop.setLayout(dragAndDropLayout);
        dragAndDropLayout.setHorizontalGroup(
            dragAndDropLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dragAndDropLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(dropLabel)
                .addContainerGap(26, Short.MAX_VALUE))
        );
        dragAndDropLayout.setVerticalGroup(
            dragAndDropLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dragAndDropLayout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(dropLabel)
                .addContainerGap(57, Short.MAX_VALUE))
        );

        evenPages.setText("Even Pages");
        evenPages.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                evenPagesMouseClicked(evt);
            }
        });
        evenPages.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                evenPagesActionPerformed(evt);
            }
        });

        supplierCheck.setText("Supplier Receiving Document");
        supplierCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                supplierCheckActionPerformed(evt);
            }
        });
        supplierCheck.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                supplierCheckPropertyChange(evt);
            }
        });

        dragAndDropSplit.setBackground(new java.awt.Color(255, 204, 0));
        dragAndDropSplit.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        dragAndDropSplit.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                dragAndDropSplitComponentAdded(evt);
            }
        });

        jLabel1.setText("Drop Files Here For Splitting");

        javax.swing.GroupLayout dragAndDropSplitLayout = new javax.swing.GroupLayout(dragAndDropSplit);
        dragAndDropSplit.setLayout(dragAndDropSplitLayout);
        dragAndDropSplitLayout.setHorizontalGroup(
            dragAndDropSplitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dragAndDropSplitLayout.createSequentialGroup()
                .addContainerGap(51, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(43, 43, 43))
        );
        dragAndDropSplitLayout.setVerticalGroup(
            dragAndDropSplitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dragAndDropSplitLayout.createSequentialGroup()
                .addGap(54, 54, 54)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        edit.setText("Edit");

        copy.setText("Copy");
        edit.add(copy);

        paste.setText("Paste");
        paste.setToolTipText("");
        edit.add(paste);

        cut.setText("Cut");
        edit.add(cut);

        menuBar.add(edit);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 1, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(dragAndDrop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(dragAndDropSplit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(titleLabel)
                            .addComponent(instructions)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(combine)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(evenPages)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(supplierCheck)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(exitButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(okButton))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(directoryLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(directoryField, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buttonBrowse)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(instructions)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(directoryLabel)
                    .addComponent(directoryField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonBrowse))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dragAndDropSplit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dragAndDrop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(okButton)
                    .addComponent(exitButton)
                    .addComponent(combine)
                    .addComponent(evenPages)
                    .addComponent(supplierCheck))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        System.exit(0);        // TODO add your handling code here:
    }//GEN-LAST:event_exitButtonActionPerformed

    private void buttonBrowseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_buttonBrowseMouseClicked
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File path = fc.getCurrentDirectory();
            directoryField.setText(path.getPath());
        }
    }//GEN-LAST:event_buttonBrowseMouseClicked

    private void buttonBrowseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonBrowseActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_buttonBrowseActionPerformed

    private void dragAndDropComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_dragAndDropComponentAdded
        new FileDrop(dragAndDrop, new FileDrop.Listener() {

            public void filesDropped(File[] files) {
                try {
                    // handle file drop
                    pdfMerge(files);
                } catch (DocumentException ex) {
                    Logger.getLogger(windowMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }   // end filesDropped

            public void pdfMerge(File[] files) throws DocumentException {
                
                File newFiles = files[0];
                String DEFAULT_PATH = newFiles.getParent();
                System.out.println(DEFAULT_PATH);
                List<java.io.InputStream> list = new ArrayList<>();
                File[] listOfFiles = files; /* Stores the listing of the files */
                Arrays.sort(listOfFiles);
                for (int i =0; i<listOfFiles.length; i++){
                System.out.println(listOfFiles[i]);
                }
                int i;
                try {
                    for ( i = 0; i < listOfFiles.length; i++) {
                        list.add(new FileInputStream(new File(DEFAULT_PATH + "\\" + listOfFiles[i].getName())));
                    }
                    OutputStream out = new FileOutputStream(new File(DEFAULT_PATH + "\\" + listOfFiles[0].getName()+".pdf"));
                    doMerge(list, out);
                    
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }//GEN-LAST:event_dragAndDropComponentAdded

    private void combineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combineActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_combineActionPerformed

    private void evenPagesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_evenPagesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_evenPagesActionPerformed

    private void evenPagesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_evenPagesMouseClicked
        try {
            // TODO add your handling code here:
            pdfEven();
        } catch (IOException ex) {
            Logger.getLogger(windowMain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(windowMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_evenPagesMouseClicked

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        try {
            pdfSplit();
        } catch (IOException ex) {
            Logger.getLogger(windowMain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(windowMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_okButtonActionPerformed

    private void supplierCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_supplierCheckActionPerformed
        // TODO add your handling code here:
               if (supplierCheck.isSelected()){
                   supplierDoc = true;
                   System.out.println(supplierDoc);
               }else {
                   supplierDoc = false;
                   System.out.println(supplierDoc);
               }
               
    }//GEN-LAST:event_supplierCheckActionPerformed

    private void supplierCheckPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_supplierCheckPropertyChange
        // TODO add your handling code here:

    }//GEN-LAST:event_supplierCheckPropertyChange

    private void dragAndDropSplitComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_dragAndDropSplitComponentAdded
        // TODO add your handling code here:
         new FileDrop(dragAndDropSplit, new FileDrop.Listener() {
            public void filesDropped(File[] files) {
                try {
                    // handle file drop
                    pdfSplitDrop(files);
                } catch (DocumentException ex) {
                    Logger.getLogger(windowMain.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(windowMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
         });
    }//GEN-LAST:event_dragAndDropSplitComponentAdded

    private void directoryFieldMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_directoryFieldMouseDragged
      // TODO add your handling code here:
    }//GEN-LAST:event_directoryFieldMouseDragged

    private void directoryFieldComponentAdded(java.awt.event.ContainerEvent evt) {//GEN-FIRST:event_directoryFieldComponentAdded
        directoryField.setDropTarget(new DropTarget() {
            public synchronized void drop(DropTargetDropEvent evt){
                try {
                    evt.acceptDrop(DnDConstants.ACTION_COPY);
                    List<File> droppedFile = (List<File>) evt
                    .getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    for (File file: droppedFile){
                        directoryField.setText(file.getPath());
                    }
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        }); 
    }//GEN-LAST:event_directoryFieldComponentAdded
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(windowMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(windowMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(windowMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(windowMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new windowMain().setVisible(true);
            }
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonBrowse;
    private javax.swing.JButton combine;
    private javax.swing.JMenuItem copy;
    private javax.swing.JMenuItem cut;
    private javax.swing.JTextField directoryField;
    public javax.swing.JLabel directoryLabel;
    private javax.swing.JPanel dragAndDrop;
    private javax.swing.JPanel dragAndDropSplit;
    private javax.swing.JLabel dropLabel;
    private javax.swing.JMenu edit;
    private javax.swing.JButton evenPages;
    public javax.swing.JButton exitButton;
    public javax.swing.JLabel instructions;
    private javax.swing.JLabel jLabel1;
    public javax.swing.JSeparator jSeparator1;
    private javax.swing.JMenuBar menuBar;
    public javax.swing.JButton okButton;
    private javax.swing.JMenuItem paste;
    public javax.swing.JProgressBar progressBar;
    private javax.swing.JCheckBox supplierCheck;
    public javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables

    public void pdfSplitDrop(File[] files) throws IOException, DocumentException {
        // TODO Instead of hard code path, pass in as argument
        String path = files[0].getParent();
        File folder = new File(path);
        File [] listOfFiles = files; /* Stores the listing of the files */
        for (int i = 0; i < listOfFiles.length; i++) {
            File file = listOfFiles[i];
            if (!file.isFile()) {
                continue;
            }
            // Split the source filename into its 2 parts
            String fileName = file.getName();
            String fileNameWithoutExt = fileName.substring(0, fileName.lastIndexOf('.'));
            PdfReader pdfFileReader = new PdfReader(file.getPath());
            Document document = new Document(); /*instantiates a new document to be made*/
            int numPages = pdfFileReader.getNumberOfPages();
            // Determine number of pages by difference of lot numbers
            // Read in the source document
            // Example file name: 16034-212234 16034-212236.pdf > 16034-212234.pdf, 16034-212235.pdf, 16034-212236.pdf
            // Create a copy of the orignal source file. We will pick specific pages out below
            document.open();
            for (int j = 1; j < numPages + 1; j++) {
                String FileName = (fileNameWithoutExt); /* Dynamic file name */
                document = new Document();
                PdfCopy copy = new PdfCopy(document, new FileOutputStream(path + "\\" +FileName+"("+j+")"+".pdf"));
                document.open();
                copy.addPage(copy.getImportedPage(pdfFileReader, j)); /* Import pages from original document */
                document.close();
                }

            System.out.println("Number of Documents Created:" + numPages);
            pdfFileReader.close();
            }
        
    }
    public void pdfSplit() throws IOException, DocumentException {
        String DEFAULT_PATH = directoryField.getText();
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
            PdfReader pdfFileReader = new PdfReader(file.getPath());
            Document document = new Document(); /*instantiates a new document to be made*/
            int numPages = pdfFileReader.getNumberOfPages();
            System.out.println(supplierDoc);
            if (supplierDoc = false){
            // Split on a space '\s'
            String[] fileNames = fileNameWithoutExt.split("\\s");
            /*if (fileNames.length != 2) {
                throw new RuntimeException("File name format is not in right format");
            }*/

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
            // Create a copy of the orignal source file. We will pick specific pages out below
            document.open();
            for (int j = 1; j < numPages + 1; j++) {
                firstLotNum++;
                String FileName = projectNum + "-" + (firstLotNum + 1) + ".pdf"; /* Dynamic file name */
                document = new Document();
                PdfCopy copy = new PdfCopy(document, new FileOutputStream(path + "\\" + FileName));
                document.open();
                copy.addPage(copy.getImportedPage(pdfFileReader, j)); /* Import pages from original document */
                }
                document.close();
            } else if (supplierDoc = false){
            // Determine number of pages by difference of lot numbers
            // Read in the source document
            // Example file name: 16034-212234 16034-212236.pdf > 16034-212234.pdf, 16034-212235.pdf, 16034-212236.pdf
            // Create a copy of the orignal source file. We will pick specific pages out below
            document.open();
            for (int j = 1; j < numPages + 1; j++) {
                String FileName = (fileNameWithoutExt); /* Dynamic file name */
                document = new Document();
                PdfCopy copy = new PdfCopy(document, new FileOutputStream(path + "\\" +FileName+"("+j+")"+".pdf"));
                document.open();
                copy.addPage(copy.getImportedPage(pdfFileReader, j)); /* Import pages from original document */
                document.close();
                }

            System.out.println("Number of Documents Created:" + numPages);
            }else{
                break;
            }
               pdfFileReader.close();
        }
    }

    public void pdfEven() throws IOException, DocumentException {

        String DEFAULT_PATH = directoryField.getText();        // TODO Instead of hard code path, pass in as argument
        File folder = new File(DEFAULT_PATH);
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
            int mod = pdfFileReader.getNumberOfPages() % 2;
            if (pdfFileReader.equals(mod)) {
                throw new RuntimeException("File is not an even number of pages");
            }
            Document document = new Document(); /*instantiates a new document to be made*/

            int numPages = secondLotNum - firstLotNum + 1;
            int p = 0;
            int j = 1;
            // Create a copy of the orignal source file. We will pick specific pages out below
            document.open();
            while (j < numPages) {
                j++;
                if (j % 2 == 1) {
                    j += 1;
                }
                firstLotNum++;
                String FileName = projectNum + "-" + (firstLotNum - 1) + ".pdf"; /* Dynamic file name */

                document = new Document();
                PdfCopy copy = new PdfCopy(document, new FileOutputStream(DEFAULT_PATH + "\\" + FileName));
                document.open();
                p += 2;
                copy.addPage(copy.getImportedPage(pdfFileReader, j)); /* Import pages from original document */

                copy.addPage(copy.getImportedPage(pdfFileReader, p));
                document.close();

            }
            System.out.println("Number of Documents Created:" + numPages);
            System.out.println("Number of Documents Created:" + listOfFiles[i]);
        }
    }

    public static void doMerge(java.util.List<InputStream> list, OutputStream outputStream)
            throws DocumentException, IOException {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, outputStream);
        document.open();
        PdfContentByte cb = writer.getDirectContent();

        for (InputStream in : list) {
            PdfReader reader = new PdfReader(in);
            for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                document.newPage();
                //import the page from source pdf
                PdfImportedPage page = writer.getImportedPage(reader, i);
                //add the page to the destination pdf
                cb.addTemplate(page, 0, 0);
            }
        }

        outputStream.flush();
        document.close();
        outputStream.close();
    }
}
