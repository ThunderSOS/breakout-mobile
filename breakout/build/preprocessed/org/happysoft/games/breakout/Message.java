/*
 * Message.java
 * Created on 22 November 2007, 12:16
 *
 * @author Chris Francis
 */

package org.happysoft.games.breakout;

import javax.microedition.lcdui.Graphics;

public class Message extends GameObject implements Runnable {
  
  private String message;
  private Breakout game;
  private boolean flashing = false;
  private boolean isVolatile = false;
  private boolean visible = true;
  private boolean alive = true;
  private Thread thread = null;
  
  private static int FLASH_RATE = 150;
  
  /** Constructor for in-game messages */
  public Message(String message, Breakout game, boolean flashing, boolean isVolatile) {
    super(
        game.getCentreX(message.length()*GameFont.FONT_SIZE), 
        (game.getWall().getWallHeight() + game.getWall().getOffsetFromTop() + 2)*game.getWall().getBrickHeight(), 
        message.length()*GameFont.FONT_SIZE, 
        GameFont.FONT_SIZE
    );
    this.message = message;
    this.game = game;
    this.flashing = flashing;
    this.isVolatile = isVolatile;
    thread = new Thread(this);
    thread.start();
  }

  /** Constructor for x-centred permanent messages such as menu options */
  public Message(String message, Breakout game, int y) {
    super(
        game.getCentreX(message.length()*8), 
        y*GameFont.FONT_SIZE, 
        message.length()*GameFont.FONT_SIZE, 
        GameFont.FONT_SIZE
    );
    this.message = message;
    this.game = game;
  }

  /** Constructor for permanent messages such as menu options */
  public Message(String message, Breakout game, int x, int y) {
    super(
        x*GameFont.FONT_SIZE, 
        y*GameFont.FONT_SIZE, 
        message.length()*GameFont.FONT_SIZE, 
        GameFont.FONT_SIZE
    );
    this.message = message;
    this.game = game;
  }
  
  public void paint(Graphics g) throws BreakoutGameException {
    if(visible) {
      for(int i = 0; i < message.length(); i++) {
        g.drawImage(game.getGameFont().getLetter(message.charAt(i)), x+(i*GameFont.FONT_SIZE), y, Graphics.TOP|Graphics.LEFT);
      }
    }
  }
  
  public void setMessage(String message) {
    this.message = message;
  }
  
  public void clear() {
    alive = false;
  }
  
  public boolean isReplaceable() {
    return isVolatile;
  }
  
  public String getMessage() {
    return message;
  }
  
  public void run() {
    while(alive) {
      Thread.yield();
      try {
        Thread.sleep(FLASH_RATE);
        if(flashing) {
          visible = !visible;
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
  
}
