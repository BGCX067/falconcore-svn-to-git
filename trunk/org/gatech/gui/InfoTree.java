package org.gatech.gui;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.util.Map;

import falcon.data.classes.InstVariable;
import falcon.data.classes.InstMethod;

public class InfoTree extends JPanel {
    DefaultMutableTreeNode root;
    JTree tree;
    Map<Integer, InstMethod> methodMap;

    ImageIcon classIcon = new ImageIcon("images/class.png");
    ImageIcon methodIcon = new ImageIcon("images/field.png");
    ImageIcon variableIcon = new ImageIcon("images/variable.png");
    ImageIcon stackleIcon = new ImageIcon("images/static.png");
    ImageIcon lineIcon = new ImageIcon("images/line.png");
    ImageIcon nameIcon = new ImageIcon("images/name.png");
    ImageIcon rootIcon = new ImageIcon("images/root.png");

    public InfoTree(int width, int heigh, Map<Integer, InstMethod> methodMap) {
        root = new DefaultMutableTreeNode(new TreeNode("", TreeNode.ROOT));
        this.methodMap = methodMap;
        tree = new JTree(root);
        tree.setCellRenderer(new CustomIconRenderer());
        setLayout(new BorderLayout());
        add(new JScrollPane(tree), BorderLayout.CENTER);
        setVisible(true);
        setSize(width, heigh);

    }

    public void update(Map<Integer, InstMethod> methodMap, Validationbean bean, boolean isAtomicity) {
        this.methodMap = methodMap;
        root.removeAllChildren();
        DefaultMutableTreeNode var1 = new DefaultMutableTreeNode(new TreeNode("Variable1", TreeNode.VARIABLE));
        DefaultMutableTreeNode var2 = new DefaultMutableTreeNode(new TreeNode("Variable2", TreeNode.VARIABLE));
        DefaultMutableTreeNode var3 = new DefaultMutableTreeNode(new TreeNode("Variable3", TreeNode.VARIABLE));
        processNode(bean.getVar1(), var1, isAtomicity, bean);
        processNode(bean.getVar2(), var2, isAtomicity, bean);
        root.add(var1);
        root.add(var2);
        if (isAtomicity) {
            processNode(bean.getVar3(), var3, isAtomicity, bean);
            root.add(var3);
        }
        DefaultMutableTreeNode stack1 = new DefaultMutableTreeNode(new TreeNode("Stack1", TreeNode.STACK));
        String values[] = AtomicityVialationPanelWithTable.getMethodData(methodMap, bean, 0);
        stack1.add(new DefaultMutableTreeNode(new TreeNode(AtomicityVialationPanelWithTable.getFormatedString(values[0], false), TreeNode.METHOD)));
        if (values.length > 1) {
            stack1.add(new DefaultMutableTreeNode(new TreeNode(AtomicityVialationPanelWithTable.getFormatedString(values[1], false), TreeNode.METHOD)));
        }
        root.add(stack1);
        DefaultMutableTreeNode stack2 = new DefaultMutableTreeNode(new TreeNode("Stack2", TreeNode.STACK));
        values = AtomicityVialationPanelWithTable.getMethodData(methodMap, bean, 1);
        stack2.add(new DefaultMutableTreeNode(new TreeNode(AtomicityVialationPanelWithTable.getFormatedString(values[0], false), TreeNode.METHOD)));
        if (values.length > 1) {
            stack2.add(new DefaultMutableTreeNode(new TreeNode(AtomicityVialationPanelWithTable.getFormatedString(values[1], false), TreeNode.METHOD)));
        }
        root.add(stack2);

        if (isAtomicity) {
            DefaultMutableTreeNode stack3 = new DefaultMutableTreeNode(new TreeNode("Stack3", TreeNode.STACK));
            values = AtomicityVialationPanelWithTable.getMethodData(methodMap, bean, 1);
            stack3.add(new DefaultMutableTreeNode(new TreeNode(AtomicityVialationPanelWithTable.getFormatedString(values[0], false), TreeNode.METHOD)));
            if (values.length > 1) {
                stack3.add(new DefaultMutableTreeNode(new TreeNode(AtomicityVialationPanelWithTable.getFormatedString(values[1], false), TreeNode.METHOD)));
            } else {
                stack3.add(new DefaultMutableTreeNode(new TreeNode("N/A", TreeNode.METHOD)));
            }

            root.add(stack3);
        }

        tree.updateUI();
    }

    private void processNode(InstVariable variable, DefaultMutableTreeNode parent, boolean isAtomicity, Validationbean bean) {
        DefaultMutableTreeNode name = new DefaultMutableTreeNode(
                new TreeNode(AtomicityVialationPanelWithTable.getFormatedString(variable.getVsig(), true), TreeNode.NAME)
        );
        parent.add(name);
        parent.add(new DefaultMutableTreeNode(new TreeNode(variable.getVtype(), TreeNode.VTYPE)));
        DefaultMutableTreeNode child = new DefaultMutableTreeNode(new TreeNode(variable.getVfile(), TreeNode.CLASS));
        child.add(new DefaultMutableTreeNode(new TreeNode("" + variable.getVline(), TreeNode.LINE)));
        parent.add(child);


    }

    class CustomIconRenderer extends DefaultTreeCellRenderer {


        public Component getTreeCellRendererComponent(JTree tree,
                                                      Object value, boolean sel, boolean expanded, boolean leaf,
                                                      int row, boolean hasFocus) {

            super.getTreeCellRendererComponent(tree, value, sel,
                    expanded, leaf, row, hasFocus);

            Object nodeObj = ((DefaultMutableTreeNode) value).getUserObject();
            int type = ((TreeNode) nodeObj).getType();
            switch (type) {
                case TreeNode.VARIABLE:
                    setIcon(variableIcon);
                    break;
                case TreeNode.METHOD:
                    setIcon(methodIcon);
                    break;
                case TreeNode.CLASS:
                    setIcon(classIcon);
                    break;
                case TreeNode.NAME:
                    setIcon(nameIcon);
                    break;
                case TreeNode.LINE:
                    setIcon(lineIcon);
                    break;
                case TreeNode.STACK:
                    setIcon(stackleIcon);
                    break;
                case TreeNode.ROOT:
                    setIcon(rootIcon);
                    break;

                default:
                    setIcon(rootIcon);
                    break;

            }

            return this;
        }
    }

    class TreeNode {
        public static final int VARIABLE = 1;
        public static final int VTYPE = 7;
        public static final int METHOD = 2;
        public static final int STACK = 3;
        public static final int CLASS = 4;
        public static final int NAME = 5;
        public static final int ROOT = 8;
        public static final int LINE = 6;
        String name;
        int type;

        TreeNode(String name, int type) {
            this.name = name;
            this.type = type;
        }

        public String toString() {
            return name;
        }

        public int getType() {
            return type;
        }


    }
}
