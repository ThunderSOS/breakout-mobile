
package org.happysoft.games;

/*
 * CSVEnumeration.java
 * 
 * Created on 15 August 2007, 17:34
 * @author Chris Francis
 */
public class CSVEnumeration { 
  
  private int counter = 0;
  private String s = null;
  
  /** Creates a new instance of CSVEnumeration */
  public CSVEnumeration(String line) {
    s = line;    
  }
     
  public String nextElement() {
    int comma = s.indexOf(",", counter);
    if(counter >= s.length()) {
      return null;
    }
    
    String ret = null;
    try {
      if(comma >= 0) {
        ret = s.substring(counter, comma);
        
      } else {
        ret = s.substring(counter+1);
      }
      
    } catch (StringIndexOutOfBoundsException ex) {
      System.out.println("Out of bounds: counter = " + counter + ", comma = " + comma);
    }
    if(comma >= 0) {
      counter = comma;
    }
    counter++;
    return ret != null ? ret.trim() : null;
  }

}
