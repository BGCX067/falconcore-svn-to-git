package org.gatech.cs;


import java.awt.*;
import javax.swing.*;
import java.util.*;

public class Ruler extends JPanel implements SwingConstants, Observer
   {
       // the length of the ruler
      int length;
      // the orientation, horizontal or vertical
      int orientation;
      // the font used for drawing the labels
      Font f;
      // the point model for tracking mouse movements
      PointModel pm = null;

      public Ruler(int length, int orientation) {
         this.length = length;
         this.orientation = orientation;
         f = new Font("sans-serif", Font.PLAIN, 9);
         int width = orientation == HORIZONTAL ? length : 20;
         int height = orientation != HORIZONTAL ? length : 20;
         setPreferredSize(new Dimension(width, height));
      }

      public void setPointModel(PointModel pm) {
         this.pm = pm;
         pm.addObserver(this);
      }

      public void update(Observable o, Object arg) {
         repaint();
      }

      public void paint(Graphics graphics) {
         super.paint(graphics);
         Graphics2D g = (Graphics2D) graphics;
         g.setFont(f);
         if (orientation == HORIZONTAL) {
            // draw the tick marks
            for (int x = 0; x < length; x += 10) {
               g.drawLine(x, 1, x, 5);
               if (x % 50 == 0) {
                  g.drawLine(x, 1, x, 10);
                  g.drawString("" + x, x - 4, 19);
               }
            }
            // draw the red line for the current point
            if (pm != null) {
               int px = pm.getX();
               if (px != Integer.MIN_VALUE) {
                  g.setColor(Color.red);
                  g.drawLine(px, 1, px, 18);
               }
            }
         } else { // orientation == VERTICAL
            // draw the tick marks
            for (int y = 0; y < length; y += 10) {
               g.drawLine(1, y, 5, y);
               if (y % 50 == 0) {
                  g.drawLine(1, y, 10, y);
                  String label = Integer.toString(y);
                  for (int i = 0; i < label.length(); i++) {
                     g.drawString(label.substring(i, i+1), 13, y + (i * 9));
                  }
               }
            }
            // draw the red line for the current point
            if (pm != null) {
               int py = pm.getY();
               if (py != Integer.MIN_VALUE) {
                  g.setColor(Color.red);
                  g.drawLine(1, py, 18, py);
               }
            }
         }
      }
   }
