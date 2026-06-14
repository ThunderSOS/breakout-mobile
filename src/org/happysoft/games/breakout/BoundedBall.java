
/*
 * BoundedBall.java
 * A ball that starts off trapped inside the wall unable to knock out bricks. 
 * 
 * Created on 15 August 2007, 15:54
 */
package org.happysoft.games.breakout;

import org.happysoft.games.Rectangle;

/**
 * @author Chris Francis
 */
public class BoundedBall extends Ball {
  
  Rectangle bounds = null;
  
  public BoundedBall(Breakout game, Rectangle bounds, int x, int y) {
    super(game);
    this.bounds = bounds;
    this.x = x;
    this.y = y;    
    setState(BALL_IMPOTENT);
  }
  
  public Rectangle getBounds() {
    return bounds;
  }
  
}
