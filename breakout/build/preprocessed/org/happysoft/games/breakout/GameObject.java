/*
 * BreakoutObject.java
 * Created on 22 June 2007, 16:38
 *
 * @author Chris Francis
 */

package org.happysoft.games.breakout;

import javax.microedition.lcdui.Graphics;
import org.happysoft.games.Rectangle;

public abstract class GameObject {
  
  public int x, y, width, height;
  
  /** Creates a new instance of BreakoutObject */
  public GameObject(int x, int y, int width, int height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }
 
  public Rectangle getBoundingRect() {
    return new Rectangle(x, y, width, height);
  }
  
  public abstract void paint(Graphics g) throws BreakoutGameException;
  
}
