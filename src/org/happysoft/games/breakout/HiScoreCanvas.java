
package org.happysoft.games.breakout;

import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

import javax.microedition.rms.*;

/*
 * HiScoreCanvas.java
 * Created on 05 December 2007, 12:05
 *
 * @author Chris Francis
 */
public class HiScoreCanvas extends GameCanvas {
  
  private int width;
  private int height;
  private Graphics g = null;

  private Breakout game;
  
  private Message rsk;
  private HiScoreData[] scores;
  
  private Message[] ranks = new Message[10];
  private Message breakout = null;
  
  public HiScoreCanvas(Breakout game) {
    super(false);
    this.game = game;
    setFullScreenMode(true);
    width = getWidth();
    height = getHeight();
    g = getGraphics();
    rsk = new Message("MENU", game, (width-32)/8, (height-6)/8);
    breakout = new Message("BREAKOUT", game, 1);
        
    HiScoreTable hs = HiScoreTable.getInstance();
    scores = hs.getHiScores();
    for(int i = 0; i < 10; i++) {
      ranks[i] = createDummyRanking(i+1);
    }    
    for(int i = 0; i < (scores.length < 10 ? scores.length : 10); i++) {
      ranks[i] = createRanking(i+1, scores[i]);
    }
  }
  
  public void paint() throws BreakoutGameException {  
    // fill solid background
    g.setColor(0x00007F);
    g.fillRect(0, 0, width, height);  
    paintTitle();
    paintRankings();
    paintExitOption();
    flushGraphics();
  }
  
  private void paintTitle() throws BreakoutGameException {
//    g.setColor(0xDFDF00);
//    g.fillRect(breakout.x-6, breakout.y-6, breakout.width+12, breakout.height+12);
    g.setColor(0xDF0000);
    g.fillRect(breakout.x-2, breakout.y-2, breakout.width+4, breakout.height+4);
    breakout.paint(g);
  }
  
  private void paintRankings() throws BreakoutGameException {
    for(int i = 0; i < 8; i++) {
      ranks[i].paint(g);
    }
  }
  
  private void paintExitOption() throws BreakoutGameException {
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
  
  private String padNumber(int number, int places, String paddingChar) {
    String n = "" + number;
    while(n.length() < places) {
      n = paddingChar + n;
    }
    return n;
  }
  
  private Message createRanking(int rank, HiScoreData data) {
    String score = padNumber(data.score, 5, "0");
    String messageString = rank + "..." + data.username + "..." + score;
    Message m = new Message(messageString, game, rank*2+4);
    return m;
  }
  
  private Message createDummyRanking(int rank) {
    String score = padNumber(0, 5, "0");
    String messageString = rank + "...AAA..." + score;
    Message m = new Message(messageString, game, rank*2+4);
    return m;
  }  
}
