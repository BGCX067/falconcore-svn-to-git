package org.gatech.cs;

import falcon.data.classes.ExecInfo;
import falcon.data.classes.InstInfo;
import falcon.data.classes.InstMethod;
import falcon.data.classes.InstVariable;
import falcon.data.xml.XMLExecInfo;
import falcon.data.xml.XMLInstInfo;
import org.gatech.RunTimeStack;
import org.gatech.gui.*;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;


public class Folcon {
    private DefaultTableModel model;
    private DefaultTableModel model2;
    private JFrame frame;
    private JTable table;
    private JTable table2;
    private String currentFileName;
    private JTextArea jep;
    private OrderValidationPanelWithTable orderValidationPanelWithTable;
    private ProjectInfoBean projectInfoBean;
    private AtomicityVialationPanelWithTable atomicityVialationPanelWithTable;
    private RunTimeStack content;
    private String data2[][];

    public static void main(String[] args) {
        new Folcon();
    }

    private Folcon getMe() {
        return this;
    }

    public Folcon() {
        JPanel shapeExample = new JPanel();
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
        }
        frame = new JFrame(" Thread Visualization");

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("File");
        JMenu helpMenu = new JMenu("Help");
        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        fileMenu.setSize(100, 30);
        JMenuItem createAction = new JMenuItem("Create New Project");
        JMenuItem openAction = new JMenuItem("Open Project");
        JMenuItem exitAction = new JMenuItem("Exit");
        JMenuItem aboutus = new JMenuItem("About US");
        fileMenu.add(createAction);

        fileMenu.addSeparator();
        fileMenu.add(openAction);
         fileMenu.addSeparator();
        fileMenu.add(exitAction);
        helpMenu.add(aboutus);

        aboutus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new AboutUSDailog(frame);
            }
        });

        createAction.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ProjectSelector(frame, getMe());

            }
        });

        openAction.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fc.setFileFilter(new FalconFileFilter());
                int returnVal = fc.showOpenDialog(frame);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    try {
                        openProject(file.getAbsolutePath());
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        frame.addWindowListener(new ExitListener());
        frame.setVisible(true);
        frame.setLayout(new BorderLayout());
        shapeExample.setPreferredSize(new Dimension(700, 500));
        frame.add(shapeExample, BorderLayout.CENTER);
        Toolkit tk = Toolkit.getDefaultToolkit();
        int xSize = ((int) tk.getScreenSize().getWidth());
        int ySize = ((int) tk.getScreenSize().getHeight());
        frame.setSize(xSize, ySize);
        JScrollPane rightPane = new JScrollPane();
        rightPane.setPreferredSize(new Dimension(xSize/2 , 500));
        jep = new JTextArea();

        rightPane.setViewportView(jep);
        JPanel methodInfo = new JPanel();
        JPanel variableInfo = new JPanel();
        shapeExample.setLayout(new BorderLayout());
        JTabbedPane jTabbedPane = new JTabbedPane();
        shapeExample.add(jTabbedPane, BorderLayout.CENTER);
        JPanel instrumentationLeftPanel = new JPanel();
        instrumentationLeftPanel.setPreferredSize(new Dimension((xSize -40)/2, 500));
        JPanel instrumentationPanel = new JPanel();
        instrumentationPanel.setLayout(new BorderLayout(2,30));
        instrumentationPanel.add(rightPane, BorderLayout.EAST);
        instrumentationPanel.add(instrumentationLeftPanel, BorderLayout.WEST);

        methodInfo.setLayout(new BorderLayout());

        URL url = getClass().getResource("/contest_account");
        try {
            File file = new File(url.toURI());
            textpath = file.getAbsolutePath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
//        textpath = "contest_account";
        xmlFile1 = textpath + System.getProperty("file.separator") + "instInfo.xml";
        xmlFile2 = textpath + System.getProperty("file.separator") + "execInfo.xml";

        readXMLs();
        data2 = getVariableData();
        String col[] = {"Name", "Return Type", "Class Nam"};
        model = new DefaultTableModel(getMethodInforData(), col);
        table = new JTable(model) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            public Component prepareRenderer(TableCellRenderer renderer, int Index_row, int Index_col) {
                Component comp = super.prepareRenderer(renderer, Index_row, Index_col);
                //even index, selected or not selected
//                if (Index_row % 2 == 0 ) {
//                    comp.setBackground(Color.WHITE);
//                } else {
//                    comp.setBackground(Color.BLUE);
//                }
                return comp;
            }
        };


        JTableHeader header = table.getTableHeader();
        header.setBackground(Color.yellow);
        JScrollPane pane = new JScrollPane(table);
//        table.setBackground(Color.BLACK);
        methodInfo.add(pane, BorderLayout.CENTER);
        JLabel methodLabel = new JLabel("Method Information");
        JLabel variableLabel = new JLabel("Variable Information");
        variableLabel.setForeground(Color.BLUE);
        methodLabel.setForeground(Color.BLUE);
        variableInfo.setLayout(new BorderLayout());


        String col2[] = {"V Type", "Line Number", "Signature", "File Name"};
        model2 = new DefaultTableModel(getVariableData(), col2);
        table2 = new JTable(model2) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            public Component prepareRenderer(TableCellRenderer renderer, int Index_row, int Index_col) {
                Component comp = super.prepareRenderer(renderer, Index_row, Index_col);
                //even index, selected or not selected
//                if (Index_row % 2 == 0 ) {
//                    comp.setBackground(Color.WHITE);
//                } else {
//                    comp.setBackground(Color.BLUE);
//                }
                return comp;
            }
        };
        TableColumn colm = table2.getColumnModel().getColumn(0);
        colm.setMaxWidth(80);
        colm = table2.getColumnModel().getColumn(1);
        colm.setMaxWidth(80);

        table2.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table2.getSelectedRow();
