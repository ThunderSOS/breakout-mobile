
package org.happysoft.games.breakout;

/*
 * TurretBonusBrick.java
 * Created on 03 December 2007, 18:30
 *
 * @author Chris Francis
 */  
public class TurretBonusBrick extends Brick {
  
  /** Creates a new instance of StickyBonusBrick */
  public TurretBonusBrick(Breakout game, int x, int y, int width, int height, int brickType) {
    super(game, x, y, width, height, brickType);
  }
  
  public void knockout(Wall wall, int brickX, int brickY) {
    game.incrementScore(50);
    Bonus b = new TurretBonus(game, x, y, width, height);
    game.addBonus(b);
    wall.clearBrick(brickX, brickY);
  }
}