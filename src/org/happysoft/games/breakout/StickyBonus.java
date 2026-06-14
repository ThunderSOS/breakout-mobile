/*
 * Bonus.java
 * Created on 22 June 2007, 18:19
 *
 * @author Chris Francis
 */
package org.happysoft.games.breakout;

import javax.microedition.lcdui.Graphics;

public class StickyBonus extends Bonus {
  
  /** Creates a new instance of Bonus */
  public StickyBonus(Breakout game, int x, int y, int width, int height) {
    super(game, x, y, width, height);
    setDX(0);
    setDY(3);
  }
  
  public void paint(Graphics g) {
    if(state == ALIVE) {
      g.drawImage(game.getGameImages().getBonusSticky(), x, y, Graphics.TOP | Graphics.LEFT);
    }
  }
  
  protected void activate() {  
    Paddle stickyPaddle = new StickyPaddle(game, 16);  //game.getPaddle().collisionX
    setGamePaddle(stickyPaddle);
    MessageController.getInstance(game).setMessage("BONUS!", 600, true, true);
  }
  
}
