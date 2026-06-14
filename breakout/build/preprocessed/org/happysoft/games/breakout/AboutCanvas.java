/*
 * AboutCanvas.java
 * Created on 10 December 2007, 13:31
 *
 * @author Chris Francis
 */

package org.happysoft.games.breakout;

import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;
import javax.microedition.media.*;

public class AboutCanvas extends GameCanvas {
  
  private Graphics g = null;
  
  private Breakout game;
  private int width, height;
  
  private Message author, copyright, breakout, rsk = null;

  
  /** Creates a new instance of NameInputCanvas */
  public AboutCanvas(Breakout game) {
    super(false);
    this.game = game;
    setFullScreenMode(true);
    width = getWidth();
    height = getHeight();
    g = getGraphics();
    breakout = new Message("BREAKOUT", game, 1);
    
    author = new Message("BY CHRIS FRANCIS", game, 9);
    copyright = new Message("COPYRIGHT 2007", game, 11);
    
    rsk = new Message("OK", game, (width-16)/8, (height-6)/8);
  }
  
  public void paint() throws BreakoutGameException {  
    // fill solid background
    g.setColor(0x00007F);
    g.fillRect(0, 0, width, height);  
    paintTitle();
    paintText();
    flushGraphics();
  }
  
  private void paintTitle() throws BreakoutGameException {
//    g.setColor(0xDFDF00);
//    g.fillRect(breakout.x-6, breakout.y-6, breakout.width+12, breakout.height+12);
    g.setColor(0xDF0000);
    g.fillRect(breakout.x-2, breakout.y-2, breakout.width+4, breakout.height+4);
    breakout.paint(g);
  }
  
  private void paintText() throws BreakoutGameException {
    author.paint(g);
    copyright.paint(g);
    rsk.paint(g);
  }
  
  public void keyPressed(int keyCode)  {
    switch(keyCode) {
      case(-7):
      case(-8):
      case(-11):
        game.startMenu();
        break;
    }
  }
}
  

