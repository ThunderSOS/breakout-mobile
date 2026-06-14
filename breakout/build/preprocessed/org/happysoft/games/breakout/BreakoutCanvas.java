
/*
 * Board.java
 *
 * Created on 05 May 2007, 20:28
 */
package org.happysoft.games.breakout;

import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.GameCanvas;

/**
 * Copyright 2007, Chris Francis
 */
public class BreakoutCanvas extends GameCanvas implements PopupListener {
  
  private int width;
  private int height;
  private Graphics g = null;
  
  //private Font smallFont = Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN, Font.SIZE_SMALL);
  //private int fontHeight = smallFont.getHeight();
  private Breakout game;
  private QuitGamePopup popup = null;  
  private Message scoreMessage, bonusTime = null;
  private boolean showingPopup = false;
  
  public BreakoutCanvas(Breakout game) {
    super(false);
    this.game = game;
    setFullScreenMode(true);
    width = getWidth();
    height = getHeight();
    g = getGraphics();
    scoreMessage = new Message("SCORE:0", game, 0, (height-5)/8);
    scoreMessage.y = scoreMessage.y+1;
    bonusTime = new Message("00", game, (width-16)/8, (height-5)/8);
    bonusTime.y = bonusTime.y+1;
  }
  
  public void paint() throws BreakoutGameException {  
    // fill solid background
    g.setColor(0x00007F);
    g.fillRect(0, 0, width, height);    
    game.paintGameObjects(g);     
    paintScore();
    if(game.getBonusRegistry().getCurrentBonus() != null) {
      paintBonusTime();
    } else {
      paintLivesLeft();
    }
    if(showingPopup) {
      popup.paint(g);      
    }
    flushGraphics();
  }
  
  private void paintScore() throws BreakoutGameException {    
    String s = "SCORE:" + game.getScore();
    scoreMessage.setMessage(s);
    scoreMessage.paint(g);
  }
  
  private void paintLivesLeft() {
    int lives = game.getLives();
    Image ballImage = game.getGameImages().getBallImage();
    for(int i = 0; i < lives; i++) {
      g.drawImage(ballImage, width - (lives-i)*9, height-10, Graphics.TOP | Graphics.LEFT);
    }
  }
  
  private void paintBonusTime() throws BreakoutGameException {
    int timeRemaining = game.getBonusRegistry().getTimeToLive();
    String s = "" + timeRemaining;
    if(s.length() == 1) {
      s = "0" + s;
    }
    bonusTime.setMessage(s);
    bonusTime.paint(g);
  }
  
  private void showPopup() {
    game.pause();
    popup = new QuitGamePopup(game, this, "QUIT?");
    showingPopup = true;
  }
  
  public void keyRepeated(int keyCode) {
    if (!showingPopup) {
      handleGameKeys(keyCode);
    }        
  }
  
  public void keyReleased(int keyCode)  {
    game.getPaddle().setNotMoving();
  }
  
  public void keyPressed(int keyCode)  {
    if(!showingPopup) {
      handleGameKeys(keyCode);
    } else {
      popup.handlePopupKeys(keyCode);
    }
  }
  
  public void hideNotify() {
    game.pause();
  }

  private void handleGameFunctionKeys(final int keyCode) {
    switch(keyCode) {
      case KEY_NUM1:
        if(game.isPaused()) {
          game.resume();
        } else {
          game.pause();
        }
        break;
        
      case -7:
        showPopup();
        break;
        
      case KEY_POUND:
        try {
          game.nextLevel();
        } catch (Exception e) {
          //ignore
        }
        break;
    }
  }
    
  private void handleGameKeys(final int keyCode) {  
    if((getKeyStates() & LEFT_PRESSED) != 0) {
      game.getPaddle().setMovingLeft();
    }
    if((getKeyStates() & RIGHT_PRESSED) != 0) {
      game.getPaddle().setMovingRight();
    }    
    if((getKeyStates() & FIRE_PRESSED) != 0) {      
      game.getPaddle().fire();
    }
    handleGameFunctionKeys(keyCode);
  }
  
  public void notifyPopupClosed() {
    showingPopup = false;
    popup = null;
  }
  
  public void notifyPopupConfirm() {
    showingPopup = false;
    popup = null;
    game.startMenu();
  }
}

