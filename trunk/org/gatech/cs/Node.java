package org.gatech.cs;

import java.awt.*;
import java.util.Random;


public class Node {
    public int x;
    public int y;
    public String label;
    private boolean showlabel;
    private boolean read;
    double offset;
    public String threadKey;
    public Color color;
    public String fileName;
    public int line;

    public Node(String label, boolean read, String fileName, int line) {
        this.label = label;
        this.read = read;
        this.fileName = fileName;
        this.line = line;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}
