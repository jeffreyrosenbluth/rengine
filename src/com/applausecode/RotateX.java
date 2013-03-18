/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.applausecode;

/**
 *
 * @author jeffreyrosenbluth
 */
public class RotateX implements Transform {
   private Geometry shape;
   private double angle;
   
   public RotateX(Geometry s, double theta) {
      this.shape = s;
      this.angle = theta;
   }
   
   @Override
   public void execute() {
      shape.rotateX(angle);
   }
}
