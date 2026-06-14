
package org.happysoft.games.breakout;

import javax.microedition.lcdui.Graphics;

/*
 * Brick.java
 * Created on 22 June 2007, 16:36
 *
 * @author Chris Francis
 */
public class Brick extends GameObject {
  
  protected int brickType = 0;  
  protected Breakout game;
  
  /** Creates a new instance of Brick */
  public Brick(Breakout game, int x, int y, int width, int height, int brickType) {
    super(x, y, width, height);
    this.game = game;
    this.brickType = brickType;
  }
  
  public void knockout(Wall wall, int brickX, int brickY) {
    wall.clearBrick(brickX, brickY);
    incrementScore();
  }
  
  public void shoot(Wall wall, int brickX, int brickY) {
    wall.clearBrick(brickX, brickY);
    incrementScore();
  }  
  
  protected void incrementScore() {
    game.incrementScore(10);  
  }
  
  public int getBrickType() {
    return brickType;
  }
  
  public void paint(Graphics g) throws BreakoutGameException {
    g.drawImage(game.getGameImages().getBrickImage(brickType), x, y, Graphics.TOP|Graphics.LEFT);
  }
  
}
