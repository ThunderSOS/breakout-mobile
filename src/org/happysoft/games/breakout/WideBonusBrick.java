
/*
 * WideBonusBrick.java
 *
 * Created on 18 August 2007, 16:53
 */
package org.happysoft.games.breakout;

/**
 * @author Chris Francis
 */
public class WideBonusBrick extends Brick {
  
  /** Creates a new instance of WideBonusBrick */
  public WideBonusBrick(Breakout game, int x, int y, int width, int height, int brickType) {
    super(game, x, y, width, height, brickType);
  }
   
  public void knockout(Wall wall, int brickX, int brickY) {
    game.incrementScore(50);
    Bonus b = new WideBonus(game, x, y, width, height);
    game.addBonus(b);
    wall.clearBrick(brickX, brickY);
  }
  
}
