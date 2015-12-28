package org.gatech.gui;

import org.gatech.cs.Folcon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ProjectSelector extends JDialog {
    JLabel initLabel;
    JLabel extLable;
    JTextField inittext;
    JTextField projetcNameText;
    JTextField exteText;
    JTextArea sourcetext;
    JScrollPane sp_sourcetext;
    JLabel label_2;
    JButton intButton;
    JButton extButton;
    JButton sourceButton;
    JPanel panel_2;
    JButton okButton;
    JButton cancelButton;

    JFileChooser fc;
    JFileChooser dc;
    private ProjectInfoBean projectInfoBean;
    private Folcon mainFrame;

    public ProjectSelector(JFrame parent, Folcon mainFrame) {
        super(parent);
        this.mainFrame = mainFrame;
        configure();

    }

    public ProjectInfoBean getProjectInfoBean() {
        return projectInfoBean;
    }

    public void configure() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
        }
        projectInfoBean = new ProjectInfoBean();
        fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);

        dc = new JFileChooser();
        dc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        ProjectSelectorLayout customLayout = new ProjectSelectorLayout();

        getContentPane().setFont(new Font("Helvetica", Font.PLAIN, 12));
        getContentPane().setLayout(customLayout);

        initLabel = new JLabel("Select Instrument File   : ");
        getContentPane().add(initLabel);

        extLable = new JLabel("Select Execution File     : ");
        getContentPane().add(extLable);

        inittext = new JTextField();
        getContentPane().add(inittext);

        exteText = new JTextField();
        getContentPane().add(exteText);

        sourcetext = new JTextArea();
        sp_sourcetext = new JScrollPane(sourcetext);
        getContentPane().add(sp_sourcetext);

        label_2 = new JLabel("Select Source Files        :");
        getContentPane().add(label_2);

        intButton = new JButton(":");
        getContentPane().add(intButton);

        extButton = new JButton(":");
        getContentPane().add(extButton);

        sourceButton = new JButton(":");
        getContentPane().add(sourceButton);

        panel_2 = new JPanel();
        getContentPane().add(panel_2);

        projetcNameText = new JTextField();


        okButton = new JButton("OK");
        getContentPane().add(okButton);
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getMe().setVisible(false);
                projectInfoBean.setProjectName(projetcNameText.getText());
                mainFrame.saveProject(projectInfoBean);
                mainFrame.reconfigure(projectInfoBean);
            }
        });


        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getMe().setVisible(false);
            }
        });


        getContentPane().add(cancelButton);


        intButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int returnVal = fc.showOpenDialog(getMe());

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    //This is where a real application would open the file.
                    inittext.setText(file.getAbsolutePath());
                    projectInfoBean.setInstFileName(file.getAbsolutePath());
                }

            }
        });

        extButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int returnVal = fc.showOpenDialog(getMe());

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    //This is where a real application would open the file.
                    exteText.setText(file.getAbsolutePath());
                    projectInfoBean.setExtFileName(file.getAbsolutePath());

                }

            }
        });


        sourceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int returnVal = dc.showOpenDialog(getMe());

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = dc.getSelectedFile();
                    //This is where a real application would open the file.
                    String str = sourcetext.getText();
                    if (file.isDirectory()) {
                        File files[] = file.listFiles();
                        for (int i = 0; i < files.length; i++) {
                            File file1 = files[i];
                            if (file1.isFile() && file1.getName().endsWith(".java")) {
                                if ("".equals(str)) {
                                    str = file1.getName();
                                } else {
                                    str = str + "\n" + file1.getName();
                                }
                                projectInfoBean.addToList(file1.getName(), file1.getAbsolutePath());
                            }
                        }
                    } else {
                        if ("".equals(str)) {
                            str = file.getName();
                        } else {
                            str = str + "\n" + file.getName();
                        }
                        projectInfoBean.addToList(file.getName(), file.getAbsolutePath());
                    }
                    sourcetext.setText(str);
                }

            }
        });
        getContentPane().add(new JLabel("Project Name"));
        getContentPane().add(projetcNameText);

        setSize(getPreferredSize());
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
//        setBounds((screenSize.width - this.getWidth()) / 2,
//                (screenSize.height - this.getHeight()) / 2,
//                400,
//                300);
        setLocationRelativeTo(null);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
//                getMe().setVisible(false);
            }
        });
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        setVisible(true);
    }

    public JDialog getMe() {
        return this;
    }

}

class ProjectSelectorLayout implements LayoutManager {

    public ProjectSelectorLayout() {
    }

    public void addLayoutComponent(String name, Component comp) {
    }

    public void removeLayoutComponent(Component comp) {
    }

    public Dimension preferredLayoutSize(Container parent) {
//        Dimension dim = new Dimension(0, 0);
        Dimension dim = java.awt.Toolkit.getDefaultToolkit().getScreenSize();


        Insets insets = parent.getInsets();
        dim.width = 527 + insets.left + insets.right;
        dim.height = 272 + insets.top + insets.bottom;

        return dim;
    }

    public Dimension minimumLayoutSize(Container parent) {
        Dimension dim = new Dimension(0, 0);
        return dim;
    }

    public void layoutContainer(Container parent) {
        Insets insets = parent.getInsets();

        Component c;
        c = parent.getComponent(0);
        if (c.isVisible()) {
            c.setBounds(insets.left + 8, insets.top + 8, 136, 24);
        }
        c = parent.getComponent(1);
        if (c.isVisible()) {
            c.setBounds(insets.left + 8, insets.top + 40, 136, 24);
        }
        c = parent.getComponent(2);
        if (c.isVisible()) {
            c.setBounds(insets.left + 152, insets.top + 8, 344, 24);
        }
        c = parent.getComponent(3);
        if (c.isVisible()) {
            c.setBounds(insets.left + 152, insets.top + 40, 344, 24);
        }
        c = parent.getComponent(4);
        if (c.isVisible()) {
            c.setBounds(insets.left + 152, insets.top + 72, 344, 90);
        }
        c = parent.getComponent(5);
        if (c.isVisible()) {
            c.setBounds(insets.left + 8, insets.top + 72, 136, 24);
        }
        c = parent.getComponent(6);
        if (c.isVisible()) {
            c.setBounds(insets.left + 496, insets.top + 8, 18, 24);
        }
        c = parent.getComponent(7);
        if (c.isVisible()) {
            c.setBounds(insets.left + 496, insets.top + 40, 18, 24);
        }
        c = parent.getComponent(8);
        if (c.isVisible()) {
            c.setBounds(insets.left + 496, insets.top + 72, 18, 24);
        }
        c = parent.getComponent(9);
        if (c.isVisible()) {
//            c.setBounds(insets.left + 32, insets.top + 192, 480, 56);
        }
        c = parent.getComponent(10);
        if (c.isVisible()) {
            c.setBounds(insets.left + 240, insets.top + 208, 72, 24);
        }
        c = parent.getComponent(11);
        if (c.isVisible()) {
            c.setBounds(insets.left + 320, insets.top + 208, 72, 24);
        }
        c = parent.getComponent(12);
        if (c.isVisible()) {
            c.setBounds(insets.left + 8, insets.top + 170, 136, 24);
        }
        c = parent.getComponent(13);
        if (c.isVisible()) {
            c.setBounds(insets.left + 152, insets.top + 170, 344, 24);
        }
    }
}
