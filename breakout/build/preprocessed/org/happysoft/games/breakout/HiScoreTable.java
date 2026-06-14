/*
 * HiScoreTable.java
 * Created on 05 December 2007, 13:21
 *
 * @author Chris Francis
 */

package org.happysoft.games.breakout;

import java.io.*;
import java.util.*;

import javax.microedition.rms.*;

public class HiScoreTable {
  
  private static HiScoreTable instance = null;
  private RecordStore rs;
  
  private int numRecords = 0;
  
  /** Creates a new instance of HiScoreTable */
  private HiScoreTable() {    
    try {
      rs = RecordStore.openRecordStore("breakout-scores", true);  
      numRecords = rs.getNumRecords();
      
    } catch (RecordStoreException rse) {
      rse.printStackTrace();
    }
  }
  
  public static HiScoreTable getInstance() {
    return instance == null ? instance = new HiScoreTable() : instance;
  }
  
  public void addHiscore(String username, int score) {  
    try {
      int numRecords = rs.getNumRecords();
      if(numRecords > 7) {
        rs.deleteRecord(findLowestScore());
      }
      HiScoreData hd = new HiScoreData(score, username);
      ByteArrayOutputStream bout = new ByteArrayOutputStream();
      DataOutputStream dout = new DataOutputStream(bout);
      dout.writeInt(score);
      dout.writeUTF(username);
      dout.flush();
      byte[] b = bout.toByteArray();
      int recordId = rs.addRecord(b, 0, b.length);
      dout.close();
      bout.close();
      
    } catch (RecordStoreException e) {
      e.printStackTrace();
      
    } catch (IOException ioe) {
      System.out.println("Caught IOException: ");
      ioe.printStackTrace();
      
    } catch (BreakoutGameException bge) {
      bge.printStackTrace();
    }
  }
  
  private void bubbleSort(HiScoreData[] array) {
    HiScoreData temp;
    for(int i = 0; i < array.length; i++) {
      for(int j = 0; j < array.length-1-i; j++) {
        if(array[j].score < array[j+1].score) {
          temp = array[j];
          array[j] = array[j+1];
          array[j+1] = temp;
        }
      }
    }
  }
  
  private int findLowestScore() throws BreakoutGameException {
    int lowestScoreRecordId = 0;
    try {
      RecordEnumeration re = rs.enumerateRecords(null, null, false);
      int lowestScore = Integer.MAX_VALUE;
            
      while(re.hasNextElement()) {
        int rid = re.nextRecordId();    
        byte[] b = rs.getRecord(rid);
        ByteArrayInputStream bin = new ByteArrayInputStream(b);
        DataInputStream din = new DataInputStream(bin);
        try {
          int score = din.readInt();
          if(score < lowestScore) {
            lowestScoreRecordId = rid;
            lowestScore = score;
          }          
        } catch (IOException ioe) {
          continue;
          
        } finally {
          try {
            din.close();
            bin.close();
          } catch (IOException ioe) {
            //ignore
          }
        }
      }
    } catch (RecordStoreException rse) {
      throw new BreakoutGameException("Error reading scores table");
    }
    return lowestScoreRecordId;
  }
  
  
  public HiScoreData[] getHiScores() {
    HiScoreData[] ret = new HiScoreData[0];

    try {
      RecordEnumeration re = rs.enumerateRecords(null, null, false);
      int numRecords = re.numRecords();
      
      int totalSize = rs.getSize();
      byte[] b= new byte[totalSize];
      
      if(numRecords > 0) {
        int idx = 0;
        while(re.hasNextElement()) {
          byte[] a = re.nextRecord();
          System.arraycopy(a, 0, b, idx*a.length, a.length);
          idx++;
        }
        
        Vector v = new Vector();
        ByteArrayInputStream bin = new ByteArrayInputStream(b);
        DataInputStream din = new DataInputStream(bin);
        
        while(numRecords > 0) {
          int score = din.readInt();
          String username = din.readUTF();
          v.addElement(new HiScoreData(score, username));
          numRecords--;
        }
        din.close();
        bin.close();        
        v.copyInto(ret = new HiScoreData[v.size()]);
      }     
      
    } catch (RecordStoreException th) {
      th.printStackTrace();
      
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
    bubbleSort(ret);
    return ret;    
  }
    
}
