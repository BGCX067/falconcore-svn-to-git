package org.gatech.gui;

import falcon.data.classes.*;
import org.gatech.cs.Folcon;
import org.gatech.cs.HighLightContainer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class AtomosityVialationPanelWithTable extends JPanel {

    private ExecInfo execInfo;
    private InstInfo xmlInstInfo;
    private JLabel nameLabel;
    private JLabel passLabel;
    private JLabel failLabel;
    private JLabel suspLabel;
    private GraphicsPanel var1Label;
    private GraphicsPanel var3Label;
    private GraphicsPanel var2Label;
    private GraphicsPanel stack1Label;
    private GraphicsPanel stack2Label;
    private GraphicsPanel stack3Label;
    private Folcon main;
    Map<Integer, InstVariable> variableMap;
    private Map<Integer, InstMethod> methodMap;
    JTextArea leftText;
    JTextArea rightText;
    JTextArea middleText;

    private DefaultTableModel model;
    private JTable table;
    private String tableData[][];


    private HashMap<String, Validationbean> beanMap = new HashMap<String, Validationbean>();

    public AtomosityVialationPanelWithTable(int width, int height, ExecInfo execInfo, InstInfo xmlInstInfo, Folcon main) {
        super();
        this.execInfo = execInfo;
        this.xmlInstInfo = xmlInstInfo;
        this.main = main;
        populate();
        configure(width, height);

    }

    public void configure(int width, int height) {
        setForeground(Color.BLUE);
        JPanel tablePanel = new JPanel();
        JPanel codePanel = new JPanel();

        JPanel upperPanel = new JPanel();
        JPanel lowerPanel2 = new JPanel();
        setLayout(new BorderLayout());

        tablePanel.setLayout(new BorderLayout(10, 10));

        lowerPanel2.setPreferredSize(new Dimension((width - 10) / 2, (height) / 2));
        upperPanel.setPreferredSize(new Dimension((width - 10) / 2, (height) / 2));
        tablePanel.setPreferredSize(new Dimension(width, (height + 100) / 2));
        codePanel.setPreferredSize(new Dimension(width, (height - 100) / 2));
        tablePanel.add(lowerPanel2, BorderLayout.WEST);
        tablePanel.add(upperPanel, BorderLayout.EAST);


        JLabel var1File = new JLabel("Variable 1 File : ");
        JLabel var2file = new JLabel("Variable 2 File : ");
        JLabel var3file = new JLabel("Variable 3 File : ");
        var1File.setForeground(Color.BLUE);
        var2file.setForeground(Color.BLUE);
        var3file.setForeground(Color.BLUE);

        add(codePanel, BorderLayout.SOUTH);
        add(tablePanel, BorderLayout.CENTER);

        JScrollPane rightPane = new JScrollPane();
        JScrollPane lefttPane = new JScrollPane();
        JScrollPane middlePane = new JScrollPane();

        JPanel var1Panle = new JPanel();
        var1Panle.setLayout(new BorderLayout());
        var1Panle.add(var1File, BorderLayout.NORTH);
        var1Panle.add(lefttPane, BorderLayout.CENTER);
        var1Panle.setBackground(Color.WHITE);

        JPanel var2Panle = new JPanel();
        var2Panle.setBackground(Color.WHITE);
        var2Panle.setLayout(new BorderLayout());
        var2Panle.add(var2file, BorderLayout.NORTH);
        var2Panle.add(rightPane, BorderLayout.CENTER);

        JPanel var3Panle = new JPanel();
        var3Panle.setBackground(Color.WHITE);
        var3Panle.setLayout(new BorderLayout());
        var3Panle.add(var3file, BorderLayout.NORTH);
        var3Panle.add(middlePane, BorderLayout.CENTER);


        var1Panle.setPreferredSize(new Dimension((width - 10) / 3, (height) / 2));
        var2Panle.setPreferredSize(new Dimension((width - 10) / 3, (height) / 2));
        var3Panle.setPreferredSize(new Dimension((width - 10) / 3, (height) / 2));

//        rightPane.setPreferredSize(new Dimension(height/2 , 500));
        leftText = new FolconTextArea(var1Panle, main.getFrame(),main);
        rightText = new FolconTextArea(var3Panle, main.getFrame(),main);
        middleText = new FolconTextArea(var2Panle, main.getFrame(),main);
        leftText.setBackground(Color.BLACK);
        rightText.setBackground(Color.BLACK);
        middleText.setBackground(Color.BLACK);

        leftText.setForeground(Color.WHITE);
        rightText.setForeground(Color.WHITE);
        middleText.setForeground(Color.WHITE);

        rightPane.setViewportView(rightText);
        lefttPane.setViewportView(leftText);
        middlePane.setViewportView(middleText);
        codePanel.setLayout(new BorderLayout(10, 10));
        codePanel.add(var1Panle, BorderLayout.WEST);
        codePanel.add(var2Panle, BorderLayout.CENTER);
        codePanel.add(var3Panle, BorderLayout.EAST);


        String col[] = {"Name", "Pass", "Fail", "Susp"};
        model = new DefaultTableModel(getData(), col);
//      model.addColumn("Grade");
        table = new JTable(model) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            public Component prepareRenderer(TableCellRenderer renderer,
                                             int rowIndex, int vColIndex) {
                Component c = super.prepareRenderer(renderer, rowIndex, vColIndex);
                if (vColIndex == 3) {
                    float hue = Float.parseFloat(tableData[rowIndex][3]);
                    System.out.println("hue = " + hue);
//                    hue = (hue /100) * 360;
//                    System.out.println(hue);
//                    Color cl = Color.getHSBColor(hue, 0f, 1f);
                    c.setBackground(getColor((hue / 100)));
                } else {
                    // If not shaded, match the table's background
                    c.setBackground(getBackground());
                }
                if (isCellSelected(rowIndex, vColIndex)) {
                    c.setBackground(Color.BLUE);
                }

                return c;
            }
        };

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    setSelected(tableData[row][0]);
                }

            }
        });
        JScrollPane pane = new JScrollPane(table);
        lowerPanel2.setLayout(new BorderLayout());
        lowerPanel2.add(pane, BorderLayout.CENTER);
        upperPanel.setBackground(Color.BLACK);
        upperPanel.setForeground(Color.BLUE);

        upperPanel.setLayout(new SpringLayout());
        reconfigure(execInfo, xmlInstInfo);

        Dimension dim = new Dimension(60, 35);

        Validationbean bean = beanMap.get("Test0");
        JLabel label4 = new JLabel("Test Name");
        upperPanel.add(label4);
        label4.setForeground(Color.WHITE);
        nameLabel = new JLabel(bean.getTestName());
        nameLabel.setForeground(Color.WHITE);
        upperPanel.add(nameLabel);

        JLabel label5 = new JLabel("Pass Count");
        upperPanel.add(label5);
        label5.setForeground(Color.WHITE);
        passLabel = new JLabel("" + bean.getPass());
        passLabel.setForeground(Color.WHITE);
        upperPanel.add(passLabel);

        JLabel label6 = new JLabel("Fail Count");
        upperPanel.add(label6);
        label6.setForeground(Color.WHITE);
        failLabel = new JLabel("" + bean.getFail());
        failLabel.setForeground(Color.WHITE);
        upperPanel.add(failLabel);

        JLabel label7 = new JLabel("Susp Count");
        upperPanel.add(label7);
        label7.setForeground(Color.WHITE);
        suspLabel = new JLabel("" + bean.getSusp());
        suspLabel.setForeground(Color.WHITE);
        upperPanel.add(suspLabel);

        JLabel jLabel = new JLabel("Variable 1");
        jLabel.setForeground(Color.WHITE);
        jLabel.setPreferredSize(dim);
        upperPanel.add(jLabel);
        InstVariable var1 = bean.getVar1();
        var1Label = new GraphicsPanel(new GraphicsBean[]{new GraphicsBean("Var1", var1.getVfile(),
                var1.getVline(), true,
                getVariableType(var1.getVtype()))}, new HighLightContainer(main, leftText));
        var1Label.setPreferredSize(var1Label.getDimension());
        var1Label.setForeground(Color.WHITE);
        upperPanel.add(var1Label);


        JLabel jLabel1 = new JLabel("Variable 2");
        jLabel1.setForeground(Color.WHITE);
        jLabel1.setPreferredSize(dim);
        upperPanel.add(jLabel1);
        var1 = bean.getVar2();
        var2Label = new GraphicsPanel(new GraphicsBean[]{new GraphicsBean("Var2", var1.getVfile(), var1.getVline(), true,
                getVariableType(var1.getVtype()))}, new HighLightContainer(main, rightText));
        var2Label.setPreferredSize(var2Label.getDimension());
        var2Label.setForeground(Color.WHITE);
        upperPanel.add(var2Label);


        JLabel lblvar3 = new JLabel("Variable 3");
        lblvar3.setForeground(Color.WHITE);
        lblvar3.setPreferredSize(dim);
        upperPanel.add(lblvar3);
        var1 = bean.getVar3();
        var3Label = new GraphicsPanel(new GraphicsBean[]{new GraphicsBean("Var3", var1.getVfile(), var1.getVline(), true,
                getVariableType(var1.getVtype()))}, new HighLightContainer(main, middleText));
        var3Label.setPreferredSize(var3Label.getDimension());
        var3Label.setForeground(Color.WHITE);
        upperPanel.add(var3Label);

        JLabel label = new JLabel("Stack 1");
        label.setForeground(Color.WHITE);
        label.setPreferredSize(dim);
        upperPanel.add(label);
        stack1Label = new GraphicsPanel(getData(bean, 0), new HighLightContainer(main, leftText));
        stack1Label.setForeground(Color.WHITE);
        upperPanel.add(stack1Label);

        JLabel label1 = new JLabel("Stack 2");
        label1.setForeground(Color.WHITE);
        label1.setPreferredSize(dim);
        upperPanel.add(label1);
        stack2Label = new GraphicsPanel(getData(bean, 1), new HighLightContainer(main, rightText));
        stack2Label.setForeground(Color.WHITE);
        upperPanel.add(stack2Label);

        JLabel lblstac3 = new JLabel("Stack 3");
        lblstac3.setForeground(Color.WHITE);
        lblstac3.setPreferredSize(dim);
        upperPanel.add(lblstac3);
        stack3Label = new GraphicsPanel(getData(bean, 2), new HighLightContainer(main, middleText));
        stack3Label.setForeground(Color.WHITE);
        upperPanel.add(stack3Label);


        setSelected(bean.getTestName());


        Utils.makeCompactGrid(upperPanel, 10, 2, 6, 6, 30, 10);

    }

    private void populate() {
        java.util.List<InstVariable> variableList = xmlInstInfo.getInstVariableList();
        java.util.List<InstMethod> methodList = xmlInstInfo.getInstMethodList();
        variableMap = new HashMap<Integer, InstVariable>();
        methodMap = new HashMap<Integer, InstMethod>();

        for (InstVariable entry : variableList) {
            variableMap.put(entry.getVid(), entry);
        }

        for (InstMethod entry : methodList) {
            methodMap.put(entry.getMid(), entry);
        }

        if (execInfo != null) {
            java.util.List<ViolationForAtomicity> list = execInfo.getAtomicityViolationList();

            for (int i = 0; i < list.size(); i++) {
                ViolationForAtomicity violationForOrder = list.get(i);
                Validationbean bean = new Validationbean();
                bean.setTestName("Test" + i);
                bean.setPass(violationForOrder.getPass());
                bean.setFail(violationForOrder.getFail());
                bean.setSusp(violationForOrder.getSusp());
                bean.setStack1(violationForOrder.getStack1());
                bean.setStack2(violationForOrder.getStack2());
                bean.setStack3(violationForOrder.getStack3());
                bean.setVar1(variableMap.get(violationForOrder.getVar1()));
                bean.setVar2(variableMap.get(violationForOrder.getVar2()));
                bean.setVar3(variableMap.get(violationForOrder.getVar3()));
                beanMap.put(bean.getTestName(), bean);
            }
        }

        repaint();
    }

    private String[][] getData() {
        int size = beanMap.size();
        String data[][] = new String[size][4];
        Iterator itr = beanMap.values().iterator();
        int count = 0;
        while (itr.hasNext()) {
            Validationbean validationbean = (Validationbean) itr.next();
            data[count] = new String[]{validationbean.getTestName(),
                    "" + validationbean.getPass(), "" + validationbean.getFail(), "" + validationbean.getSusp()};
            count++;
        }
        tableData = data;
        return data;
    }


    private void setSelected(String name) {
        Validationbean bean = beanMap.get(name);
        if (bean != null) {
            InstVariable var1 = bean.getVar1();
            nameLabel.setText(bean.getTestName());
            passLabel.setText("" + bean.getPass());
            failLabel.setText("" + bean.getFail());
            suspLabel.setText("" + bean.getSusp());
            var1Label.setBeans(new GraphicsBean[]{new GraphicsBean(getFormatedString(var1.getVsig(), true),
                    var1.getVfile(), var1.getVline(), true, getVariableType(var1.getVtype()))});


            var1 = bean.getVar2();
            var2Label.setBeans(new GraphicsBean[]{new GraphicsBean(getFormatedString(var1.getVsig(), true),
                    var1.getVfile(), var1.getVline(), true, getVariableType(var1.getVtype()))});
            stack1Label.setBeans(getData(bean, 0));

            stack2Label.setBeans(getData(bean, 1));
            var1 = bean.getVar3();
            var3Label.setBeans(new GraphicsBean[]{new GraphicsBean(getFormatedString(var1.getVsig(), true),
                    var1.getVfile(), var1.getVline(), true, getVariableType(var1.getVtype()))});
            stack3Label.setBeans(getData(bean, 2));

            leftText.getHighlighter().removeAllHighlights();
            rightText.getHighlighter().removeAllHighlights();
            middleText.getHighlighter().removeAllHighlights();

            leftText.setText("");
            rightText.setText("");
            middleText.setText("");

        }

    }

    public Color getColor(double power) {
        double H = (1 - power) * 0.4; // Hue (note 0.4 = Green, see huge chart below)
        double B = 0.9; // Saturation
        double S = 0.9; // Brightness
        return Color.getHSBColor((float) H, (float) S, (float) B);
    }


    private GraphicsBean[] getData(Validationbean bean, int index) {
        if (bean != null) {
            String stack;
            if (index == 0) {
                stack = bean.getStack1();
            } else if (index == 1) {
                stack = bean.getStack2();
            } else {
                stack = bean.getStack3();
            }

            String methods[] = getStackTraceString(stack);
            GraphicsBean beans[] = new GraphicsBean[methods.length];
            for (int i = 0; i < methods.length; i++) {
                String methodName = methods[i];
                InstMethod method = methodMap.get(Integer.decode(methodName));
                if (method == null) {
                    beans[i] = new GraphicsBean("N/A", "N/A", 12, false, false);
                } else {
                    beans[i] = new GraphicsBean(main.getMethodName(method), main.fileName(method), 12, false, false);
                }
                //TODO need to find a way to get the line number for the method

            }
            return beans;

        }
        return null;
    }

    public static String[] getMethodData(Map<Integer, InstMethod> methodMap, Validationbean bean, int index) {
        if (bean != null) {
            String stack;
            if (index == 0) {
                stack = bean.getStack1();
            } else if (index == 1) {
                stack = bean.getStack2();
            } else {
                stack = bean.getStack3();
            }


            String methods[] = getStackTraceString(stack);
            String[] array = new String[methods.length];
            for (int i = 0; i < methods.length; i++) {
                String methodName = methods[i];
                InstMethod method = methodMap.get(Integer.decode(methodName));
                if (method == null) {
                    array[i] = "N/A";
                } else {
                    array[i] = method.getMsig();
                }
                //TODO need to find a way to get the line number for the method

            }
            return array;
        }
        return null;
    }

    public static String getFormatedString(String value, boolean isVarible) {
        value = value.replaceAll("<", "");
        value = value.replaceAll(">", "");
        value = value.replaceAll("this.", "");
        if (isVarible) {
            return Folcon.getVariableName(value);
        }
        return value;
    }

    public void reconfigure(ExecInfo execInfo, InstInfo xmlInstInfo) {
        this.execInfo = execInfo;
        this.xmlInstInfo = xmlInstInfo;
        populate();
        String col[] = {"Name", "Pass", "Fail", "Susp"};
        model.setDataVector(getData(), col);
        table.revalidate();
    }

    public static String[] getStackTraceString(String mids) {
        return mids.split("\\|");
    }

    private boolean getVariableType(String vtype) {
        if (vtype.indexOf('W') >= 0) {
            return false;
        }
        return true;
    }
}

