/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.applausecode;

/**
 *
 * @author jeffreyrosenbluth
 */
public class Translate implements Transform {
   private Geometry shape;
   private double x, y, z;
   
   public Translate(Geometry s, double a, double b, double c) {
      this.shape = s;
      this.x = a;
      this.y = b;
      this.z = c;
   }
   
   @Override
   public void execute() {
      shape.translate(x, y, z);
   }
}
