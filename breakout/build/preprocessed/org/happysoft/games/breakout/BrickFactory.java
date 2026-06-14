
/*
 * BrickTypeMap.java
 *
 * Created on 18 August 2007, 16:46
 */
package org.happysoft.games.breakout;

import java.util.Hashtable;

/**
 * @author Chris Francis
 */
public class BrickFactory {
  
  private Hashtable map = new Hashtable();
  
  /** Creates a new instance of BrickTypeMap */
  public BrickFactory() {
  }
  
  public Brick getBrick(Breakout game, int wallX, int wallY, int brickWidth, int brickHeight, int brickType) {
    Brick brick = null;

    switch ((brickType & 56) >> 3) {
      case(1):
        brick = new StickyBonusBrick(game, wallX, wallY, brickWidth, brickHeight, brickType);
        break;
        
      case(2):
        brick = new WideBonusBrick(game, wallX, wallY, brickWidth, brickHeight, brickType);
        break;
        
      case(3):
        brick = new TurretBonusBrick(game, wallX, wallY, brickWidth, brickHeight, brickType);
        break;
    }
    if((brickType & 64) != 0) {
      brick = new ImmovableBrick(game, wallX, wallY, brickWidth, brickHeight);
    }
    if((brickType & 128) != 0) {
      brick = new MultiHitBrick(game, wallX, wallY, brickWidth, brickHeight, brickType, 2);
    }
    if(brick == null) {
      brick = new Brick(game, wallX, wallY, brickWidth, brickHeight, brickType);
    }
    return brick;
  }

}
