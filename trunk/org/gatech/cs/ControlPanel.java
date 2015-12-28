package org.gatech.cs;

import org.gatech.RunTimeStack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ControlPanel extends JPanel {
    private RunTimeStack shapeExample;
    private JComboBox varibleCombo;
    private JComboBox threadCombo;

    public ControlPanel(RunTimeStack shapeExample, String[] variabes, String[] threads) {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        shapeExample.setControlPanel(this);
        this.shapeExample = shapeExample;
        populate(variabes, threads);
        reconfigure(variabes, threads);
    }

    public void reconfigure(String[] variabes, String[] threads) {
        varibleCombo.removeAllItems();
        threadCombo.removeAllItems();
        for (int i = 0; i < variabes.length; i++) {
            String variabe = variabes[i];
            varibleCombo.addItem(variabe);
        }
        for (int i = 0; i < threads.length; i++) {
            String thread = threads[i];
            threadCombo.addItem(thread);
        }
    }

    private void populate(final String[] variabes, final String[] threads) {
        varibleCombo = new JComboBox(variabes);
        add(varibleCombo);
        varibleCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                shapeExample.showVariable("" + varibleCombo.getSelectedItem());
            }
        });

        JButton showAll = new JButton(" Show All ");
        add(showAll);
        showAll.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                shapeExample.showAll();
            }
        });


        threadCombo = new JComboBox(threads);
        add(threadCombo);
        threadCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                shapeExample.showThreas("" + threadCombo.getSelectedItem());
            }
        });

        JButton showAllThreads = new JButton(" Show All Threads ");
        add(showAllThreads);
        showAllThreads.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                shapeExample.showAllThreads();
            }
        });
    }
}
