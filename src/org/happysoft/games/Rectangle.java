/*
 * Rectangle.java
 * Created on 13 June 2007, 12:19
 *
 * @author Chris Francis
 */

package org.happysoft.games;


public class Rectangle {
  
  public int width,height,x,y;
  
  /** Creates a new instance of Rectangle */
  public Rectangle(int x, int y, int w, int h) {
    this.x = x;
    this.y = y;
    width = w;
    height = h;
  } 
  
  public boolean intersects(Rectangle r) {
    int tw = this.width;
    int th = this.height;
    int rw = r.width;
    int rh = r.height;
    if (rw <= 0 || rh <= 0 || tw <= 0 || th <= 0) {
        return false;
    }
    int tx = this.x;
    int ty = this.y;
    int rx = r.x;
    int ry = r.y;
    rw += rx;
    rh += ry;
    tw += tx;
    th += ty;
    //      overflow || intersect
    return ((rw < rx || rw > tx) &&
      (rh < ry || rh > ty) &&
      (tw < tx || tw > rx) &&
      (th < ty || th > ry));
  }
  
  public String toString() {
    return "x = " + x + ", y = " + y + ", width = " + width + ", height = " + height;
  }
  
}
