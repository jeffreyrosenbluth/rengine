/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.applausecode;

/**
 *
 * @author jeffreyrosenbluth
 */
public class Scale implements Transform {
   private Geometry shape;
   private double x, y, z;
   
   public Scale(Geometry s, double a, double b, double c) {
      this.shape = s;
      this.x = a;
      this.y = b;
      this.z = c;
   }
   
   @Override
   public void execute() {
      shape.scale(x, y, z);
   }
}
