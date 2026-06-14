/*
 * Paddle.java
 *
 * Created on 02 June 2007, 20:38
 */
package org.happysoft.games.breakout;

import org.happysoft.games.*;
import javax.microedition.lcdui.*;

/**
 * @author Chris Francis
 */
public class Paddle extends MovingGameObject {
  
  protected boolean movingLeft = false, movingRight = false;    
  protected Breakout game;
  
  public static final int PADDLE_Y_OFFSET = 22;
  public static final int PADDLE_STEP = 6;
    
  /** Creates a new instance of Paddle */
  public Paddle(Breakout game, int ballX) {
    super(0, 0, 32, 10);
    this.game = game;
    setMaxX(game.getScreenSize().width - width);
    setMaxY(game.getScreenSize().height);
    y = getMaxY()-PADDLE_Y_OFFSET;   
  }
  
  public void paint(Graphics g) throws BreakoutGameException {
    g.drawImage(game.getGameImages().getPaddleStandard(), x, y, Graphics.TOP|Graphics.LEFT);
  }
  
  public void move() {
    if(movingLeft) {
      moveLeft();
    }
    if(movingRight) {
      moveRight();
    }
  }
  
  public void fire() {
    game.releaseBall();
  }
  
  public int getPaddleCollisionOffset() {
    return 0;
  }
  
  public void handleBounce(Ball b) {
    b.reverseY();
    b.updateBallPos();
  }

  public void attachBallToBat(Ball b, int collisionX) {
    Rectangle r = getBoundingRect();
    b.x = r.x + collisionX;
    b.y = getMaxY() - (PADDLE_Y_OFFSET + 8);
  }
  
  public void setMovingLeft() {
    movingLeft = true;
    movingRight = false;
  }

  public void setMovingRight() {
    movingRight = true;
    movingLeft = false;
  }
  
  public void setNotMoving() {
    movingLeft = false;
    movingRight = false;
  }
  
  private void moveLeft() {
    if(x-PADDLE_STEP >= getMinX()) {
      x -= PADDLE_STEP;
    }
  }
  
  private void moveRight() {
    if(x+PADDLE_STEP <= getMaxX()) {
      x += PADDLE_STEP;
    } 
  }  
    
}
