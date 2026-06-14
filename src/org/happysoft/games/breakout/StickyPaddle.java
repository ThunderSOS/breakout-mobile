
/*
 * StickPaddle.java
 *
 * Created on 29 June 2007, 12:32
 */
package org.happysoft.games.breakout;

import javax.microedition.lcdui.Graphics;
import org.happysoft.games.Rectangle;

/**
 * @author Chris Francis
 */
public class StickyPaddle extends Paddle {
  
  private int lastSignX = 1;
      
  /** Creates a new instance of StickyPaddle */
  public StickyPaddle(Breakout game, int ballx) {
    super(game, ballx);
    x = 0; y = 0; width = 32; height = 10;
    setMaxX(game.getScreenSize().width - width);
    setMaxY(game.getScreenSize().height);
    y = getMaxY()-PADDLE_Y_OFFSET;    
  }
  
  public void paint(Graphics g) throws BreakoutGameException {
    g.drawImage(game.getGameImages().getPaddleSticky(), x, y, Graphics.TOP|Graphics.LEFT);
  }
  
  public void handleBounce(Ball b) {
    //this.collisionX = b.x - x;
    if (b.y <= 190) {
      b.setState(Ball.BALL_NEW);
      if(b.x >= x && b.x <= (x + width - 8) ) {
        return ;
      }
      int overhangLeft = x - b.x;
      int overhangRight = x + width - b.x;
      if(overhangLeft > 0) {
        setMinX(overhangLeft + 4);
        setMaxX(game.getScreenSize().width - width);
      }
      if(overhangRight > 0 && overhangLeft <= 0) {
        setMaxX(game.getScreenSize().width - width - overhangRight);
        setMinX(0);
      }
    }
  }
 
}
