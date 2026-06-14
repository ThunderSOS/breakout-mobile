/*
 * BonusRegistry.java
 *
 * Created on 24 June 2007, 13:33
 */
package org.happysoft.games.breakout;

/**
 * @author Chris Francis
 */
public class BonusRegistry {
  
  private Breakout game = null;
  
  private int TIME_OUT = 30000;
  private Bonus currentBonus = null;
  
  private int timeAlive = 0;
  
  
  /** Creates a new instance of BonusRegistry */
  public BonusRegistry(Breakout game) {
    this.game = game;
  }
 
  public void setCurrentBonus(Bonus bonus) {
    if(bonus == null && currentBonus != null) {
      currentBonus.deactivate();
    }
    currentBonus = bonus;
    timeAlive = 0;
  }
  
  public Bonus getCurrentBonus() {
    return currentBonus;
  }
  
  public boolean bonusActive() { 
    return timeAlive < TIME_OUT;
  }
   
  protected void checkStatus() {
    if(currentBonus != null) {
      timeAlive += Breakout.FRAME_RATE;
      if(timeAlive > TIME_OUT) {
        currentBonus.deactivate();
        currentBonus = null;
      }
    }
  }
  
  public int getTimeToLive() {
    if(currentBonus != null) {
      return (int)((TIME_OUT - timeAlive)/1000);
    } else {
      return 0;
    }
  }
  
}
