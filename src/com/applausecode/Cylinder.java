/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.applausecode;

/**
 *
 * @author jeffreyrosenbluth
 */
public class Cylinder extends Geometry{
   
   public Cylinder(int m, int n) {
      vertices = new double[(m+1) * (n+1)][4];
      faces = new int[m * n][4];
      double[][] uvPlane = new double[(m+1) * (n+1)][2];
      for (int i = 0; i < (m+1) * (n+1); i++) {
            uvPlane[i] = new double[]{i2u(i,m,n), i2v(i,m,n)};
            vertices[i] = toCylinder(uvPlane[i][0],uvPlane[i][1]);
      }
      for (int i = 0; i < m; i++) {
         for (int j = 0; j < n; j++){
            faces[i + m * j] = faceIndices(i, j, m);
         }
      }
   }
   
   private static double[] toCylinder(double u, double v) {
      double[] xyz = new double[3];
      double rv = (v == 0 || v == 1) ? 0 : 1;
      xyz[0] = Math.cos(2 * Math.PI * u) * rv;
      xyz[1] = Math.sin(2 * Math.PI * u) * rv;
      xyz[2] = (v < 0.5) ? -1 : 1;
      return xyz;
   }
   
}