//                int col = table2.getSelectedColumn();
                if (row != -1) {
                    System.out.println(Integer.parseInt(data2[row][1]));
                    highlightLine(Integer.parseInt(data2[row][1]) - 1, data2[row][3]);
                }


            }
        });
        JTableHeader header2 = table2.getTableHeader();
        header2.setBackground(Color.yellow);
        JScrollPane pane2 = new JScrollPane(table2);
        pane2.setBackground(Color.BLACK);
        variableInfo.add(variableLabel, BorderLayout.NORTH);
        methodInfo.add(methodLabel, BorderLayout.NORTH);
        variableInfo.add(pane2, BorderLayout.CENTER);

//        methodInfo.setPreferredSize(new Dimension(500,250));

        variableInfo.setBackground(Color.BLACK);
        methodInfo.setBackground(Color.BLACK);

        variableInfo.setBorder(BorderFactory.createRaisedBevelBorder());
        methodInfo.setBorder(BorderFactory.createRaisedBevelBorder());
        instrumentationLeftPanel.setLayout(new BorderLayout());
        instrumentationLeftPanel.add(methodInfo, BorderLayout.NORTH);
        instrumentationLeftPanel.add(variableInfo, BorderLayout.SOUTH);

        methodInfo.setPreferredSize(new Dimension((shapeExample.getWidth() - 10), ((shapeExample.getHeight() - 100) / 2)));

        variableInfo.setPreferredSize(new Dimension((shapeExample.getWidth() - 10), shapeExample.getHeight() / 2));

        orderValidationPanelWithTable = new OrderValidationPanelWithTable(shapeExample.getWidth(), shapeExample.getHeight(), execInfo, instInfo, this);
        atomicityVialationPanelWithTable = new AtomicityVialationPanelWithTable(shapeExample.getWidth(), shapeExample.getHeight(), execInfo, instInfo, this);
        JLabel label1 = new JLabel();
        label1.setText("You are in area of Tab1");
        jTabbedPane.addTab("Instrumentation", instrumentationPanel);
        jTabbedPane.addTab("Order Violations", orderValidationPanelWithTable);
        jTabbedPane.addTab("Atomicity Violations", atomicityVialationPanelWithTable);


        Ruler hRule = new Ruler(1000, Ruler.HORIZONTAL);
        hRule.setPointModel(new PointModel());
        JScrollPane scrollPane = new JScrollPane();
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        hRule.setBorder(BorderFactory.createEtchedBorder());
        content = new RunTimeStack(this, instInfo.getInstVariableList());
        ControlPanel controlPanel = new ControlPanel(content, content.getVariables(),content.getThreads());
