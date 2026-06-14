
/*
 * Stack.java
 * Implements a simple stack
 *
 * Created on 12 August 2007, 19:35
 */
package org.happysoft.games;

import java.util.Vector;

/**
 * @author Chris Francis
 */
public class Stack {
  
  private Vector v = new Vector();
  
  public Stack() {
  }
  
  public void push(Object ob) {
    v.addElement(ob);
  }
  
  public Object pop() {    
    Object o = null;
    try {
      o = v.lastElement();
      v.removeElement(o);
    } catch (Exception e) {
      // ignore if stack empty 
    }
    return o;
  }
  
  public boolean isEmpty() {
    return v.isEmpty();
  }
  
}
