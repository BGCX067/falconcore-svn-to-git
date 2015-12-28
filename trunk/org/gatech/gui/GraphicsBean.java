package org.gatech.gui;

import java.awt.*;


public class GraphicsBean {
    private String name;
    private String className;
    private int line;
    public int x;
    public int y;
    private  boolean isVariable;
    private boolean isRead;

    public GraphicsBean(String name, String className, int line, boolean isVariable, boolean isRead) {
        this.name = name;
        this.className = className;
        this.line = line;
        this.isRead = isRead;
        this.isVariable = isVariable;
    }

    public boolean isVariable() {
        return isVariable;
    }

    public boolean isRead() {
        return isRead;
    }

    public String getName() {
        return name;
    }

    public String getClassName() {
        return className;
    }

    public int getLine() {
        return line;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
