/*
 * MultiHitBrick.java
 * Created on 04 December 2007, 16:15
 *
 * @author Chris Francis
 */

package org.happysoft.games.breakout;

import javax.microedition.lcdui.Graphics;


public class MultiHitBrick extends Brick {
  
  private int flashPeriod = 200; // ms (roughly) 
  private int counter = 0;
  private int numHits = 2;
  
  /** Creates a new instance of ImmovableBrick */
  public MultiHitBrick(Breakout game, int x, int y, int width, int height, int brickType, int numHits) {
    super(game, x, y, width, height, brickType);
    this.numHits = numHits;
  }

  public void knockout(Wall wall, int brickX, int brickY) {
    numHits--;
    if(numHits == 0) {
      super.knockout(wall, brickX, brickY);
    } else {
      counter = flashPeriod / Breakout.FRAME_RATE;
    }    
  }
  
  public void paint(Graphics g) throws BreakoutGameException {
    if(counter > 0) {
      g.drawImage(game.getGameImages().getBrickFlashImage(), x, y, Graphics.TOP|Graphics.LEFT);
      counter--;
    } else {
      g.drawImage(game.getGameImages().getBrickImage(brickType), x, y, Graphics.TOP|Graphics.LEFT);
    }
  }
  
}