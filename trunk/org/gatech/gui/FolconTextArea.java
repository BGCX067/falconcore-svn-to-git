package org.gatech.gui;

import org.gatech.cs.Folcon;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class FolconTextArea extends JTextArea {
    private int lineNumber;
    private String fileName;

    public FolconTextArea(JPanel rightPane, final JFrame owner, final Folcon main) {
        JMenuItem menuItem;
        final JPopupMenu popup = new JPopupMenu();
        menuItem = new JMenuItem("Open in new window");
        menuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new CodeDialog(owner, getFileName(), getLineNumber(), main);
            }
        });
        popup.add(menuItem);
        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger())
                    System.out.println("");
                    popup.show(e.getComponent(), e.getX(), e.getY());
            }

        });
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


}
