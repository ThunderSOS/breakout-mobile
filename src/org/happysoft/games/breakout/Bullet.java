/*
 * Bullet.java
 * Created on 29 November 2007, 16:12
 *
 * @author Chris Francis
 */
package org.happysoft.games.breakout;

import javax.microedition.lcdui.Graphics;
import org.happysoft.games.Rectangle;

public class Bullet extends MovingGameObject {
  
  private Breakout game;
  
  /** Creates a new instance of Bullet */
  public Bullet(Breakout game) {
    super(0, 0, 0, 0);
    this.game = game;
    Rectangle paddlePos = game.getPaddle().getBoundingRect();
    x = paddlePos.x + paddlePos.width/2;
    y = paddlePos.y;
    setDY(-6);
    setDX(0);
  }
  
  public void move() {
    incrementY();
    if(intersectsWall(x, y, 1, -1)) {
      game.removeBullet(this);
    }
  }
  
  private boolean intersectsWall(int x, int y, int signX, int signY) {
    Wall wall = game.getWall();
    int brickWidth = wall.getBrickWidth();
    int brickHeight = wall.getBrickHeight();
    
    Brick[][] bricks = wall.getBricks();
    int brickX = ((x+1+signX*2)/brickWidth);
    int brickY = ((y+1+signY*2)/brickHeight);
    brickY -= wall.getOffsetFromTop();
    if(brickY >= 0 && brickY < 5 && brickX < 11) {
      Brick brick = bricks[brickY][brickX];
      if(brick != null) {
        brick.shoot(wall, brickX, brickY);
        return true;
      }
    }
    return false;
  }
  
  public void paint(Graphics g) throws BreakoutGameException {
    g.setColor(0xFFFFFF);
    g.drawLine(x, y, x, y-3);
  }
  
}
