package org.gatech.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class AboutUSDailog extends JDialog {

    public AboutUSDailog(Frame owner) {
        super(owner);
        configureFrame();
    }

//    private void configure() {
//        setTitle("About US");
//        JPanel mainPanel = new JPanel();
//        JLabel label = new JLabel();
//        label.setText("<html><body>This is a <p><b>" +
//                  "<font size=\"+2\">a label on</font>" +
//                  "</b><p>three lines.</body></html>");
//
//        setLayout(new BorderLayout());
//        mainPanel.setLayout(new BorderLayout());
//        mainPanel.add(label, BorderLayout.CENTER);
//        add(mainPanel, BorderLayout.CENTER);
//
//        pack();
//        setSize(300, 200);
//        setResizable(false);
//        setLocationRelativeTo(null);
//        addWindowListener(new WindowAdapter() {
//            public void windowClosing(WindowEvent e) {
//                getMe().setVisible(false);
//            }
//        });
//        setVisible(true);
//    }

    private void configureFrame() {
          this.setTitle("About us");
          JLabel image;
          image = new JLabel();
          ImageIcon icon;
          icon = new ImageIcon("images/aboutus.png");
          image.setIcon(icon);
          this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
          this.getContentPane().add(image);
          JLabel label;
          label = new JLabel("  Developed by Deepal Jayasinghe and Pengcheng Xiong");
          label.setFont(label.getFont().deriveFont(Font.BOLD));
          this.getContentPane().add(label);
          this.pack();
          this.setVisible(true);
          setLocationRelativeTo(null);
      }
    

    private AboutUSDailog getMe() {
        return this;
    }
}
