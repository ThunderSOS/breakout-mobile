/*
 * SoundEffect.java
 * Created on 10 December 2007, 14:52
 *
 * @author Chris Francis
 */

package org.happysoft.games.breakout;

import java.io.*;
import javax.microedition.media.*;
import javax.microedition.media.control.VolumeControl;

public class SoundEffect implements Runnable {
  
  private static SoundEffect instance = null;
  
  private Player player = null;
  private Thread t = null;
  
  private boolean alive = true;
  private int playBrickSound = 0;
  
  /** Creates a new instance of SoundEffect */
  private SoundEffect() {
    try {
      InputStream is = getClass().getResourceAsStream("/sound6.amr");
      System.out.println("InputStream: " + is);
      player = Manager.createPlayer(is, "audio/amr");
      System.out.println("Got player: " + player);
      player.realize();
      System.out.println("Player realized");
      // get volume control for player and set volume to max
      VolumeControl vc = (VolumeControl) player.getControl("VolumeControl");
      if(vc != null) {
        vc.setLevel(100);
      }
      player.prefetch();
      System.out.println("Prefetched");
      t = new Thread(this);
      t.start();
      
    } catch(Exception e) {
      e.printStackTrace();
    }
  }
  
  public static SoundEffect getInstance() {
    return instance == null ? instance = new SoundEffect() : instance;
  }
  
  public void run() {
    try {
      while(alive) {
        if(playBrickSound > 0) {
          player.start();
          playBrickSound--;        
        }
        Thread.sleep(10);
      }
       
    } catch(Exception e) {
      e.printStackTrace();
    }
  }
  
  public void playBrickNoise() {
    playBrickSound++;
  } 
  
  public void dispose() {
    alive = false;
  }
  
}
