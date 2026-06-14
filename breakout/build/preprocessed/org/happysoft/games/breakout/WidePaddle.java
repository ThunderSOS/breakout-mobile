
/*
 * WidePaddle.java
 *
 * Created on 20 June 2007, 21:59
 */
package org.happysoft.games.breakout;

import java.io.IOException;
import org.happysoft.games.*;
import javax.microedition.lcdui.*;

/**
 * @author Chris Francis
 */
public class WidePaddle extends Paddle {
  
  /** Creates a new instance of Paddle */
  public WidePaddle(Breakout game, int ballx) {
    super(game, ballx);
    x = 0; y = 0; width = 48; height = 10;
    setMaxX(game.getScreenSize().width - width);
    setMaxY(game.getScreenSize().height);
    y = getMaxY()-Paddle.PADDLE_Y_OFFSET;     
  }
  
  public int getPaddleCollisionOffset() {
    return 16;
  }
  
  public void paint(Graphics g) throws BreakoutGameException {
    g.drawImage(game.getGameImages().getPaddleWide(), x, y, Graphics.TOP|Graphics.LEFT);
  }
  
}
