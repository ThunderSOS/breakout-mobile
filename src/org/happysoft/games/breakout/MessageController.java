/*
 * MessageController.java
 * Created on 22 November 2007, 16:11
 *
 * @author Chris Francis
 */
package org.happysoft.games.breakout;

import org.happysoft.games.Stack;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MessageController implements Runnable {
  
  private Breakout game;
  private Timer messageTimer;  
  private Stack messageStack = new Stack();
  private boolean alive = true;
  
  private Thread thread;
  
  private static MessageController instance;
  
  private MessageController(Breakout game) {
    this.game = game;
    messageTimer = new Timer();
    thread = new Thread(this);
    thread.start();
  }
  
  public static MessageController getInstance(Breakout game) {
    return instance == null ? instance = new MessageController(game) : instance;
  }
  
  /** Creates a new instance of MessageController */
  public void setMessage (String message, int timeout, boolean flashing, boolean replaceable) {
    MessageAttributes ma = new MessageAttributes(message, timeout, flashing, replaceable);
    messageStack.push(ma);
  }
  
  public void dispose() {
    alive = false;
    messageTimer.cancel();
  }

  private void setGameMessage() {
    MessageAttributes nextMessage = (MessageAttributes) messageStack.pop();
    if(nextMessage != null) {
      Message m = new Message(nextMessage.message, game, nextMessage.flashing, nextMessage.isReplaceable);
      game.setMessage(m);
      messageTimer.schedule(new ClearMessageTimerTask(), new Date( System.currentTimeMillis()+nextMessage.timeout )  );
    }
  }
  
  public void run() {
    try {
      while(alive) {
        Thread.sleep(100);
        if(!messageStack.isEmpty()) {
          Message currentMessage = game.getCurrentMessage();
          if(currentMessage == null) { 
            setGameMessage();
          } else {
            if(currentMessage.isReplaceable()) {
              game.clearCurrentMessage();
              messageTimer.cancel();
              messageTimer = new Timer();
              setGameMessage();
            }
          }
        }
      }
    } catch (InterruptedException ie) {
    }
  }
  
  private class MessageAttributes {
    String message;
    int timeout;
    boolean flashing, isReplaceable;
    
    private MessageAttributes(String message, int timeout, boolean flashing, boolean isReplaceable) {
      this.message = message;
      this.timeout = timeout;
      this.flashing = flashing;
      this.isReplaceable = isReplaceable;
    }
  }
   
  private class ClearMessageTimerTask extends TimerTask {
   
    private ClearMessageTimerTask() {
    }
    
    public void run() { 
      if(game.getCurrentMessage().isReplaceable()) {
        game.clearCurrentMessage();
      }
    }    
  }
  
}
