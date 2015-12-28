package org.gatech.cs;

import java.util.ArrayList;
import java.awt.*;


public class ThreadLine {

    public int start;
    public int width;
    public int height;
    public Color color;
    public String label;
    private int offset;
    private int middle;


    public ThreadLine(int start, int width, int height, Color color, String label, int offset) {
        this.start = start;
        this.width = width;
        this.height = height;
        this.color = color;
        this.label = label;
        this.offset = offset;

        middle = start + (height / 2);
    }

    public int getMiddle() {
        return middle;
    }
}
