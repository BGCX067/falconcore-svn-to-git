package org.gatech.gui;

import org.gatech.cs.HighLightContainer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;


public class GraphicsPanel extends JPanel implements MouseListener {
    private GraphicsBean beans[];
    private int width;
    private int height;
    private HighLightContainer main;
    private Image variableRead;
    private Image variableWrite;
    private Image methodCall;

    public GraphicsPanel(GraphicsBean[] beans, HighLightContainer main) {
        this.beans = beans;
        this.main = main;
        addMouseListener(this);
        setBackground(Color.BLACK);
        setForeground(Color.WHITE);
//        setBorder(BorderFactory.createEtchedBorder());
        variableRead = Toolkit.getDefaultToolkit().getImage("images/circle_blue.png");
        variableWrite = Toolkit.getDefaultToolkit().getImage("images/circle_red.png");
        methodCall = Toolkit.getDefaultToolkit().getImage("images/furl_48.png");

    }

    public void setBeans(GraphicsBean[] beans) {
        this.beans = beans;
        repaint();
    }

    public Dimension getDimension() {
        return new Dimension(width, height);
    }

    public void paintComponent(Graphics g) {
        clear(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.BLACK);
        int x = 5;
        height = 40;
        FontMetrics metrics = g.getFontMetrics();
        for (int i = 0; i < beans.length; i++) {
            GraphicsBean bean = beans[i];
            if (bean != null) {
                int tem = metrics.stringWidth(bean.getName()) + 80;
                bean = paintNodeForRead(g2d, metrics, x, bean.getName(), bean, tem);
                x = x + tem;
            }

            beans[i] = bean;
        }
        width = x;
    }

    protected void clear(Graphics g) {
        super.paintComponent(g);
    }

    public GraphicsBean paintNodeForRead(Graphics2D g, FontMetrics fm, int x, String label, GraphicsBean bean, int width) {
        // take the x,y  coordinates of the node object
        int y = 0;

        // take the type of node
        int x1 = x + fm.stringWidth(label) + 5;
        g.setColor(Color.WHITE);
        g.drawString(label, x + 5, y + 25);
        y = 6;
        if (bean.isVariable()) {
            if (bean.isRead()) {
                g.drawImage(variableRead, x1, y, this);
            } else {
                g.drawImage(variableWrite, x1, y, this);
            }
        } else {
            g.setColor(Color.GREEN);
            g.draw3DRect(x, 0, width, 33, true);
            g.drawImage(methodCall, x1, y, this);
        }
        bean.x = x1;
        bean.y = y;
        return bean;
    }

    private GraphicsBean pick;

    public void mouseClicked(MouseEvent e) {
        if (e.getButton() != 3) {
            int x = e.getX();
            int y = e.getY();
            double bestdist = Double.MAX_VALUE;
            for (int i = 0; i < beans.length; i++) {
                GraphicsBean n = beans[i];
                double dist = (n.x - x) * (n.x - x) + (n.y - y) * (n.y - y);
                if (dist < bestdist) {
                    pick = n;
                    bestdist = dist;
                }
            }
            repaint();
            if (pick.isVariable()) {
                main.highlightLine(pick.getLine() - 1, pick.getClassName());
            } else {
                main.highlight(pick.getName(), pick.getClassName());
            }

        }
    }

    public void mousePressed(MouseEvent e) {
        if (e.getButton() != 3) {
            int x = e.getX();
            int y = e.getY();
            double bestdist = Double.MAX_VALUE;
            for (int i = 0; i < beans.length; i++) {
                GraphicsBean n = beans[i];
                double dist = (n.x - x) * (n.x - x) + (n.y - y) * (n.y - y);
                if (dist < bestdist) {
                    pick = n;
                    bestdist = dist;
                }
            }
            repaint();
            main.highlightLine(pick.getLine() - 1, pick.getClassName());
        }
    }

    public void mouseReleased(MouseEvent e) {
        pick = null;
        repaint();
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
        pick = null;
        repaint();
    }


}
