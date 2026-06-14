
package org.happysoft.games.breakout;

import org.happysoft.games.*;

import java.util.Vector;

import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;

/**
 * BreakOut for MIDP
 * Copyright Chris Francis 2007
 */
public class Breakout extends MIDlet implements Runnable { 
  
  public static final int INIT = -1;
  public static final int MENU_RUNNING = 0;
  public static final int GAME_RUNNING = 1;
  public static final int HISCORES_RUNNING = 2;
  public static final int NAME_ENTRY_RUNNING = 3;
  public static final int ABOUT_RUNNING = 4;
  
  private int INITIAL_LIVES = 6;
  
  private int gameState = INIT;
  
  private boolean gameAssetsLoaded = false;
  
  private Display display;
  private BreakoutCanvas game;
  private MenuCanvas menu;
  private AboutCanvas about;
  private HiScoreCanvas hiscores;
  private NameInputCanvas nameEntry;
  
  private boolean alive = true, paused = false;
  
  private Thread gameThread;
  private long startTime = 0, endTime = 0;
  
  private Vector bonuses = new Vector();
  private Vector balls = new Vector();  
  private Vector bullets = new Vector();
  
  public static int MAX_LEVEL = 5;                                                                
  public static int FRAME_RATE = 35;
  
  private int delay = 1000 / FRAME_RATE;
    
  private Wall wall = null;
  private Paddle paddle = null;
  private BonusRegistry bonusRegistry = null;
  private Message message = null;
  
  private int score = 0, currentLevel = 1, lives = INITIAL_LIVES;
  
  private Dimension screenSize;
  
  private GameImages gameImages = null;
  private GameFont font = null;
  
  private Object gameSyncLock = new Object();
  
  public Breakout() { 
    try {
      if(!gameAssetsLoaded) {
        gameImages = new GameImages();
        font = new GameFont(this);
        bonusRegistry = new BonusRegistry(this);
        gameAssetsLoaded = true;
      }
    } catch (BreakoutGameException bge) {
      // exit
    }
    gameThread = new Thread(this);
    gameThread.start();
    display = Display.getDisplay(this);

    game = new BreakoutCanvas(this); 
    screenSize = new Dimension(game.getWidth(), game.getHeight()); 
    
    menu = new MenuCanvas(this);
    startMenu();
  }  
  
  public void startMenu() {
    menu = new MenuCanvas(this);
    display.setCurrent(menu);
    gameState = MENU_RUNNING;
    hiscores = null;
  } 
  
  public void startGame() {
    game = new BreakoutCanvas(this);
    currentLevel = 1;
    nextLevel();
    resetPaddle();
    resume();
    score = 0;
    setMessage(null);
    gameState = GAME_RUNNING;
    display.setCurrent(game); 
    menu = null;
    hiscores = null;
    SoundEffect.getInstance();
  }
  
  public void startHiScores() {
    hiscores = new HiScoreCanvas(this);
    display.setCurrent(hiscores);
    gameState = HISCORES_RUNNING;
    game = null;
    menu = null;
  }
  
  public void startNameEntry(int score) {
    nameEntry = new NameInputCanvas(this, score);
    display.setCurrent(nameEntry);
    gameState = NAME_ENTRY_RUNNING;
  }
  
  public void startAbout() {
    about = new AboutCanvas(this);
    display.setCurrent(about);
    gameState = ABOUT_RUNNING;
  }
  
  public void pause() {
    paused = true;
    if(message != null) {
      message.clear();
    }
    setMessage(new Message("PAUSED", this, false, false));
  }
  
  public void resume() {
    paused = false;
    setMessage(null);
  }
  
  public boolean isPaused() {
    return paused;
  }
    
  public void incrementScore(int amount) {
    score += amount;
  }
  
  public int getScore() {
    return score;
  }
  
  public void loseLife() {
    lives--;
    if(lives < 0) {
      startNameEntry(score);
      lives = INITIAL_LIVES;
    }
  }
  
  public int getLives() {
    return lives;
  }  
   
  public void setPaddle(Paddle paddle) {
    this.paddle = paddle;
  } 
  
  public void setMessage(Message newMessage) {
    message = newMessage;    
  }
  
  public Message getCurrentMessage() {
    return message;
  }
  
  public void clearCurrentMessage() {
    if(message != null) {
      message.clear();
    }
    message = null;
  }
  
  public void nextLevel() {
    MessageController.getInstance(this).setMessage("LEVEL " + currentLevel, 2500, false, true);
    getBonusRegistry().setCurrentBonus(null);
    currentLevel %= MAX_LEVEL + 1;
    Level level = new Level("/level" + currentLevel + ".dat");
    wall = level.buildWall(this);    
    balls = wall.getBallArray();
    currentLevel++;
    if(currentLevel > MAX_LEVEL) {
      currentLevel = 1;
    }    
  }
  
  public void resetPaddle() {
    paddle = new Paddle(this, 12);  
    paddle.x = getCentreX(paddle.width);
    bonuses.removeAllElements();
    bonusRegistry.setCurrentBonus(null);
    bullets = new Vector();
  }
      
