
/*
 * LevelData.java
 *
 * Created on 14 August 2007, 13:07
 */
package org.happysoft.games.breakout;

import java.io.*;
import org.happysoft.games.CSVEnumeration;
import org.happysoft.games.Rectangle;

/**
 * @author Chris Francis
 */
public class Level {
  
  private byte[] b = null;
  private int count = 0;
  
  private StringBuffer sb = new StringBuffer();
    
  /** Creates a new instance of LevelData */
  public Level(String filename) {
    try {
      DataInputStream din = new DataInputStream(getClass().getResourceAsStream(filename));    
      int available = din.available();
      System.out.println("Available: " + available);
      b = new byte[available];
      din.readFully(b);
      din.close();

    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }
  
  public Wall buildWall(Breakout game) {
    String gridPropsLine = getNextLine();
    GridDimensions grid = getGridProperties(gridPropsLine);

    int height = grid.height;
    int width = grid.width;
    int offset = grid.offsetFromTop;

    Wall wall = new Wall(game, width, height, offset);
    
    for(int i = 0; i < height; i++) {
      wall.setRow(i, getWallBricks(getNextLine(), width));
    }
    
    int numBalls = 0; 
    try {
      numBalls = Integer.parseInt(getNextLine());
      
    } catch (Exception nfe) {
      // no extra ball definitions
    }
    
    for(int i = 0; i < numBalls; i++) {
      BallProperties props = getBallData();
      wall.addNewBall(props.bounds, props.bx, props.by);
    }
    
    wall.init();    
    return wall;
  }
  
  private String getNextLine() {
    sb.setLength(0);
    byte bt = 0;
    while (count < b.length) {
      bt = b[count];
      count++;
      if(bt == 10 || bt == 13) {
        break;
      }
      sb.append((char)bt);
    }
    if(sb.length() == 0) {
      return null;
    }
    return sb.toString();
  }
  
  private GridDimensions getGridProperties(String line) {
    CSVEnumeration csv = new CSVEnumeration(line);
    int height = Integer.parseInt(csv.nextElement());
    int width = Integer.parseInt(csv.nextElement());
    int offset = Integer.parseInt(csv.nextElement());
    return new GridDimensions(width, height, offset);
  }
  
  private int[] getWallBricks(String line, int width) {
    CSVEnumeration csv = new CSVEnumeration(line);    
    int[] ret = new int[width];
    for(int i = 0; i < width; i++) {
      ret[i] = Integer.parseInt(csv.nextElement());
    }
    return ret;
  }
  
  private BallProperties getBallData() {
    CSVEnumeration xyLine = new CSVEnumeration(getNextLine());
    CSVEnumeration rectData = new CSVEnumeration(getNextLine());
    
    int ballx = Integer.parseInt(xyLine.nextElement());
    int bally = Integer.parseInt(xyLine.nextElement());
    
    int x = Integer.parseInt(rectData.nextElement());
    int y = Integer.parseInt(rectData.nextElement());
    int w = Integer.parseInt(rectData.nextElement());
    int h = Integer.parseInt(rectData.nextElement());
    
    Rectangle rect = new Rectangle(x, y, w, h);
    BallProperties props = new BallProperties(ballx, bally, rect);
    
    return props;
  }
   
  private class GridDimensions {
    int width, height, offsetFromTop;
    
    GridDimensions(int width, int height, int offset) {
      this.width = width;
      this.height = height;
      this.offsetFromTop = offset;
    }
  }
  
  private class BallProperties {
    int bx, by;
    Rectangle bounds;
    
    public BallProperties(int bx, int by, Rectangle rect) {
      System.out.println("startX = " + bx + ", startY = " + by + ", rect = {" + rect + "}");
      this.bx = bx;
      this.by = by;
      bounds = rect;
    }
    
  }
  
}
