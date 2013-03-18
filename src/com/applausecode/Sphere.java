/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.applausecode;

/**
 *
 * @author jeffreyrosenbluth
 */
public class Sphere extends Geometry {

   public Sphere(int m, int n) {
      vertices = new double[(m+1) * (n+1)][4];
      faces = new int[m * n][4];
      double[][] uvPlane = new double[(m+1) * (n+1)][2];
      for (int i = 0; i < (m+1) * (n+1); i++) {
            uvPlane[i] = new double[]{i2u(i,m,n), i2v(i,m,n)};
            vertices[i] = toSphere(uvPlane[i][0],uvPlane[i][1]);
      }
      for (int i = 0; i < m; i++) {
         for (int j = 0; j < n; j++){
            faces[i + m * j] = faceIndices(i, j, m);
         }
      }
   }

   private static double[] toSphere(double u, double v) {
      double[] xyz = new double[3];
      xyz[0] = Math.cos(2 * Math.PI * u) * Math.cos(Math.PI * (v - 0.5));
      xyz[1] = Math.sin(2 * Math.PI * u) * Math.cos(Math.PI * (v - 0.5));
      xyz[2] = Math.sin(Math.PI * (v - 0.5));
      return xyz;
   }
}