  public boolean releaseBall() {
    for(int i = 0; i < balls.size(); i++) {
      Ball b = (Ball) balls.elementAt(i);
      int state = b.getState();
      if(state == Ball.BALL_NEW) {
        b.setState(Ball.BALL_ALIVE);
        return true;
      }
    }
    return false;
  }

    
  public Wall getWall() {
    return wall;
  }
  
  public Paddle getPaddle() {
    return paddle;
  }
   
  public void run() {
    while(alive) {
      switch(gameState) {
        case(ABOUT_RUNNING):
          doAboutLoop();
          break;
        
        case(GAME_RUNNING):
          doGameLoop();
          break;
          
        case(MENU_RUNNING):
          doMenuLoop();
          break;
          
        case(HISCORES_RUNNING):
          doHiScoresLoop();
          break;
          
        case(NAME_ENTRY_RUNNING):
          doNameEntryLoop();
          break;
          
      }
    }
  }
  
  private void doAboutLoop() {
    try {      
      about.paint();
      gameThread.sleep(delay);

    } catch (Exception ie) {
      ie.printStackTrace();
    }
  }
  
  private void doNameEntryLoop() {
    try {
      nameEntry.paint();
      gameThread.sleep(delay);

    } catch (Exception ie) {
      ie.printStackTrace();
    }
  } 
  
  private void doGameLoop() {
    try {
      synchronized(gameSyncLock) {
        startTime = System.currentTimeMillis();
        if(!paused) {
          updateGameObjects();
          bonusRegistry.checkStatus();
          game.paint();
          if(wall.roundOver()) {
            nextLevel();
          }
        } else {
          game.paint();
        }
        endTime = System.currentTimeMillis();
        long elapsed = endTime-startTime;
        if(elapsed < delay) {
          gameThread.sleep(delay - (endTime - startTime));
        }
        //System.out.println("Actual frame rate: " +(delay - (endTime - startTime)));
      }
    } catch (Exception ie) {
      ie.printStackTrace();
    }
  }

  private void doMenuLoop() {
    try {
      menu.paint();
      gameThread.sleep(delay);

    } catch (Exception ie) {
      ie.printStackTrace();
    }
  }

  private void doHiScoresLoop() {
    try {
      hiscores.paint();
      gameThread.sleep(delay);

    } catch (Exception ie) {
      ie.printStackTrace();
    }
  }
  
  public void addBonus(Bonus bonus) {
    bonuses.addElement(bonus);
  }
  
  public void removeBonus(Bonus bonus) {
    bonuses.removeElement(bonus);
  }

  public void addBullet(Bullet bullet) {
    bullets.addElement(bullet);
  }
  
  public void removeBullet(Bullet bullet) {
    bullets.removeElement(bullet);
  }
  
  public Vector getBonuses() {
    return bonuses;
  }

  public Vector getBullets() {
    return bullets;
  }
  
  public BonusRegistry getBonusRegistry() {
    return bonusRegistry;
  }
      
  
  public int getLiveBallCount() {
    int count = 0;  
    for(int i = 0; i < balls.size(); i++) {
      Ball b = (Ball) balls.elementAt(i);
      int state = b.getState();
      if(state == Ball.BALL_ALIVE || state == Ball.BALL_NEW || state == Ball.BALL_LOST) {
        count++;
      }
    }
    return count;
  }

  private void updateGameObjects() {    
    for(int i = 0; i < balls.size(); i++) {
      Ball b = (Ball) balls.elementAt(i);
      b.move();
    }    
    paddle.move();
    
    for (int i = 0; i < bonuses.size(); i++) {
      ((Bonus)bonuses.elementAt(i)).move();
    }
    
    for (int i = 0; i < bullets.size(); i++) {
      ((Bullet)bullets.elementAt(i)).move();
    }
  }
  
  public void paintGameObjects(Graphics g) throws BreakoutGameException {
    wall.paint(g);
    paddle.paint(g);
    
    for(int i = 0; i < balls.size(); i++) {
      Ball b = (Ball) balls.elementAt(i);
      b.paint(g);
    }
    
    for(int i = 0; i < bonuses.size(); i++) {
      Bonus b = (Bonus) bonuses.elementAt(i);
      b.paint(g);
    }
    
    for(int i = 0; i < bullets.size(); i++) {
      Bullet b = (Bullet) bullets.elementAt(i);
      b.paint(g);
    }
    
    if(message != null) {
      message.paint(g);
    }
  }
  
  public Dimension getScreenSize() {
    return screenSize;
  }
   
  public Display getDisplay() {
    return display;
  }
  
  public void startApp() {
  }
  
  public GameImages getGameImages() {
    return gameImages;
  }
  
  public GameFont getGameFont() {
    return font;
  }
  
  public int getCentreX(int objectWidth) {
    return (screenSize.width-objectWidth)/2;
  }
  
  public int getCentreY(int objectHeight) {
    return (screenSize.height-objectHeight)/2;
  }
    
  public void pauseApp() {
    System.out.println("pause called");
    if(!paused) {
      pause();
      notifyPaused();
    }
  }
  
  public void destroyApp(boolean unconditional) {
    System.out.println("Dispose");
    display.setCurrent(null);
    alive = false;
    MessageController.getInstance(this).dispose();
    SoundEffect.getInstance().dispose();
  }
  
}
