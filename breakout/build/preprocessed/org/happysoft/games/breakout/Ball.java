
/*
 * Ball.java
 * Created on 05 May 2007, 22:47
 */
package org.happysoft.games.breakout;

import javax.microedition.lcdui.Graphics;
import org.happysoft.games.*;

/**
 * @author Chris Francis
 */
public class Ball extends MovingGameObject {
  
  public static final int BALL_NEW = 0;
  public static final int BALL_ALIVE = 1;
  public static final int BALL_LOST = 2;
  public static final int BALL_IMPOTENT = 3;
  public static final int BALL_DEAD = 99;
  
  private int state = BALL_NEW;
  private int lastCollisionX = 12;
  //private int lastDirection = 3;
  private int lastSignX = -1;
  
  private Breakout game;
  
  // to make sure once the ball has bounced we don't
  // bounce it again immediately when still in range
  private boolean justBounced = false;
  
  /** Creates a new instance of Ball */
  public Ball(Breakout game) {
    super(0, 0, 8, 8);
    this.game = game;
    setMaxX(game.getScreenSize().width);
    setMaxY(game.getScreenSize().height);
  }
  
  public int getState() {
    return state;
  }
  
  public void setState(int newState) {
    state = newState;
    setBallDirection(lastCollisionX, lastSignX);
    System.out.println("Set state: " + newState);
  }
 
  private void reset() {
    if(game.getLiveBallCount() > 1) {
      state = BALL_DEAD;
    } else {
      game.resetPaddle();
      game.loseLife();
      lastCollisionX = 12;
      lastSignX = -1;
      state = BALL_NEW;
    }
  }
  
  public void move() {
    switch(state) {
      case(BALL_ALIVE):
      case(BALL_LOST):
      case(BALL_IMPOTENT):
        iterate();
        break;
        
      case(BALL_NEW):
        game.getPaddle().attachBallToBat(this, lastCollisionX);
        break;
    }
  }
  
  private void checkBounds() {
    if(state == BALL_IMPOTENT) {
      Rectangle r = game.getWall().getBounds(this);
      if(x < r.x || x > r.x+r.width || y < r.y || y > r.y+r.height) {
        state = BALL_ALIVE;
      }
    }
  }
  
  private void iterate() {
    int nextX = x + getDX();
    int nextY = y + getDY();
    int signX = sign(getDX());
    int signY = sign(getDY());
    
    if(intersectsWall(x, nextY, signX, signY)) {
      reverseY();
    }
    if(intersectsWall(nextX, y, signX, signY)) {
      reverseX();
    }
    
    Rectangle ballRect = getBoundingRect();
    
    Rectangle nextBallRect = new Rectangle(nextX, nextY, width, height);
    Rectangle nextBallXRect = new Rectangle(nextX, y, width, height);
    Rectangle nextBallYRect = new Rectangle(x, nextY, width, height);
    
    Rectangle paddleRect = game.getPaddle().getBoundingRect();
    
    if(signY < 0 && y > getMaxY()-(Paddle.PADDLE_Y_OFFSET+2)) {
      ballLost();
    }
    
    if(nextBallXRect.intersects(paddleRect)) {
      if(!justBounced && state == BALL_LOST) {
        reverseX();
        justBounced = true;
      }
    }
    
    if(nextBallYRect.intersects(paddleRect) || nextBallXRect.intersects(paddleRect)) {
      int collisionY = y - paddleRect.y;
      int collisionX = x - paddleRect.x;
      
      // check if y pos is too low, ball is lost anyway
      if(collisionY > -4) {
        ballLost();
      }
      
      // left edge bounce
      if(collisionX < -6 && !justBounced) {
        if(signX > 0) {
          reverseX();
        }
        ballLost();
        updateBallPos();
        justBounced = true;
        return;
      }
      
      // right edge bounce
      if(collisionX > paddleRect.width - 2 && !justBounced) {
        if(signX < 0) {
          reverseX();
        }
        ballLost();
        updateBallPos();
        justBounced = true;
        return;
      }
      
      // otherwise bounce the ball
      if(!justBounced && state != BALL_LOST) {
        game.getPaddle().handleBounce(this);
        if(state == BALL_IMPOTENT) {
          // ball never impotent by the time it's reached the bat? 
          setState(BALL_ALIVE);
        }
        setBallDirection(collisionX, signX);
        return;
      }
    } else {
      justBounced = false;
    }
    
    if(nextX+signX*4 >= getMaxX() || nextX+signX*4 < 0) {
      reverseX();
    }
    if(nextY+signY*4 < 0 ) {
      reverseY();
    }
    if(nextY+signY*4 >= getMaxY()) {
      reset();
    }
    updateBallPos();
    checkBounds();
  }
  
  private void ballLost() {
    state = BALL_LOST;
    if(sign(getDY()) < 0) {
      reverseY();
    }
  }
  
  public void updateBallPos() {
    incrementX();
    incrementY();
  }
  
  private boolean intersectsWall(int x, int y, int signX, int signY) {
    Wall wall = game.getWall();
    int brickWidth = wall.getBrickWidth();
    int brickHeight = wall.getBrickHeight();
    
    Brick[][] bricks = wall.getBricks();
    int brickX = (int) ((x+1+signX*2)/brickWidth);
    int brickY = (int) ((y+1+signY*2)/brickHeight);
    brickY -= wall.getOffsetFromTop();
    if(brickY >= 0 && brickY < 5 && brickX < 11) {
      Brick brick = bricks[brickY][brickX];
      if(brick != null) {
        if(state != BALL_IMPOTENT) {
          brick.knockout(wall, brickX, brickY);
          SoundEffect.getInstance().playBrickNoise();
        }
        return true;
      }
    }
    return false;
  }
  
  private void setBallDirection(int collisionX, int signX) {
    lastCollisionX = collisionX;
    lastSignX = signX;

    int offset = 0;
    Paddle paddle = game.getPaddle();
    if(paddle != null) {
      offset = paddle.getPaddleCollisionOffset();
    }
    
    if(collisionX < 4) {
      setDX(-5);
      setDY(-2);
    }
    if(collisionX >=4 && collisionX < 12 + offset) {
      setDX(3*signX);
      setDY(-3);
    }
    if(collisionX >= 12 + offset && collisionX < 20 + offset) {
      setDX(2*signX);
      setDY(-5);
    }
    if(collisionX >= 20 + offset && collisionX < 28 + offset) {
      setDX(3*signX);
      setDY(-3);
    }
    if(collisionX >= 28 + offset) {
      setDX(5);
      setDY(-2);
    }
  }
  
  private int sign(int num) {
    return num >= 0 ? 1 : -1;
  }
  
  public void paint(Graphics g) throws BreakoutGameException {
    if(state != BALL_DEAD) {
      g.drawImage(game.getGameImages().getBallImage(), x, y, Graphics.TOP|Graphics.LEFT);
    }
  }
}
