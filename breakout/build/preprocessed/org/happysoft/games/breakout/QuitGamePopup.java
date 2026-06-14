/*
 * PopupMessage.java
 * Created on 27 November 2007, 13:06
 *
 * @author Chris Francis
 */
package org.happysoft.games.breakout;

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import org.happysoft.games.Rectangle;

public class QuitGamePopup extends GameObject {
    
  private Breakout game;  
  private Message yes, no, message;
  
  private boolean yesSelected = true;
  
  private PopupListener listener;
    
  /** Creates a new instance of PopupMessage */
  public QuitGamePopup(Breakout game, PopupListener listener, String message) {
    super(0, 0, 0, 0);  
    width = game.getScreenSize().width/2;
    height = game.getScreenSize().height/3+4;
    x = game.getCentreX(width);
    y = game.getCentreY(height)-2;
    System.out.println("x = " + x);
    this.game = game;
    this.listener = listener;
    this.message = new Message(message, game, game.getCentreX(message.length()*8)/8, (y/8)+2);
    yes = new Message("YES", game, (x/8)+1, (y/8)+8);
    no = new Message("NO", game, (x+width)/8-2, (y/8)+8);
  }
  
  public void paint(Graphics g) throws BreakoutGameException {
    g.setColor(0x009F9F);
    //g.setColor(0x0000DF);
    g.fillRoundRect(x, y, width, height, 16, 16);
    message.paint(g);
    paintHighLight(g);
    yes.paint(g);
    no.paint(g);
  }
  
  private void paintHighLight(Graphics g) {
    g.setColor(0x0000DF);
    //g.setColor(0x00007F);
    //g.setColor(0x009F9F);
    Rectangle rect = yesSelected ? yes.getBoundingRect() : no.getBoundingRect();
    g.fillRect(rect.x - 2, rect.y - 2, rect.width + 4, rect.height + 4);
  }
  
  public void flipSelection() {
    yesSelected = !yesSelected;
  }
  
  public boolean isYesSelected() {
    return yesSelected;
  }
  
  public void handlePopupKeys(final int keyCode) {
    switch(keyCode) {
      case Canvas.KEY_NUM4:
      case Canvas.KEY_NUM6:
      case -3:
      case -4:
        flipSelection();
        break;
        
      case -11:
      case -8:
        listener.notifyPopupClosed();
        break;
        
      case Canvas.KEY_NUM5:
      case -5:
      case -6:
        if(isYesSelected()) {
          listener.notifyPopupConfirm();
        } else {
          listener.notifyPopupClosed();
        }
        break;
    }
  }
  
}
