package org.gatech;


import org.gatech.cs.Node;
import org.gatech.cs.ThreadLine;
import org.gatech.cs.Folcon;
import org.gatech.cs.ControlPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Rectangle2D;
import java.util.*;

import falcon.data.classes.InstVariable;


public class RunTimeStack extends JPanel implements MouseListener {
    private int width = 1500;
    private Rectangle2D.Double square =
            new Rectangle2D.Double(0, 0, width, 500);
    private Node pick;

    private HashMap threadLines;
    private HashMap nodeList;
    private HashMap colorList;
    private String variableName;
    private boolean showAll = true;
    private boolean showAllThreads = true;
    private String threadName;
    private ControlPanel controlPanel;
    private Folcon folcon;

    private Color[] colors = new Color[]{
            Color.BLUE,
            Color.PINK,
            Color.MAGENTA,
            Color.ORANGE,
            Color.GREEN,
            Color.RED,
            Color.YELLOW};

    public void paintComponent(Graphics g) {
        clear(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        g2d.fill(square);
        drawNodes(g2d);
    }

    private void drawNodes(Graphics2D g) {
        Iterator itr = threadLines.keySet().iterator();
        while (itr.hasNext()) {
            String key = (String) itr.next();
            ThreadLine line = (ThreadLine) threadLines.get(key);
            if (showAllThreads) {
                paintThread(g, line);
            } else if (key.equals(threadName)) {
                paintThread(g, line);
            }
        }

        Iterator itr2 = nodeList.keySet().iterator();
        while (itr2.hasNext()) {
            String key = (String) itr2.next();
            ArrayList list = (ArrayList) nodeList.get(key);
            int size = list.size();

            for (int i = 0; i < size; i++) {
                Node node = (Node) list.get(i);
                if (!showAll && !variableName.equals(node.label)) {
                    continue;
                }
                if (!showAllThreads && !threadName.equals(node.threadKey)) {
                    continue;
                }
                if (showAllThreads) {
                    if (size > 1 && (i + 1 < size)) {
                        Node next = (Node) list.get(i + 1);
//                    drawLine(g,node.x,next.x,node.y, next.y);
                        if (node.threadKey.equals(next.threadKey)) {
                            drawThickArc(g, node.x + 10, node.y + 10, next.x + 10, next.y + 10, Color.BLUE);
                        } else {
                            drawThickLine(g, node.x + 10, node.y, next.x + 10, next.y, 2, Color.BLUE);
                        }

                    }
                }

                if (node.isRead()) {
                    paintNodeForRead(g, node, g.getFontMetrics());
                } else {
                    paintNodeForWrite(g, node, g.getFontMetrics());
                }

            }
        }
    }

    public void drawThickLine(Graphics g, int x1, int y1, int x2, int y2, int thickness, Color c) {
        // The thick line is in fact a filled polygon
        g.setColor(c);
        int dX = x2 - x1;
        int dY = y2 - y1;
        // line length
        double lineLength = Math.sqrt(dX * dX + dY * dY);

        double scale = (double) (thickness) / (2 * lineLength);

        // The x and y increments from an endpoint needed to create a rectangle...
        double ddx = -scale * (double) dY;
        double ddy = scale * (double) dX;
        ddx += (ddx > 0) ? 0.5 : -0.5;
        ddy += (ddy > 0) ? 0.5 : -0.5;
        int dx = (int) ddx;
        int dy = (int) ddy;

        // Now we can compute the corner points...
        int xPoints[] = new int[4];
        int yPoints[] = new int[4];

        xPoints[0] = x1 + dx;
        yPoints[0] = y1 + dy;
        xPoints[1] = x1 - dx;
        yPoints[1] = y1 - dy;
        xPoints[2] = x2 - dx;
        yPoints[2] = y2 - dy;
        xPoints[3] = x2 + dx;
        yPoints[3] = y2 + dy;

        g.fillPolygon(xPoints, yPoints, 4);
    }

    public void drawThickArc(Graphics2D g, int x1, int y1, int x2, int y2, Color c) {
        // The thick line is in fact a filled polygon
        g.setColor(c);


        QuadCurve2D q = new QuadCurve2D.Float();
        int cx = (x1 + x2) / 2;
        int cy = y1 + 30;
// draw QuadCurve2D.Float with set coordinates
        q.setCurve(x1, y1, cx, cy, x2, y2);
        g.draw(q);

    }


    public void paintThread(Graphics2D g, ThreadLine line) {
        g.setColor(line.color);
        g.fill3DRect(0, line.start, line.width, line.height, true);
    }

    public void paintNodeForRead(Graphics2D g, Node n, FontMetrics fm) {
        // take the x,y  coordinates of the node object
        int x = n.x;
        int y = n.y - 15;
        // take the type of node
        Ellipse2D.Double circle =
                new Ellipse2D.Double(x, y, 30, 30);
        Ellipse2D.Double circle2 =
                new Ellipse2D.Double(x, y, 35, 35);
        // setting the color that should draw depending on selected or fixed
        if (n.equals(pick)) {
            g.setColor(Color.LIGHT_GRAY);
        } else
            g.setColor(n.color);

        // take the length of node label (name parameter)
        int w = fm.stringWidth(n.label) + 10;
        //hight of the label
        int h = fm.getHeight() + 20;
        // this method is fill a rectangle , around the x,y using abouve
        // selected color
        //g.fill3DRect(x - w / 2 - 15, y - h / 2, w, h, true);
        Composite originalComposite = g.getComposite();

        g.fill(circle);
        g.setComposite(makeComposite(3 * 0.1F));
        g.setPaint(Color.BLACK);
        g.fill(circle2);
        g.setComposite(originalComposite);

        // set the drawing color to black
        g.setColor(Color.WHITE);
        // draw rectangle around the above rectangle
//
        // draw the name of the node inside the rectangle , which drew
        // earlier
        g.drawString(n.label, x - (w - 10) / 2 + 5, (y - (h - 4) / 2) + fm.getAscent());

    }

    public void paintNodeForWrite(Graphics2D g, Node n, FontMetrics fm) {
        // take the x,y  coordinates of the node object
        int x = n.x;
        int y = n.y - 15;
        // take the type of node
        Rectangle2D.Double square =
                new Rectangle2D.Double(x, y, 23, 23);
        Rectangle2D.Double square2 =
                new Rectangle2D.Double(x, y, 28, 23);
        // setting the color that should draw depending on selected or fixed

        if (n.equals(pick)) {
            g.setColor(Color.LIGHT_GRAY);
        } else
            g.setColor(n.color);

        // take the length of node label (name parameter)
        int w = fm.stringWidth(n.label) + 10;
        //hight of the label
        int h = fm.getHeight() + 20;
        // this method is fill a rectangle , around the x,y using abouve
        // selected color
        //g.fill3DRect(x - w / 2 - 15, y - h / 2, w, h, true);
        Composite originalComposite = g.getComposite();

        g.fill(square);
        g.setComposite(makeComposite(3 * 0.1F));
        g.setPaint(Color.BLACK);
        g.fill(square2);
        g.setComposite(originalComposite);

        // set the drawing color to black
        g.setColor(Color.WHITE);
        // draw rectangle around the above rectangle
//
        // draw the name of the node inside the rectangle , which drew
        // earlier
        g.drawString(n.label, x - (w - 10) / 2 + 5, (y - (h - 4) / 2) + fm.getAscent());

    }

    private AlphaComposite makeComposite(float alpha) {
        int type = AlphaComposite.SRC_OVER;
        return (AlphaComposite.getInstance(type, alpha));
    }

    // super.paintComponent clears offscreen pixmap,
    // since we're using double buffering by default.

    protected void clear(Graphics g) {
        super.paintComponent(g);
    }


    public void addThreadLine(ThreadLine threadLine) {
        threadLines.put(threadLine.label, threadLine);
    }


    public void reconfigure(java.util.List<InstVariable> variableList){
        threadLines.clear();
        nodeList.clear();
        populate(variableList);
        controlPanel.reconfigure(getVariables(), getThreads());
        repaint();
    }
    private void populate(java.util.List<InstVariable> variableList) {
        ThreadLine t1 = new ThreadLine(100, width, 10, Color.WHITE, "Thread1", 100);

        ThreadLine t2 = new ThreadLine(200, width, 10, Color.WHITE, "Thread2", 120);

        ThreadLine t3 = new ThreadLine(300, width, 10, Color.WHITE, "Thread3", 140);

        addThreadLine(t1);
        addThreadLine(t2);
        addThreadLine(t3);

        int index = 0;
        for (int i = 0; i < variableList.size(); i++) {
            InstVariable instVariable = variableList.get(i);
            boolean read = true;
            if (instVariable.getVtype().indexOf("W") >= 0) {
                read = false;
            }
            index = (i%3) + 1;
            int offset = new Random().nextInt(20);
            offset = offset + 30 + i * 10 ;
            addNode(new Node(Folcon.getVariableName(instVariable.getVsig()), read, instVariable.getVfile(), instVariable.getVline()), "Thread"+index, offset);
        }
    }

    public void addNode(Node node, String threadKey, int offset) {
        ThreadLine threadLine = (ThreadLine) threadLines.get(threadKey);
        node.y = threadLine.getMiddle();
        node.x = offset;
        node.threadKey = threadKey;
        ArrayList list = (ArrayList) nodeList.get(node.label);
        if (list == null) {
            list = new ArrayList();
        }
        list.add(node);
        String label = node.label;
        Color color = (Color) colorList.get(node.label);
        if (color == null) {
            int hashcode = Math.abs(label.hashCode()) ;
            int i = hashcode % colors.length;
            color = colors[i];
            colorList.put(label, color);
        }

        node.color = color;
        nodeList.put(node.label, list);

    }

    public RunTimeStack(Folcon folcon, java.util.List<InstVariable> variableList) {
        this.folcon = folcon;
        threadLines = new HashMap();
        nodeList = new HashMap();
        colorList = new HashMap();
        addMouseListener(this);
        populate(variableList);
        setBackground(Color.BLACK);
    }

    private void highlightLine(int number, String fileName) {
       folcon.highlightLine(number, fileName);
    }

    public void showVariable(String varibleName) {
        this.variableName = varibleName;
        showAll = false;
        repaint();
    }

    public void showAll() {
        showAll = true;
        repaint();
    }

    public void showAllThreads() {
        showAllThreads = true;
        repaint();
    }

    public void showThreas(String threadName) {
        this.threadName = threadName;
        showAllThreads = false;
        repaint();
    }

    public String[] getVariables() {
        Iterator itr = colorList.keySet().iterator();
        ArrayList<String> nameList = new ArrayList<String>();
        while (itr.hasNext()) {
            String key = (String) itr.next();
            nameList.add(key);
        }
        return nameList.toArray(new String[nameList.size()]);
    }

    public String[] getThreads() {
        Iterator itr = threadLines.keySet().iterator();
        ArrayList<String> nameList = new ArrayList<String>();
        while (itr.hasNext()) {
            String key = (String) itr.next();
            nameList.add(key);
        }
        return nameList.toArray(new String[nameList.size()]);
    }


    public void mouseClicked(MouseEvent e) {
        if (e.getButton() != 3) {
            int x = e.getX();
            int y = e.getY();
            double bestdist = Double.MAX_VALUE;
            Collection nodeArray = nodeList.values();
            for (Iterator iterator = nodeArray.iterator(); iterator.hasNext();) {
                ArrayList list = (ArrayList) iterator.next();
                for (int i = 0; i < list.size(); i++) {
                    Node n = (Node) list.get(i);
                    if (!showAll && !variableName.equals(n.label)) {
                        continue;
                    }
                    double dist = (n.x - x) * (n.x - x) + (n.y - y) * (n.y - y);
                    if (dist < bestdist) {
                        pick = n;
                        bestdist = dist;
                    }
                }
            }
            repaint();
            highlightLine(pick.line, pick.fileName);
//            JOptionPane.showMessageDialog(this, pick.label);
        }
    }


    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
        pick = null;
        repaint();
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {

    }

    public void setControlPanel(ControlPanel controlPanel) {
        this.controlPanel = controlPanel;
    }
}
