/*
 * Wall.java
 *
 * Created on 19 May 2007, 17:10
 */
package org.happysoft.games.breakout;

import java.util.Vector;
import javax.microedition.lcdui.*;
import org.happysoft.games.Rectangle;

/**
 * @author Chris Francis
 */
public class Wall {
  
//  private int[][] bricks = new int[][] {
//    new int[] {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
//    new int[] {2, 2, 0, 0, 2, 2, 2, 0, 0, 2, 2},
//    new int[] {3, 3, 0, 0, 3, 3, 3, 0, 0, 3, 3},
//    new int[] {4, 4, 7, 6, 4, 4, 4, 6, 7, 4, 4},
//    new int[] {5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5}
//  };
  
  public int[][] bricks = null;
  
  private Brick[][] wall = null;
  //private Ball[] balls = new Ball[3];
  private Vector balls = new Vector();
  
  private int brickWidth = 16, brickHeight = 11;
  private int wallWidth = 11;
  private int wallHeight = 5;
  private int offsetFromTop = 3;
  
  private int brickCount = 0;
  
  private Breakout game;
  
  /** Creates a new instance of Wall */
  public Wall(Breakout game, int wallWidth, int wallHeight, int offset) {
    offsetFromTop = offset;
    this.game = game;
    this.wallWidth = wallWidth;
    this.wallHeight = wallHeight;
    bricks = new int[wallHeight][wallWidth];
    
    Ball ball = new Ball(game);
    ball.setState(Ball.BALL_NEW);
    balls.addElement(ball); 
  }
  
  public void init() {
    buildWall(); 
  }
  
  private void buildWall() {
    BrickFactory fac = new BrickFactory();
    wall = new Brick[wallHeight][wallWidth];
    for(int j = 0; j < wallHeight; j++) {
      for(int i = 0; i < wallWidth; i++) {
        int brickType = bricks[j][i];
        if(brickType != 255) {
          wall[j][i] = fac.getBrick(game, i*brickWidth, (j+offsetFromTop)*brickHeight, brickWidth, brickHeight, brickType);
        }
      }
    }
  }
  
  public void setRow(int row, int[] brickRow) {
    bricks[row] = brickRow;
  }  
  
  public void addNewBall(Rectangle bounds, int initX, int initY) {
    BoundedBall ball = new BoundedBall(game, bounds, initX, initY);
    balls.addElement(ball);
  }

  public boolean roundOver() {
    return brickCount == 0;
  }
  
  public int getBrickWidth() {
    return brickWidth;
  }
  
  public int getBrickHeight() {
    return brickHeight;
  }
  
  public int getWallWidth() {
    return wallWidth;
  }
  
  public int getWallHeight() {
    return wallHeight;
  }
  
  public Brick[][] getBricks() {
    return wall;
  }
  
  public int getOffsetFromTop() {
    return offsetFromTop;
  }
  
  public void paint(Graphics g) throws BreakoutGameException {
    brickCount = 0;
    for(int j = 0; j < wallHeight; j++) {
      for(int i = 0; i < wallWidth; i++) {
        Brick brick = wall[j][i];
        if(brick != null) {
          brick.paint(g);    
          brickCount++;
        }
      }
    }
  }
  
  public void clearBrick(int brickX, int brickY) {
    wall[brickY][brickX] = null;
  }
  
  public Rectangle getBounds(Ball ball) {
    for(int i = 0; i < balls.size(); i++) {
      if(ball == (Ball)balls.elementAt(i)) {
        return ((BoundedBall) balls.elementAt(i)).getBounds();
      }
    }
    // should never happen ... 
    return null;
  }
  
  public Vector getBallArray() {
    return balls;
  }
   
}
