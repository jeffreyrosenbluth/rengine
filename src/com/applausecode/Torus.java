/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.applausecode;

/**
 *
 * @author jeffreyrosenbluth
 */
public class Torus extends Geometry {
   
   public Torus(int m, int n, double r) {
      vertices = new double[(m+1) * (n+1)][4];
      faces = new int[m * n][4];
      double[][] uvPlane = new double[(m+1) * (n+1)][2];
      for (int i = 0; i < (m+1) * (n+1); i++) {
            uvPlane[i] = new double[]{i2u(i,m,n), i2v(i,m,n)};
            vertices[i] = toTorus(uvPlane[i][0],uvPlane[i][1], r);
      }
      for (int i = 0; i < m; i++) {
         for (int j = 0; j < n; j++){
            faces[i + m * j] = faceIndices(i, j, m);
         }
      }
   }
   
   private static double[] toTorus(double u, double v, double r) {
      double[] xyz = new double[3];
      xyz[0] = Math.cos(2 * Math.PI * u) * (1 + r * Math.cos(2 * Math.PI * v));
      xyz[1] = Math.sin(2 * Math.PI * u) * (1 + r * Math.cos(2 * Math.PI * v));
      xyz[2] = r * Math.sin(2 * Math.PI * v);
      return xyz;
   }
   
}
