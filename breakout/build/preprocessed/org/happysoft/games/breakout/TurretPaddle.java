/*
 * TurretPaddle.java
 * Created on 03 December 2007, 18:22
 *
 * @author Chris Francis
 */

package org.happysoft.games.breakout;

import javax.microedition.lcdui.Graphics;

public class TurretPaddle extends Paddle {
  
  /** Creates a new instance of TurretPaddle */
  public TurretPaddle(Breakout game, int ballX) {
    super(game, ballX);
  }
  
  public void paint(Graphics g) throws BreakoutGameException {
    g.drawImage(game.getGameImages().getPaddleTurret(), x, y, Graphics.TOP|Graphics.LEFT);
  }
  
  public void fire() {
    game.addBullet(new Bullet(game));    
  }  
}
