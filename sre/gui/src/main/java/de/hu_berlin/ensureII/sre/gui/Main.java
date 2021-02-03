package de.hu_berlin.ensureII.sre.gui;

import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import org.apache.commons.io.FileUtils;
import javax.swing.JLabel;

public class Main {

    private JFrame frame;
    private JTextField textFieldBrowseFiles;
    private JTextField textFieldLogicFormular;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Main window = new Main();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public Main() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        
        textFieldBrowseFiles = new JTextField();
        textFieldBrowseFiles.setEditable(false);
        textFieldBrowseFiles.setBounds(6, 28, 315, 26);
        frame.getContentPane().add(textFieldBrowseFiles);
        textFieldBrowseFiles.setColumns(10);
        
        JButton btnBrowseFiles = new JButton("Browse Files");
        btnBrowseFiles.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textFieldBrowseFiles.setText(browseFiles(frame));
            }
        });
        btnBrowseFiles.setBounds(327, 28, 117, 29);
        frame.getContentPane().add(btnBrowseFiles);
        
        JLabel lblBrowseFile = new JLabel("Choose a file containing an sre");
        lblBrowseFile.setBounds(6, 6, 217, 16);
        frame.getContentPane().add(lblBrowseFile);
        
        textFieldLogicFormular = new JTextField();
        textFieldLogicFormular.setBounds(6, 110, 315, 26);
        frame.getContentPane().add(textFieldLogicFormular);
        textFieldLogicFormular.setColumns(10);
        
        JLabel lblLogicFormular = new JLabel("Input Logic Formular");
        lblLogicFormular.setBounds(6, 82, 147, 16);
        frame.getContentPane().add(lblLogicFormular);
        
        JButton btnParseFormular = new JButton("Parse Formular");
        btnParseFormular.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                parseFormular(textFieldLogicFormular.getText());
            }
        });
        btnParseFormular.setBounds(327, 110, 117, 29);
        frame.getContentPane().add(btnParseFormular);
    }
    
    private String browseFiles(JFrame frame) {
        
        FileDialog fd = new FileDialog(frame, "Choose a file", FileDialog.LOAD);
        fd.setDirectory(FileUtils.getUserDirectory().getAbsolutePath());
        fd.setVisible(true);
        String filename = fd.getFile();
        String path = fd.getDirectory() + filename;
        if (filename == null) {
          return "";
        }else {
          return path;
        }

    }
    
    private void parseFormular(String formular) {
        
    }
}
