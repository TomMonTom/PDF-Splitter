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
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.text.Highlighter;

/**
 *
 * @author Thomas Thompson, Jeffery Jenkins
 * @version 0.0.1
 */
public class PDFSplitter extends javax.swing.JFrame implements ActionListener,
        PropertyChangeListener {

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
            java.util.logging.Logger.getLogger(PDFSplitter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PDFSplitter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PDFSplitter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PDFSplitter.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        // </editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PDFSplitter().setVisible(true);
            }
        });

    }
    public boolean copyCheck;
    String example = "C:\\PDFs";
    public Highlighter highlightRed;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonBrowse;
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton combine;
    private javax.swing.JTextField copiesAmount;
    private javax.swing.JCheckBox copiesCheck;
    private javax.swing.JMenuItem copyItem;
    private javax.swing.JLabel copyLabel;
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
    private javax.swing.JDialog helpDialog;
    public javax.swing.JLabel instructions;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    public javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JMenuBar menuBar;
    public javax.swing.JButton okButton;
    private javax.swing.JButton okDialog;
    private javax.swing.JMenuItem paste;
    public javax.swing.JProgressBar progressBar;
    private javax.swing.JTextArea progressListing;
    public javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables
    /**
     * Creates new form WindowMain
     */
    public PDFSplitter() {
        initComponents();
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
        concatDirectoryContents(folder);
        File[] fSorted = fileList.toArray(new File[fileList.size()]);
        combiner.pdfMerge(fSorted);

    }// GEN-LAST:event_combineActionPerformed

    public int p = 0;
    public List<File> fileList = new ArrayList<>();

    public void concatDirectoryContents(File dir) {
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                concatDirectoryContents(file);
            } else if (file.isFile() && !file.isDirectory()) {
                fileList.add(file);
                System.out.print(p);
            }
        }
    }

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
            public void filesDropped(File[] files) {
                PdfMerge merge = new PdfMerge();
                merge.pdfMerge(files);
                progressListing.append(merge.getdatacounter());
                progressBar.setIndeterminate(false);
            }
        });

    }// GEN-LAST:event_dragAndDropComponentAdded

    private void dragAndDropSplitComponentAdded(java.awt.event.ContainerEvent evt) {// GEN-FIRST:event_dragAndDropSplitComponentAdded
        // Drag and drop zone to split pdf documents that are dragged and dropped into the JPanel.
        new FileDrop(dragAndDropSplit, new FileDrop.Listener() {
            public void filesDropped(File[] files) {
                PdfSplit dropSplit = new PdfSplit();
                String path;
                path = directoryField.getText() + "/";
                if (directoryField.getText().isEmpty()) {
                    path = files[0].getParent() + "/";
                } else {
                    path = directoryField.getText() + "/";
                }
                System.out.println(path);
                String copyNumString = copiesAmount.getText();
                int copyNum;
                if (!copiesAmount.getText().isEmpty()) {
                    copyNum = Integer.parseInt(copyNumString);
                } else {
                    copyNum = 0;
                }
                try {
                    if (copyCheck == true) {
                        dropSplit.pdfSplitDropCopy(files, path, copyNum);
                        progressListing.append(dropSplit.getdatacounter());
                    } else if (copyCheck == false) {
                        progressBar.setValue(dropSplit.getProgress());
                        dropSplit.pdfSplitDrop(files, path);
                        progressListing.append(dropSplit.getdatacounter());
                        System.out.println(progressListing.getText());
                        if (progressListing.getText().contains("encrypted")) {
                            copyLabel.setText("File is encrypted, please decrypt before modifying");
                            copyLabel.setForeground(Color.red);
                        }
                        if (!progressListing.getText().contains("encrypted")) {
                            copyLabel.setText("");
                        }
                    }
                } catch (IOException | DocumentException | InterruptedException ex) {
                    Logger.getLogger(PDFSplitter.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(PDFSplitter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(PDFSplitter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }// GEN-LAST:event_evenPagesMouseClicked

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_exitButtonActionPerformed
        helpDialog.setVisible(true); // button to perform an exit command to close the application.
    }// GEN-LAST:event_exitButtonActionPerformed

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        helpDialog = new javax.swing.JDialog();
        okDialog = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jSeparator1 = new javax.swing.JSeparator();
        directoryLabel = new javax.swing.JLabel();
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
        dragAndDropSplit = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        copiesCheck = new javax.swing.JCheckBox();
        cancelButton = new javax.swing.JButton();
        copiesAmount = new javax.swing.JTextField();
        copyLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        progressListing = new javax.swing.JTextArea();
        progressBar = new javax.swing.JProgressBar();
        menuBar = new javax.swing.JMenuBar();
        edit = new javax.swing.JMenu();
        copyItem = new javax.swing.JMenuItem();
        paste = new javax.swing.JMenuItem();
        cut = new javax.swing.JMenuItem();
        exit = new javax.swing.JMenuItem();

        helpDialog.setTitle("Help");
        helpDialog.setMinimumSize(new java.awt.Dimension(400, 309));
        helpDialog.setResizable(false);

        okDialog.setText("OK");
        okDialog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okDialogActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setText("Drag and drop text files into the green window to combine them together. This works with image files\n\nFiles draged into the orange window will split them appart. These will be named and stored in a separate directory. The buttons will do the same commands by searching the entire directory structure and save them into their own directory.\n\nPlease donate to keep this project going at www.pdfsplitterapp.org\n\nHappy Splitting!\n\nCopyright 2014: PDFSplitter by Thomas David Monberg Thompson and Jeffery Jenkins 2014\n\n");
        jTextArea1.setWrapStyleWord(true);
        jScrollPane1.setViewportView(jTextArea1);
        jTextArea1.setEditable(false);
        jTextArea1.setLineWrap(true);
        jTextArea1.setWrapStyleWord(true);

        javax.swing.GroupLayout helpDialogLayout = new javax.swing.GroupLayout(helpDialog.getContentPane());
        helpDialog.getContentPane().setLayout(helpDialogLayout);
        helpDialogLayout.setHorizontalGroup(
            helpDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(helpDialogLayout.createSequentialGroup()
                .addGroup(helpDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(helpDialogLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(okDialog))
                    .addGroup(helpDialogLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)))
                .addContainerGap())
        );
        helpDialogLayout.setVerticalGroup(
            helpDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(helpDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(okDialog)
                .addContainerGap())
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        directoryLabel.setText("Directory:");

        okButton.setText("Split");
        okButton.setToolTipText("Splits the documents that are located in the directory path.");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        titleLabel.setText("PDF Spliter");

        exitButton.setText("Help");
        exitButton.setToolTipText("Exit's the program");
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });

        directoryField.setForeground(new java.awt.Color(204, 204, 204));
        directoryField.setToolTipText("The directory path uses the path given to save where the files are combined or split. It is also for the buttons at the bottom");
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
        combine.setToolTipText("Combine will take the files in the directory path and combine them together. Works with image files as well.");
        combine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combineActionPerformed(evt);
            }
        });

        dragAndDrop.setBackground(new java.awt.Color(204, 255, 51));
        dragAndDrop.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        dragAndDrop.setForeground(new java.awt.Color(25, 25, 25));
        dragAndDrop.setPreferredSize(new java.awt.Dimension(240, 131));
        dragAndDrop.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                dragAndDropComponentAdded(evt);
            }
        });
        dragAndDrop.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dragAndDropPropertyChange(evt);
            }
        });

        dropLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        dropLabel.setText("Drop Files Here For Combining");

        javax.swing.GroupLayout dragAndDropLayout = new javax.swing.GroupLayout(dragAndDrop);
        dragAndDrop.setLayout(dragAndDropLayout);
        dragAndDropLayout.setHorizontalGroup(
            dragAndDropLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dropLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)
        );
        dragAndDropLayout.setVerticalGroup(
            dragAndDropLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dragAndDropLayout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(dropLabel)
                .addContainerGap(56, Short.MAX_VALUE))
        );

        evenPages.setText("Split Even Pages");
        evenPages.setToolTipText("Even pages will split a pdf that has even pages such as a front and back");
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

        dragAndDropSplit.setBackground(new java.awt.Color(255, 204, 0));
        dragAndDropSplit.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        dragAndDropSplit.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        dragAndDropSplit.setPreferredSize(new java.awt.Dimension(240, 80));
        dragAndDropSplit.addContainerListener(new java.awt.event.ContainerAdapter() {
            public void componentAdded(java.awt.event.ContainerEvent evt) {
                dragAndDropSplitComponentAdded(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Drop Files Here For Splitting");

        javax.swing.GroupLayout dragAndDropSplitLayout = new javax.swing.GroupLayout(dragAndDropSplit);
        dragAndDropSplit.setLayout(dragAndDropSplitLayout);
        dragAndDropSplitLayout.setHorizontalGroup(
            dragAndDropSplitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dragAndDropSplitLayout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        dragAndDropSplitLayout.setVerticalGroup(
            dragAndDropSplitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dragAndDropSplitLayout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        copiesCheck.setText("Create Copies:");
        copiesCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copiesCheckActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.setToolTipText("Cancels the current operation");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        copiesAmount.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                copiesAmountFocusGained(evt);
            }
        });

        progressListing.setColumns(20);
        progressListing.setRows(5);
        progressListing.setWrapStyleWord(true);
        jScrollPane2.setViewportView(progressListing);
        progressListing.setEditable(false);

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSeparator1)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(combine)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(evenPages)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(cancelButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(exitButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(okButton))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(dragAndDrop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(dragAndDropSplit, javax.swing.GroupLayout.DEFAULT_SIZE, 259, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(directoryLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(directoryField, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buttonBrowse))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(copiesCheck)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(copiesAmount, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(copyLabel))
                            .addComponent(titleLabel, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(instructions, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane2))
                .addContainerGap())
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
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(dragAndDropSplit, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                    .addComponent(dragAndDrop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(copiesCheck)
                    .addComponent(copiesAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(copyLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(exitButton)
                        .addComponent(okButton)
                        .addComponent(cancelButton))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(combine)
                        .addComponent(evenPages)))
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
            if (copiesAmount.getText().isEmpty()) {
                copiesAmount.setBackground(Color.red);
                copyLabel.setText("Please put in the number of copies to be made");
            } else {
                copiesAmount.setBackground(Color.white);
                copyLabel.setText("");
            }
            copyCheck = true;
            System.out.println(copyCheck);
        } else {
            copyCheck = false;
            copiesAmount.setBackground(Color.white);
            copyLabel.setText("");
            System.out.println(copyCheck);
        }
    }//GEN-LAST:event_copiesCheckActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        // TODO add your handling code here:
        PdfSplit worker = new PdfSplit();
        worker.cancel(true);
        //workerMerge.cancel(true);
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

    private void copiesAmountFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_copiesAmountFocusGained
        // TODO add your handling code here:
        copiesAmount.setBackground(Color.white);
    }//GEN-LAST:event_copiesAmountFocusGained

    private void dragAndDropPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dragAndDropPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_dragAndDropPropertyChange

    private void okDialogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okDialogActionPerformed
        // TODO add your handling code here:
        helpDialog.setVisible(false);
    }//GEN-LAST:event_okDialogActionPerformed

    private void evenPagesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_evenPagesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_evenPagesActionPerformed

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_okButtonActionPerformed
        PdfSplit okSplit = new PdfSplit();
        try {
            String path = directoryField.getText();
            String copyNumString = copiesAmount.getText();
            int copyNum = Integer.parseInt(copyNumString);
            if (copyCheck == true) {
                okSplit.pdfSplitCopy(path, copyNum);
                progressListing.append(okSplit.getdatacounter());
            } else if (copyCheck == false) {
                okSplit.pdfSplit(path);
                progressListing.append(okSplit.getdatacounter());
            }

        } catch (IOException ex) {
            Logger.getLogger(PDFSplitter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException | InterruptedException ex) {
            Logger.getLogger(PDFSplitter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }// GEN-LAST:event_okButtonActionPerformed

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
