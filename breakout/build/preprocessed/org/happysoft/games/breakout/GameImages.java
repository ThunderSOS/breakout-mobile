/*
 * GameImages.java
 *
 * Created on 16 June 2007, 18:12
 */
package org.happysoft.games.breakout;

import java.io.IOException;
import java.io.InputStream;
import javax.microedition.lcdui.Image;
import javax.microedition.media.*;
import javax.microedition.media.control.VolumeControl;

/**
 * @author Chris Francis
 */
public class GameImages {
  
  private Image[] brickTypes = new Image[] { };
  private Image brickPink, brickGrey, brickGreyLight, brickOrange, brickBlue, brickGreen, brickYellow, brickPurple, brickRed, brickCyan;
  private Image bonusSticky, bonusWide, bonusTurret;
  
  private Image font = null;
    
  private Image paddleStandard, paddleTurret, paddleSticky, paddleWide;
  private Image ball;

  public GameImages() throws BreakoutGameException {
    loadImages();
  }
    
  private void loadImages() throws BreakoutGameException {
    try {     
      brickPink = Image.createImage("/brick_pink.png");
      brickRed = Image.createImage("/brick_red.png");
      brickBlue = Image.createImage("/brick_blue.png");
      brickGreen = Image.createImage("/brick_green.png");
      brickPurple = Image.createImage("/brick_purple.png");      
      brickYellow = Image.createImage("/brick_yellow.png");
      brickGreyLight = Image.createImage("/brick_grey_light.png");
      brickOrange = Image.createImage("/brick_orange.png");
      brickGrey = Image.createImage("/brick_grey.png");
      brickCyan = Image.createImage("/brick_cyan.png");      
      
      paddleStandard = Image.createImage("/paddle_standard.png");
      paddleTurret = Image.createImage("/paddle_turret.png");
      paddleSticky = Image.createImage("/paddle_sticky.png");
      paddleWide = Image.createImage("/paddle_wide.png");
      
      ball = Image.createImage("/ball.png");

      bonusTurret = Image.createImage("/bonus_turret.png");        
      bonusSticky = Image.createImage("/bonus_sticky.png");            
      bonusWide = Image.createImage("/bonus_wide.png");   
      font = Image.createImage("/font.png");      
      
    } catch (IOException ioe) {
      throw new BreakoutGameException("Failed to load game images - " + ioe.getMessage());
    }
  }
  
  public Image getBrickImage(int brickType) {
    switch(brickType & 7) {      
      case(0):
        return brickPink;
         
      case(1):
        return brickRed;

      case(2):
        return brickBlue;
      
      case(3):
        return brickGreen;
        
      case(4):
        return brickYellow;
              
      case(5):
        return brickPurple;
       
      case(6):
        return brickCyan;
      
      case(7):
        return brickOrange;
    }
    return null;
  }

  public Image getImmovableBrickImage() {
    return brickGrey;
  }
  
  public Image getBrickFlashImage() {
    return brickGreyLight;
  }
  
  public Image getBallImage() {
    return ball;
  }
  
  public Image getPaddleStandard() {
    return paddleStandard;
  }

  public Image getPaddleSticky() {
    return paddleSticky;
  }
  
  public Image getPaddleWide() {
    return paddleWide;
  }
  
  public Image getPaddleTurret() {
    return paddleTurret;
  }
  
  public Image getBonusSticky() {
    return bonusSticky;
  }

  public Image getBonusWide() {
    return bonusWide;
  }
  
  public Image getBonusTurret() {
    return bonusTurret;
  }
  
  public Image getFont() {
    return font;
  }
  
  public void destroyImage(Image image) {
    image = null;
  }
  
}
