package org.gatech.cs;

import org.gatech.gui.ProjectInfoBean;
import org.gatech.gui.FolconTextArea;

import javax.swing.text.Highlighter;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.*;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.awt.*;

public class HighLightContainer {

    private String currentFileName;
    private Folcon folcon;
    private JTextArea jep;

    public HighLightContainer(Folcon folcon, JTextArea jep) {
        this.folcon = folcon;
        this.jep = jep;
    }

    public void highlightLine(int number, String fileName) {
        ProjectInfoBean projectInfoBean = folcon.getProjectInfoBean();
        String fullFileName = projectInfoBean.getFileName(fileName);
        if (fileName != null && !fileName.equals(currentFileName)) {
            try {
                if (fullFileName != null) {
                    jep.read(new FileReader(new File(fullFileName)), null);
                    currentFileName = fullFileName;
                } else {
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(jep instanceof FolconTextArea) {
            ((FolconTextArea)jep).setLineNumber(number);
            ((FolconTextArea)jep).setFileName(fileName);
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
            if(number <line -1){
                number = number + 1;
            }
            so = jep.getLineStartOffset(number);
            eo = jep.getLineEndOffset(number);
            jep.setSelectionStart(so);
            jep.setSelectionEnd(eo);
            jep.scrollRectToVisible(new  Rectangle(0,jep.getLineOfOffset(number),10,eo-so));
            jep.repaint();

        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    public void highlight(String pattern, String fileName) {
        System.out.println("pattern = " + pattern);
        System.out.println("fileName = " + fileName);
        ProjectInfoBean projectInfoBean = folcon.getProjectInfoBean();
        fileName = projectInfoBean.getFileName(fileName);
        if (fileName != null && !fileName.equals(currentFileName) && projectInfoBean != null) {
            String fullFileName = projectInfoBean.getFileName(fileName);
            try {
                if (fullFileName != null) {
                    jep.read(new FileReader(new File(fullFileName)), null);
                    Highlighter hh = jep.getHighlighter();
                    hh.removeAllHighlights();
                    Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.RED);

                    jep.repaint();

                    try {
                        Document doc = jep.getDocument();
                        String text = doc.getText(0, doc.getLength());
                        int pos = 0;
                        // Search for pattern
                        while ((pos = text.indexOf(pattern, pos)) >= 0) {
                            // Create highlighter using private painter and apply around pattern
                            hh.addHighlight(pos, pos + pattern.length(), painter);
                            pos += pattern.length();
                        }
                    } catch (BadLocationException e) {
                    }
                } else {
                    return;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
