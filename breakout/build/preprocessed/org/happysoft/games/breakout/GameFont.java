/*
 * GameFont.java
 * Created on 22 November 2007, 11:57
 *
 * @author Chris Francis
 */
package org.happysoft.games.breakout;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

public class GameFont {
  
  public static final int FONT_SIZE = 8;
  
  private String letters = "0123456789:!^=v?$ABCDEFGHIJKLMNOPQRSTUVWXYZ#>< .";
  private Image[] letterImages = new Image[48];
  private Breakout game = null;
  
  /** Creates a new instance of GameFont */
  public GameFont(Breakout game) {
    this.game = game;
    buildImages();
  }
  
  private void buildImages() { 
    Image font = game.getGameImages().getFont();
    for(int y = 0; y < 6; y++) {
      for(int x = 0; x < 8; x++) {
        letterImages[y*8+x] = Image.createImage(font, x*FONT_SIZE, y*FONT_SIZE, FONT_SIZE, FONT_SIZE, Sprite.TRANS_NONE);
      }
    }
    game.getGameImages().destroyImage(font);
  }
  
  public Image getLetter(char c) {
    int idx = letters.indexOf(c);
    return letterImages[idx >= 0 ? idx : 46];
  }
  
}
