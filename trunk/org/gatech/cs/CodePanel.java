package org.gatech.cs;

import javax.swing.*;
import javax.swing.text.Highlighter;
import javax.swing.text.DefaultHighlighter;
import java.awt.*;
import java.io.FileInputStream;

public class CodePanel extends JScrollPane {
    public CodePanel() {
        populate();
    }


    private void populate() {
        setPreferredSize(new Dimension(400, 500));
        JTextPane jep = new JTextPane();
        jep.setBackground(Color.BLACK);
        jep.setForeground(Color.WHITE);
        jep.setContentType("text/x-java");
//        jep.setEditorKit(kit);
        try {

//           jep.sF


            jep.read(new FileInputStream("D:\\academics\\fall2009\\SoftwareAnalysis\\project\\Thread_visualization\\src\\org\\gatech\\cs\\Node.java"), null);
            String text = jep.getText();
            String value[] = text.split("\n");
            int j = 12;
            int count = 0;
            int length = 0;

            for (int i = 0; i <= j - 1; i++) {
                String s = value[i];
                count = count + s.length();
                length = s.length() + 2;
            }
            Highlighter hh = jep.getHighlighter();
            Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.RED);
            hh.addHighlight(count, count + length, painter);
            
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        setViewportView(jep);
    }
}
