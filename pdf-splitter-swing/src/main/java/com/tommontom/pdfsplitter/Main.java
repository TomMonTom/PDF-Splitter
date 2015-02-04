/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tommontom.pdfsplitter;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.text.DefaultEditorKit;
import com.itextpdf.text.DocumentException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author Thomas Thompson, Jeffery Jenkins
 * @version 0.0.1
 */
public class Main extends javax.swing.JFrame implements ActionListener, 
                                        PropertyChangeListener{

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        // <editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        // </editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main().setVisible(true);
            }
        });

    }
    public boolean copyCheck;
    public boolean supplierDoc;
    String example = "C:\\User\\PDFstobesplit";

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonBrowse;
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton combine;
    private javax.swing.JCheckBox copiesCheck;
    private javax.swing.JMenuItem copyItem;
    private javax.swing.JMenuItem cut;
    public javax.swing.JTextField directoryField;
    public javax.swing.JLabel directoryLabel;
    private javax.swing.JPanel dragAndDrop;
    private javax.swing.JPanel dragAndDropSplit;
    private javax.swing.JLabel dropLabel;
    private javax.swing.JMenu edit;
    private javax.swing.JButton evenPages;
    private javax.swing.JMenuItem exit;
    public javax.swing.JButton exitButton;
    public javax.swing.JLabel instructions;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    public javax.swing.JSeparator jSeparator1;
    private javax.swing.JMenuBar menuBar;
    public javax.swing.JButton okButton;
    private javax.swing.JMenuItem paste;
    public javax.swing.JProgressBar progressBar;
    public javax.swing.JTextArea progressListing;
    private javax.swing.JCheckBox supplierCheck;
    public javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables
    /**
     * Creates new form WindowMain
     */
    public Main() {
        initComponents();
        progressBar.setBorderPainted(true);
        /* Stores the listing of the files */
    }

    private void buttonBrowseMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_buttonBrowseMouseClicked
        // Creates a file chooser to properly navigate and select a directory that then sets the text in the directory field for later use.
        directoryField.setForeground(Color.BLACK);
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File path = fc.getSelectedFile();
            directoryField.setText(path.getPath());
        }
    }// GEN-LAST:event_buttonBrowseMouseClicked

    private void combineActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_combineActionPerformed
        // TODO add your handling code here:
        PdfMerge combiner = new PdfMerge();
        File folder = new File(directoryField.getText());
        File[] files = folder.listFiles();
        for (int i = 0; i < files.length; i++) {
            combiner.pdfMerge(files);
        }

    }// GEN-LAST:event_combineActionPerformed

    public JMenuBar createMenuBar() {
        // Creates a menu to copy and paste directory information
        edit.setMnemonic(KeyEvent.VK_E);

        cut = new JMenuItem(new DefaultEditorKit.CutAction());
        cut.setText("Cut");
        cut.setMnemonic(KeyEvent.VK_T);
        edit.add(cut);

        copyItem = new JMenuItem(new DefaultEditorKit.CopyAction());
        copyItem.setText("Copy");
        copyItem.setMnemonic(KeyEvent.VK_C);
        copyItem.add(copyItem);

        paste = new JMenuItem(new DefaultEditorKit.PasteAction());
        paste.setText("Paste");
        paste.setMnemonic(KeyEvent.VK_P);
        edit.add(paste);
        menuBar.add(edit);
        return menuBar;
    }



    private void dragAndDropComponentAdded(java.awt.event.ContainerEvent evt) {// GEN-FIRST:event_dragAndDropComponentAdded
        // Code used for the drag and drop portion of the combine function

        new FileDrop(dragAndDrop, new FileDrop.Listener() {
            // initializes a drag and drop interface to then use an object
            @Override
            public void filesDropped(File[] files) {
                PdfMerge merger = new PdfMerge();
                merger.pdfMerge(files);
                progressListing.append(merger.getdatacounter());

            }

        });
    }// GEN-LAST:event_dragAndDropComponentAdded

    private void dragAndDropSplitComponentAdded(java.awt.event.ContainerEvent evt) {// GEN-FIRST:event_dragAndDropSplitComponentAdded
        // Drag and drop zone to split pdf documents that are dragged and dropped into the JPanel.
        new FileDrop(dragAndDropSplit, new FileDrop.Listener() {
            public void filesDropped(File[] files) {
                progressListing.setText("");
                PdfSplit dropSplit = new PdfSplit();
                try {
                    if (copyCheck == true && supplierDoc == false) {
                        dropSplit.pdfSplitDropCopy(files);

                        progressListing.append(dropSplit.getdatacounter());
                    } else if (copyCheck == false && supplierDoc == false) {
                        dropSplit.pdfSplitDrop(files);
                        progressListing.append(dropSplit.getdatacounter());
                    }
                    if (supplierDoc == true && copyCheck == false) {
                        dropSplit.pdfSplitDropSupplierDoc(files);
                        progressListing.append(dropSplit.getdatacounter());
                    }
                } catch (IOException | DocumentException | InterruptedException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }// GEN-LAST:event_dragAndDropSplitComponentAdded

    private void evenPagesMouseClicked(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_evenPagesMouseClicked
        PdfSplit evenSplit = new PdfSplit();
        String path = directoryField.getText();
        try {
            /*
             * Execute method pdfEven when the button is clicked. The method takes pages that are even set and makes one pdf documents consisting of two pages
             * found within the main pdf document.
             */
            evenSplit.pdfEven(path);
            progressListing.append(evenSplit.getdatacounter());
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }// GEN-LAST:event_evenPagesMouseClicked

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_exitButtonActionPerformed
        System.exit(0); // button to perform an exit command to close the application.
    }// GEN-LAST:event_exitButtonActionPerformed

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
        copiesCheck = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        progressListing = new javax.swing.JTextArea();
        cancelButton = new javax.swing.JButton();
        menuBar = new javax.swing.JMenuBar();
        edit = new javax.swing.JMenu();
        copyItem = new javax.swing.JMenuItem();
        paste = new javax.swing.JMenuItem();
        cut = new javax.swing.JMenuItem();
        exit = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        directoryLabel.setText("Directory:");

        okButton.setText("Split");
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

        directoryField.setForeground(new java.awt.Color(204, 204, 204));
        directoryField.setText("C:\\User\\PDFstobesplit");
        directoryField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                directoryFieldMouseClicked(evt);
            }
        });
        directoryField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                directoryFieldActionPerformed(evt);
            }
        });

        buttonBrowse.setText("Browse...");
        buttonBrowse.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                buttonBrowseMouseClicked(evt);
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dragAndDropLayout.createSequentialGroup()
                .addContainerGap(61, Short.MAX_VALUE)
                .addComponent(dropLabel)
                .addGap(58, 58, 58))
        );
        dragAndDropLayout.setVerticalGroup(
            dragAndDropLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dragAndDropLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(dropLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        evenPages.setText("Even Pages");
        evenPages.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                evenPagesMouseClicked(evt);
            }
        });

        supplierCheck.setText("Supplier Receiving Document");
        supplierCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                supplierCheckActionPerformed(evt);
            }
        });

        dragAndDropSplit.setBackground(new java.awt.Color(255, 204, 0));
        dragAndDropSplit.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        dragAndDropSplit.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(64, 64, 64))
        );
        dragAndDropSplitLayout.setVerticalGroup(
            dragAndDropSplitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dragAndDropSplitLayout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(jLabel1)
                .addContainerGap(59, Short.MAX_VALUE))
        );

        copiesCheck.setText("Create Copies");
        copiesCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copiesCheckActionPerformed(evt);
            }
        });

        progressListing.setEditable(false);
        progressListing.setColumns(20);
        progressListing.setRows(5);
        jScrollPane1.setViewportView(progressListing);

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        edit.setText("Edit");

        copyItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_MASK));
        copyItem.setText("Copy");
        edit.add(copyItem);

        paste.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.CTRL_MASK));
        paste.setText("Paste");
        paste.setToolTipText("");
        edit.add(paste);

        cut.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.CTRL_MASK));
        cut.setText("Cut");
        edit.add(cut);

        exit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        exit.setText("Quit");
        exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitActionPerformed(evt);
            }
        });
        edit.add(exit);

        menuBar.add(edit);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(directoryLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(directoryField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonBrowse))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(combine)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(evenPages)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cancelButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(exitButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(okButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(copiesCheck)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(supplierCheck))
                            .addComponent(titleLabel)
                            .addComponent(instructions))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 546, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(dragAndDrop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dragAndDropSplit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(instructions)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(directoryLabel)
                            .addComponent(directoryField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(buttonBrowse))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(dragAndDropSplit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(dragAndDrop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(copiesCheck)
                            .addComponent(supplierCheck))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(combine)
                            .addComponent(evenPages)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(exitButton)
                            .addComponent(okButton)
                            .addComponent(cancelButton))))
                .addGap(18, 18, 18))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_exitActionPerformed

    private void copiesCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copiesCheckActionPerformed
        // TODO add your handling code here:
        if (copiesCheck.isSelected()) {
            copyCheck = true;
            System.out.println(copyCheck);
        } else {
            copyCheck = false;
            System.out.println(copyCheck);
        }
    }//GEN-LAST:event_copiesCheckActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        // TODO add your handling code here:
        PdfSplit cancel = new PdfSplit();
        try {
            for (int i =0; i<cancel.cancel().length;i++) {
                if (cancel.cancel()[i] != null) {
                    Path path = Paths.get(cancel.cancel()[i]);
                    Files.delete(path.resolve(cancel.cancel()[i]));
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_cancelButtonActionPerformed

    private void directoryFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_directoryFieldMouseClicked
        // TODO add your handling code here:
        if (directoryField.getText().equals(example)) {
            directoryField.setText("");
            directoryField.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_directoryFieldMouseClicked

    private void directoryFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_directoryFieldActionPerformed
        // TODO add your handling code here:
            if (directoryField.getText().equals(example)) {
            directoryField.setText("");
            directoryField.setForeground(Color.BLACK);
        }
    }//GEN-LAST:event_directoryFieldActionPerformed

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_okButtonActionPerformed
        PdfSplit okSplit = new PdfSplit();
        try {
            String path = directoryField.getText();
            if (copyCheck == true && supplierDoc == false) {
                okSplit.pdfSplitCopy(path);
                progressBar.setValue(okSplit.barUpdate);
                progressListing.append(okSplit.getdatacounter());
            } else if (copyCheck == false && supplierDoc == false) {
                okSplit.pdfSplit(path);
                progressListing.append(okSplit.getdatacounter());
            }
            if (supplierDoc == true && copyCheck == false) {
                okSplit.pdfSplitSupplierDoc(path);
                progressBar.setValue(okSplit.getvalue());
                progressListing.append(okSplit.getdatacounter());
            } else if (supplierDoc == false && copyCheck == false) {
                okSplit.pdfSplit(path);
                progressBar.setValue(okSplit.getvalue());
                progressListing.append(okSplit.getdatacounter());
            } else {
                okSplit.pdfSplit(path);
                progressBar.setValue(okSplit.getvalue());
                progressListing.append(okSplit.getdatacounter());
            }

        } catch (IOException ex) {
        } catch (DocumentException | InterruptedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }// GEN-LAST:event_okButtonActionPerformed

    private void supplierCheckActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_supplierCheckActionPerformed
        // Radio button to utilize a rename function for specific organization needs.
        if (supplierCheck.isSelected()) {
            supplierDoc = true;
            System.out.println(supplierDoc);
        } else {
            supplierDoc = false;
            System.out.println(supplierDoc);
        }

    }// GEN-LAST:event_supplierCheckActionPerformed

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
