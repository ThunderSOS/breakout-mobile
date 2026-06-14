/*
 * BreakoutAnimatedObject.java
 * Created on 22 June 2007, 00:21
 */
package org.happysoft.games.breakout;

/**
 * @author Chris Francis
 */
public abstract class MovingGameObject extends GameObject {
  
  private int dx, dy;
  private int maxX, maxY;
  private int minX = 0, minY = 0;
  
  /** Creates a new instance of MovingGameObject */
  public MovingGameObject(int x ,int y, int width, int height) {
    super(x, y, width, height);
  }
  
  public int getMaxX() {
    return maxX;
  }

  public void setMaxX(int maxX) {
    this.maxX = maxX;
  }

  public int getMaxY() {
    return maxY;
  }

  public void setMaxY(int maxY) {
    this.maxY = maxY;
  }
  
  public int getMinX() {
    return minX;
  }

  public void setMinX(int minX) {
    this.minX = minX;
  }

  public int getMinY() {
    return minY;
  }

  public void setMinY(int minY) {
    this.minY = minY;
  }
  
  public int getDX() {
    return dx;
  }
  
  public void setDX(int dx) {
    this.dx = dx;
  }
  
  public int getDY() {
    return dy;
  }
  
  public void setDY(int dy) {
    this.dy = dy;
  }
  
  public void reverseX() {
    dx = -dx;
  }
  
  public void reverseY() {
    dy = -dy;
  }
  
  public void incrementX() {
    x = x + dx;
  }
  
  public void incrementY() {
    y = y + dy;
  }
  
  /*
   *Nout moves without this... 
   */
  public abstract void move();

}
