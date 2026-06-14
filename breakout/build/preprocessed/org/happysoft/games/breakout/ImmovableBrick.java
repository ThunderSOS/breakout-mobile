/*
 * ImmovableBrick.java
 * Created on 03 December 2007, 17:23
 *
 * @author Chris Francis
 */
package org.happysoft.games.breakout;

import javax.microedition.lcdui.Graphics;

/**
 * @author Chris Francis
 */
public class ImmovableBrick extends Brick {
  
  private int flashPeriod = 200; // ms (roughly) 
  private int counter = 0;
  
  /** Creates a new instance of ImmovableBrick */
  public ImmovableBrick(Breakout game, int x, int y, int width, int height) {
    super(game, x, y, width, height, 64);
  }

  public void knockout(Wall wall, int brickX, int brickY) {
    counter = (int)(flashPeriod/Breakout.FRAME_RATE);
  }
  
  public void paint(Graphics g) throws BreakoutGameException {
    if(counter > 0) {
      g.drawImage(game.getGameImages().getBrickFlashImage(), x, y, Graphics.TOP|Graphics.LEFT);
      counter--;
    } else {
      g.drawImage(game.getGameImages().getImmovableBrickImage(), x, y, Graphics.TOP|Graphics.LEFT);
    }
  }
  
}