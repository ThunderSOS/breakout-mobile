/*
 * Bonus.java
 *
 * Created on 29 June 2007, 22:20
 *
 * @author Chris Francis
 */
package org.happysoft.games.breakout;

import javax.microedition.lcdui.Graphics;

public abstract class Bonus extends MovingGameObject {
  
  public static final int ALIVE = 0;
  public static final int DEAD = 1;
  
  //protected Breakout game = null;  
  protected int state = 0;
  protected Breakout game;
  
  /** Creates a new instance of Bonus */
  public Bonus(Breakout game, int x, int y, int width, int height) {
    super(x, y, width, height);
    this.game = game;
    setDX(0);
    setDY(3);
  }
  
  public int getState() {
    return state;
  }
  
  public void move() {
    incrementY();
    if(y > game.getScreenSize().height) {
      state = DEAD;
    }
    if(getBoundingRect().intersects(game.getPaddle().getBoundingRect())) { 
      game.getBonusRegistry().setCurrentBonus(this);
      game.removeBonus(this);
      activate();
    }
  }
  
  public void paint(Graphics g) {
    if(state == ALIVE) {
      g.drawImage(game.getGameImages().getBonusSticky(), x, y, Graphics.TOP | Graphics.LEFT);
    }
  }
  
  protected abstract void activate();  
  
  public void deactivate() {
    state = DEAD;
    Paddle normalPaddle = new Paddle(game, 16);
    setGamePaddle(normalPaddle);   
  }
  
  protected void setGamePaddle(Paddle paddle) {
    Paddle oldPaddle = game.getPaddle();
    paddle.x = oldPaddle.x; 
    paddle.movingLeft = oldPaddle.movingLeft;
    paddle.movingRight = oldPaddle.movingRight;    
    game.setPaddle(paddle);
  }
  
}
