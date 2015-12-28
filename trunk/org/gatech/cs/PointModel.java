package org.gatech.cs;

import java.util.*;

public class PointModel extends Observable
{
   // the coordinate point
   int x, y;
   // are we dragging?
   boolean drag;

   public PointModel() {
      // initialize with impossible coordinate values
      x = Integer.MIN_VALUE;
      y = Integer.MIN_VALUE;
      drag = false;
   }

   // set the new point and notify observers
   public void setPoint(int x, int y) { setPoint(x, y, false); }
   public void setPoint(int x, int y, boolean drag) {
      this.x = x;
      this.y = y;
      this.drag = drag;
      setChanged();
      notifyObservers();
   }

   // clear the point (with impossible values), and notify
   public void clearPoint() {
      x = Integer.MIN_VALUE;
      y = Integer.MIN_VALUE;
      setChanged();
      notifyObservers();
   }

   // get the coordinate point and dragging state
   public int getX() { return x; }
   public int getY() { return y; }
   public boolean getDrag() { return drag; }
}
