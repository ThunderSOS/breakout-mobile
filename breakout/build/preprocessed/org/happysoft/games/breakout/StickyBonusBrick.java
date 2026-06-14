
/*
 * StickyBonusBrick.java
 *
 * Created on 18 August 2007, 17:00
 */
package org.happysoft.games.breakout;

/**
 * @author Chris Francis
 */
public class StickyBonusBrick extends Brick {
  
  /** Creates a new instance of StickyBonusBrick */
  public StickyBonusBrick(Breakout game, int x, int y, int width, int height, int brickType) {
    super(game, x, y, width, height, brickType);
  }
  
  public void knockout(Wall wall, int brickX, int brickY) {
    game.incrementScore(50);
    Bonus b = new StickyBonus(game, x, y, width, height);
    game.addBonus(b);
    wall.clearBrick(brickX, brickY);
  }
}