/*
 * TurretBonus.java
 * Created on 03 December 2007, 18:16
 *
 * @author Chris Francis
 */

package org.happysoft.games.breakout;

import javax.microedition.lcdui.Graphics; 

public class TurretBonus extends Bonus {
  
  /** Creates a new instance of TurretBonus */
  public TurretBonus(Breakout game, int x, int y, int width, int height) {
    super(game, x, y, width, height);
    this.game = game;
    setDX(0);
    setDY(3);
  }
    
  public void paint(Graphics g) {
    if(state == ALIVE) {
      g.drawImage(game.getGameImages().getBonusTurret(), x, y, Graphics.TOP | Graphics.LEFT);
    }
  }
  
  protected void activate() {    
    Paddle turretPaddle = new TurretPaddle(game, 16);  // game.getPaddle().collisionX
    setGamePaddle(turretPaddle);
    MessageController.getInstance(game).setMessage("BONUS!", 600, true, true);
  }
  
}