//        content.reconfigure(instInfo.getInstVariableList());
        mainPanel.add(controlPanel, BorderLayout.NORTH);
        mainPanel.add(content, BorderLayout.CENTER);
        mainPanel.add(hRule, BorderLayout.SOUTH);

        scrollPane.getViewport().add(mainPanel);
        jTabbedPane.addTab("Execution Trace", scrollPane);
        jep.setBackground(Color.BLACK);
        jep.setForeground(Color.WHITE);
    }

    public void highlight(String pattern, String fileName) {
        System.out.println("pattern = " + pattern);
        System.out.println("fileName = " + fileName);
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


    public void highlightLine(int number, String fileName) {
        if (fileName != null && !fileName.equals(currentFileName) && projectInfoBean != null) {
            String fullFileName = projectInfoBean.getFileName(fileName);
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
//            int length = 0;

            for (int i = 0; i <= number - 1; i++) {
                String s = value[i];
                count = count + s.length();
//                length = s.length() + 2;
            }

            int so = jep.getLineStartOffset(number);
            int eo = jep.getLineEndOffset(number);
            Highlighter hh = jep.getHighlighter();
            hh.removeAllHighlights();
            Highlighter.HighlightPainter painter = new DefaultHighlighter.DefaultHighlightPainter(Color.RED);
            hh.addHighlight(so, eo, painter);
            jep.repaint();
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    /////////////////////////
//    private String textpath = "D:\\academics\\fall2009\\SoftwareAnalysis\\project\\Thread_visualization\\20091002_xml\\xmls\\contest_account";
    private String textpath = "contest_account";
    private String xmlFile1 = textpath + System.getProperty("file.separator") + "instInfo.xml";
    private String xmlFile2 = textpath + System.getProperty("file.separator") + "execInfo.xml";

    private static InstInfo instInfo = new InstInfo();
    private static ExecInfo execInfo = new ExecInfo();

    /**
     * read XMLs and build data structures
     */
    private void readXMLs() {
        XMLInstInfo xmlInstInfo = new XMLInstInfo();
        instInfo = xmlInstInfo.deSerializeInstInfo(xmlFile1);

        XMLExecInfo xmlExecInfo = new XMLExecInfo();
        execInfo = xmlExecInfo.deSerializeExecInfo(xmlFile2);
    }


    public void reconfigure(ProjectInfoBean bean) {
        projectInfoBean = bean;
        xmlFile1 = bean.getInstFileName();
        xmlFile2 = bean.getExtFileName();
        readXMLs();
        data2 = getVariableData();
        String col[] = {"Name", "Return Type", "Class Nam"};
//        model = new DefaultTableModel(getMethodInforData(), col);
        String col2[] = {"V Type", "Line Number", "Signature", "File Name"};
//        model2 = new DefaultTableModel(getVariableData(), col2);
        model.setDataVector(getMethodInforData(), col);
        model2.setDataVector(getVariableData(), col2);

        frame.getContentPane().repaint();

        table.revalidate();
        table2.revalidate();
        TableColumn colm = table2.getColumnModel().getColumn(0);
        colm.setMaxWidth(80);
        colm = table2.getColumnModel().getColumn(1);
        colm.setMaxWidth(80);
        orderValidationPanelWithTable.reconfigure(execInfo, instInfo);
        atomicityVialationPanelWithTable.reconfigure(execInfo, instInfo);
        content.reconfigure(instInfo.getInstVariableList());
        jep.setText("");
    }

    private void openProject(String fileName) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        org.w3c.dom.Document document = db.parse(new FileInputStream(fileName));
        Element element = document.getDocumentElement();
        NodeList nodelist = element.getChildNodes();
        ProjectInfoBean bean = new ProjectInfoBean();
        for (int i = 0; i < nodelist.getLength(); i++) {
            org.w3c.dom.Node node = nodelist.item(i);
            if (node instanceof Element) {
                Element ele = (Element) node;
                if ("InstFile".equals(ele.getNodeName())) {
                    bean.setInstFileName(ele.getTextContent());
                } else if ("ExtFile".equals(ele.getNodeName())) {
                    bean.setExtFileName(ele.getTextContent());
                } else if ("source".equals(ele.getNodeName())) {
                    NodeList files = ele.getChildNodes();
                    for (int j = 0; j < files.getLength(); j++) {
                        org.w3c.dom.Node filr = files.item(j);
                        if (filr instanceof Element) {
                            Element fileEle = (Element) filr;
                            File file = new File(fileEle.getTextContent());
                            bean.addToList(file.getName(), file.getAbsolutePath());
                        }
                    }
                }
            }

        }
        reconfigure(bean);
    }

    public void saveProject(ProjectInfoBean bean) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("<falcon>");
        buffer.append("<InstFile>").append(bean.getInstFileName()).append("</InstFile>");
        buffer.append("<ExtFile>").append(bean.getExtFileName()).append("</ExtFile>");
        Iterator values = bean.getSourceList().values().iterator();
        buffer.append("<source>");
        while (values.hasNext()) {
            String filename = (String) values.next();
            buffer.append("<file>").append(filename).append("</file>");

        }
        buffer.append("</source>");
        buffer.append("</falcon>");
        try {
            File file = new File("D:\\Demo");
            File falconFile = new File(file,bean.getProjectName() + ".falcon");
            PrintWriter writer = new PrintWriter(new FileOutputStream(falconFile), true);
            writer.println(buffer.toString());
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public String getMethodName(InstMethod method) {
        if (method == null) {
            return "N/A";
        }
        String methodSig = method.getMsig();
        int index = methodSig.indexOf('(');
        String v = methodSig.substring(0, index);
        index = v.lastIndexOf(' ');
        v = v.substring(index);
        // &lt;init&gt;
        v = v.replaceAll("<", "");
        v = v.replaceAll(">", "");
        return v;
    }

    private String getRetrunType(InstMethod method) {
        String methodSig = method.getMsig();
        int index = methodSig.indexOf('(');
        String v = methodSig.substring(0, index);
        index = v.lastIndexOf(':');
        v = v.substring(index + 1);
        v = v.trim();
        String values[] = v.split("\\ ");
        return values[0];
    }


    private String getClassName(InstMethod method) {
        if (method == null) {
            return "N/A";
        }
        String methodSig = method.getMsig();
        int index = methodSig.indexOf(':');
        String v = methodSig.substring(0, index);
        index = v.lastIndexOf('<');
        v = v.substring(index + 1);
        return v;
    }

    public String fileName(InstMethod method) {
        String fileName = getClassName(method);
        int index = fileName.lastIndexOf('.');
        fileName = fileName.substring(index + 1);
        return fileName + ".java";
    }

    private String[][] getMethodInforData() {
        java.util.List<InstMethod> methods = instInfo.getInstMethodList();
        int size = methods.size();
        String data[][] = new String[size][3];
        for (int i = 0; i < methods.size(); i++) {
            InstMethod instMethod = methods.get(i);
            String methodName = getMethodName(instMethod);
            String returnType = getRetrunType(instMethod);
            data[i] = new String[]{methodName, returnType, getClassName(instMethod)};
        }
        return data;
    }

    private String[][] getVariableData() {
        List<InstVariable> variables = instInfo.getInstVariableList();
        String data2[][] = new String[variables.size()][4];
        for (int i = 0; i < variables.size(); i++) {
            InstVariable instVariable = variables.get(i);
            data2[i] = new String[]{instVariable.getVtype(), "" + instVariable.getVline(),
                    getVariableName(instVariable.getVsig()), instVariable.getVfile()};
        }
        return data2;
    }

    public static String getVariableName(String varSig) {
        String str = varSig;
        str = str.replaceAll("<", "");
        str = str.replaceAll(">", "");
        int index = str.indexOf("(");
        if (index > 0) {
            return "parameter";
        }
        String values[] = str.split("\\ ");
        int size = values.length;
        return values[size - 1];
    }

    private class FalconFileFilter extends javax.swing.filechooser.FileFilter {
        public boolean accept(File f) {
            if (f.isDirectory()) return true;
            return f.isFile() && f.getName().endsWith(".falcon");
        }

        public String getDescription() {
            return null;
        }
    }

    public ProjectInfoBean getProjectInfoBean() {
        return projectInfoBean;
    }

    public JFrame getFrame() {
        return frame;
    }
}