/*
 * NameInputCanvas.java
 * Created on 06 December 2007, 15:33
 *
 * @author Chris Francis
 */

package org.happysoft.games.breakout;

import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

public class NameInputCanvas extends GameCanvas implements PopupListener {
  
  private Graphics g;
  private Breakout game;
  private int width, height;
  
  private Message line1, line2, line3, line4, enterName, congratulations, breakout, rsk = null;
  private Message nameMessage = null;
  private int x, y, startX, startY;
  private int score;
  
  private int currentChar = 0;
  private char[] name = {'.', '.', '.'};
  
  private QuitGamePopup popup = null;
  private boolean showingPopup = false;
  
  /** Creates a new instance of NameInputCanvas */
  public NameInputCanvas(Breakout game, int score) {
    super(false);
    this.game = game;
    this.score = score;
    setFullScreenMode(true);
    width = getWidth();
    height = getHeight();
    g = getGraphics();
    breakout = new Message("BREAKOUT", game, 1);    
    congratulations = new Message("CONGRATULATIONS!", game, 5);
    enterName = new Message("ENTER YOUR INITIALS", game, 7);
    line1 = new Message("A B C D E F G H I", game, 11);    
    line2 = new Message("J K L M N O P Q R", game, 13);    
    line3 = new Message("S T U V W X Y Z =", game, 15);
    line4 = new Message("# ", game, 17);
    nameMessage = new Message(". . .", game, 20);
    rsk = new Message("OK", game, (width-16)/8, (height-6)/8);
    
    popup = new QuitGamePopup(game, this, "QUIT?");
    
    line2.x = line1.x;
    line3.x = line2.x;
    line4.x = line3.x;
    x = line1.getBoundingRect().x-1;
    y = line1.getBoundingRect().y-1;
    startX = x;
    startY = y;
  }
  
  public void paint() throws BreakoutGameException {  
    // fill solid background
    g.setColor(0x00007F);
    g.fillRect(0, 0, width, height);  
    paintTitle();
    paintHighlight();
    paintOptions();
    paintName();
    paintCursor();
    if(showingPopup) {
      popup.paint(g);
    }
    flushGraphics();
  }
  
  private void paintTitle() throws BreakoutGameException {
//    g.setColor(0xDFDF00);
//    g.fillRect(breakout.x-6, breakout.y-6, breakout.width+12, breakout.height+12);
    g.setColor(0xDF0000);
    g.fillRect(breakout.x-2, breakout.y-2, breakout.width+4, breakout.height+4);
    breakout.paint(g);
  }

  private void paintName() throws BreakoutGameException {
    nameMessage.paint(g);
  }
  
  private void paintOptions() throws BreakoutGameException {
    line1.paint(g);
    line2.paint(g);
    line3.paint(g);
    line4.paint(g);
    enterName.paint(g);
    congratulations.paint(g);
    rsk.paint(g);
  }
  
  private void paintHighlight() {
    g.setColor(0x009F9F);
    g.drawRect(x, y, 9, 9);
  }
  
  private void paintCursor() {
    g.setColor(0x009F9F);
    g.drawLine(nameMessage.x+(currentChar*16)-1, nameMessage.y+nameMessage.height, nameMessage.x+(currentChar*16)+7, nameMessage.y+nameMessage.height);
  }
  
  private void selectLetter() {
    int line = ((y-startY)/16);
    int column = ((x-startX)/16);
    char c = (char)(line*9+column+65);    
    if((int)c == 91) {
      c = ' ';
    }
    if((int)c == 92) {
      doDelete();
      return;
    }
    name[currentChar] = c;
    nameMessage.setMessage(name[0] + " " + name[1] + " " + name[2]);
    currentChar++;
    if(currentChar > 2) {
      currentChar = 2;
    }
  }
  
  private void doDelete() {
    name[currentChar] = '.';
    nameMessage.setMessage(name[0] + " " + name[1] + " " + name[2]);
    currentChar--;
    if(currentChar < 0) {
      currentChar = 0;
    }
  }
  
  private void save() {
    HiScoreTable.getInstance().addHiscore(new String(name), score);
    game.startHiScores();
  }
 
  public void keyPressed(int keyCode) {
    if(showingPopup) {
      popup.handlePopupKeys(keyCode);
    } else {
      handleInputKeys(keyCode);
    }
  }

  private void handleInputKeys(final int keyCode) {
    switch(keyCode) {
      case(-1):
      case(KEY_NUM2):
        if(y > startY) {
          y = y - 16;
        }
        break;
        
      case(-2):
      case(KEY_NUM8):
        if(y == startY + 32 && x == startX) {
          y = y + 16;
        }
        if(y < startY + 32) {
          y = y + 16;
        }
        break;
        
      case(-3):
      case(KEY_NUM4):
        if(x > startX) {
          x = x - 16;
        }
        break;
        
      case(-4):
      case(KEY_NUM6):
        if(y <= startY + 32 && x < startX + 120) {
          x = x + 16;
        }
        break;
        
      case(KEY_NUM5):
      case(-5):
        selectLetter();
        break;
        
      case(-7):
        save();
        break;
        
      case(-8):
      case(-11):
        showingPopup = true;
        break;
    }
  }

  public void notifyPopupClosed() {
    showingPopup = false;
  }
  
  public void notifyPopupConfirm() {
    showingPopup = false;
    game.startMenu();
  }
}
