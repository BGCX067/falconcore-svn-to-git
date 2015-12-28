package org.gatech.gui;

import org.gatech.cs.Folcon;

import javax.swing.*;
import javax.swing.text.Highlighter;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;

public class CodeDialog extends JDialog {

    public CodeDialog(Frame owner, String fileName, int lineNumber, Folcon main) {
        super(owner);
        configureFrame(fileName, lineNumber, main);
    }

    private void configureFrame(String fileName, int lineNumber, Folcon main) {
        this.setTitle(fileName);

        JScrollPane rightPane = new JScrollPane();
        JTextArea rightText = new JTextArea();

        rightText.setBackground(Color.BLACK);

        rightText.setForeground(Color.WHITE);
        rightPane.setViewportView(rightText);


        this.getContentPane().add(rightPane);
         Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
//        setBounds((screenSize.width - this.getWidth()) / 2,
//                (screenSize.height - this.getHeight()) / 2,
//                200,
//                300);
        setSize(700,500);
        setLocationRelativeTo(null);
//        this.pack();
        this.setVisible(true);
        highlightLine(lineNumber, fileName, main, rightText);

    }

    private void highlightLine(int number, String fileName, Folcon folcon, JTextArea jep) {
        ProjectInfoBean projectInfoBean = folcon.getProjectInfoBean();
        String fullFileName = projectInfoBean.getFileName(fileName);
        if (fileName != null) {
            try {
                if (fullFileName != null) {
                    jep.read(new FileReader(new File(fullFileName)), null);
                } else {
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        try {
            String text = jep.getText();
            String value[] = text.split("\n");
            int count = 0;
            for (int i = 0; i <= number - 1; i++) {
                String s = value[i];
                count = count + s.length();
            }

            int so = jep.getLineStartOffset(number);
            int eo = jep.getLineEndOffset(number);
            Highlighter hh = jep.getHighlighter();
            hh.removeAllHighlights();
            Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.RED);
            hh.addHighlight(so, eo, painter);

            int line = jep.getLineCount();
            if (number < line - 1) {
                number = number + 1;
            }
            so = jep.getLineStartOffset(number);
            eo = jep.getLineEndOffset(number);
            jep.setSelectionStart(so);
            jep.setSelectionEnd(eo);
            jep.scrollRectToVisible(new Rectangle(0, jep.getLineOfOffset(number), 10, eo - so));
            jep.repaint();

        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

}
