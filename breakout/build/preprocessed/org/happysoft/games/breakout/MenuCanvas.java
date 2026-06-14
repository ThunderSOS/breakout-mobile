/*
 * MenuCanvas.java
 * Created on 26 November 2007, 17:02
 *
 * @author Chris Francis
 */
package org.happysoft.games.breakout;

import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.GameCanvas;
import org.happysoft.games.Rectangle;

/**
 * Copyright 2007, Chris Francis
 */
public class MenuCanvas extends GameCanvas implements PopupListener {
  
  private int width;
  private int height;
  private Graphics g = null;

  private Breakout game;
  
  private QuitGamePopup popup = null;
  private Message breakout, startOption, scoresOption, aboutOption, exitOption, lsk; //, rsk;
  private Rectangle highLight = null;
  
  private int[] menuY = new int[] {9, 11, 13, 17};
  private int currentMenu = 0;
  
  private boolean showingExitPopup = false;
  
  public MenuCanvas(Breakout game) {
    super(false);
    this.game = game;
    setFullScreenMode(true);
    width = getWidth();
    height = getHeight();
    g = getGraphics();
    startOption = new Message("NEW GAME", game, menuY[0]);
    scoresOption = new Message("HI SCORES", game, menuY[1]);
    aboutOption = new Message("ABOUT", game, menuY[2]);
    exitOption = new Message("EXIT GAME", game, menuY[3]);
    lsk = new Message("SELECT", game, 0, (height-6)/8);
    highLight = exitOption.getBoundingRect();
    popup = new QuitGamePopup(game, this, "EXIT?");
    breakout = new Message("BREAKOUT", game, 1);
  }
  
  public void paint() throws BreakoutGameException {  
    // fill solid background
    g.setColor(0x00007F);
    g.fillRect(0, 0, width, height); 
    paintTitle();
    paintHighLight();
    paintMenuOptions();
    flushGraphics();
  }
  
  private void paintMenuOptions() throws BreakoutGameException {
    startOption.paint(g);
    scoresOption.paint(g);
    aboutOption.paint(g);
    exitOption.paint(g);
    lsk.paint(g);
    if(showingExitPopup) {
      popup.paint(g);
    }
  }
  
  private void paintTitle() throws BreakoutGameException {
//    g.setColor(0xDFDF00);
//    g.fillRect(breakout.x-6, breakout.y-6, breakout.width+12, breakout.height+12);
    g.setColor(0xDF0000);
    g.fillRect(breakout.x-2, breakout.y-2, breakout.width+4, breakout.height+4);
    breakout.paint(g);
  }
  
  private void paintHighLight() {
    g.setColor(0x0000DF);
    g.fillRect(highLight.x-2, menuY[currentMenu]*8-2, highLight.width+4, highLight.height+4);
  }

  private void handleMenuSelect() {
    switch(currentMenu) {
      case(0):
        game.startGame();
        break;

      case(1):
        game.startHiScores();
        break;
        
      case(2):
        game.startAbout();
        break;

      case(3):
        showingExitPopup = true;
        break;
    }
  }
  
  public void keyPressed(int keyCode)  {
    if(!showingExitPopup) {
      handleMenuKeys(keyCode);
      
    } else {
      popup.handlePopupKeys(keyCode);
    }    
  }
  
  public void notifyPopupClosed() {
    showingExitPopup = false;
  }
  
  public void notifyPopupConfirm() {
    game.destroyApp(true);
    game.notifyDestroyed();
  }

  private void handleMenuKeys(final int keyCode) {
    switch(keyCode) {
      case -1:
      case KEY_NUM2:
        currentMenu--;
        if(currentMenu < 0) {
          currentMenu = 3;
        }
        break;
        
      case -2:
      case KEY_NUM8:
        currentMenu++;
        if(currentMenu > 3) {
          currentMenu = 0;
        }
        break;
        
      case KEY_NUM5:
      case -5:
      case -6:
        handleMenuSelect();
        break;
    }
  }

}


