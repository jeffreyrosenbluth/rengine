/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.applausecode;

/**
 *
 * @author jeffreyrosenbluth
 */
public class Identity implements Transform {
   private Geometry shape;
   
   public Identity(Geometry s) {
      this.shape = s;
   }
   
   @Override
   public void execute() {
      shape.identity();
   }
   
}
