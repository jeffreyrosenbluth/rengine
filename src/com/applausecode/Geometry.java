
package com.applausecode;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author jeffreyrosenbluth
 * public double[][] getVertices();          // return transformed vertices
 * public int[][] getFaces();
 * public void identity();
 * public void translate(double a, double b, double c);
 * public void rotateX(double theta);
 * public void rotateY(double theta);
 * public void rotateZ(double theta);
 * public void scale(double x, double y, double z);
 * 
 * public void add(Geometry child);          // add a child
 * public Geometry getChild(int i);          // get child i
 * public int getNumChildren();              // number of children
 * public boolean remove(Geometry child);    // remove a child
 */

public class Geometry {
   
   protected double vertices[][];
   protected int faces[][];
   private double matrix[][];
   private double globalMatrix[][];
   private List<Geometry> children;
   public List<Transform> transforms;
   public Color color;
   
   public Geometry() {
      matrix = new double[4][4];
      globalMatrix = new double[4][4];
      children = new ArrayList();
      matrix = identityMatrix();
      globalMatrix = identityMatrix();
      transforms = new LinkedList();
      color = Color.gray;
   }
   
   public double[][] getVertices() {
      double[][] dst = new double[vertices.length][vertices[0].length];
      for (int i = 0; i < vertices.length ; i++) {
         transform(vertices[i], dst[i]);
      }
      return dst;
   }
   
   public int[][] getFaces() {
      return faces;
   }
   
   public double[][] getGlobalMatrix() {
      return globalMatrix;
   }
   
   public void setGlobalMatrix(double[][] g) {
      globalMatrix = multiply(g, matrix);
   }
      
   public int getNumChildren() {
      return children.size();
   }
   
   public Geometry getChild(int i) {
      return children.get(i);
   }
   
   public boolean addChild(Geometry child) {
      return children.add(child);
   }
   
   public boolean removeChild(Geometry child) {
      return children.remove(child);
   }
   
   public static double i2v(int i, int n, int m) {
      return (i / (n+1)) / (double) m;
   }
   
   public static double i2u(int i, int n, int m) {
      return (i % (n+1)) / (double) n;
   }
   
   private static int vertIdx(int m, int n, int meshSize) {
      return m + (meshSize + 1) * n;
   }
   
   public static int[] faceIndices(int i, int j, int m) {
      int[] face = new int[4];
      face[0] = vertIdx(i,j,m);
      face[1] = vertIdx(i+1,j,m);
      face[2] = vertIdx(i+1,j+1,m);
      face[3] = vertIdx(i,j+1,m);
      return face;
   }
   
 // -------------- Matrix algebra methods --------------------------------------
   
   private static double[][] multiply(double[][] L, double[][] R) {
      double product[][] = new double[4][4];
      for (int i = 0; i < 4; i++) {
         for (int j = 0; j < 4; j++) {
            for (int k = 0; k < 4; k++) {
               product[i][j] += L[i][k] * R[k][j];
            }
         }
      }
      return product;
   }
   
   public void identity() {
      for (int i=0; i<4; i++) {
         for (int j=0; j<4; j++) {
            if (i == j) {matrix[i][j] = 1.0;}
            else {matrix[i][j] = 0.0;}
         }
      }
   }
   
   private static double[][] identityMatrix() {
      double[][] m = new double[4][4];
      for (int i=0; i<4; i++) {
         for (int j=0; j<4; j++) {
            if (i == j) {m[i][j] = 1.0;}
            else {m[i][j] = 0.0;}
         }
      }
      return m;
   }
   
   public void translate(double a, double b, double c) {
      double tData[][] = new double[4][4];
      for (int i=0; i<4; i++) {tData[i][i] = 1.0;}
      tData[0][3] = a;
      tData[1][3] = b;
      tData[2][3] = c;
      matrix = multiply(tData, matrix);
   }
   
   public void rotateX(double theta) {
      double tData[][] = new double[4][4];
      for (int i=0; i<4; i++) {tData[i][i] = 1.0;}
      tData[1][1] =   Math.cos(theta);
      tData[1][2] = - Math.sin(theta);
      tData[2][1] =   Math.sin(theta);
      tData[2][2] =   Math.cos(theta);
      matrix = multiply(tData, matrix);
   }
   
   public void rotateY(double theta) {
      double tData[][] = new double[4][4];
      for (int i=0; i<4; i++) {tData[i][i] = 1.0;}
      tData[0][0] =   Math.cos(theta);
      tData[0][2] =   Math.sin(theta);
      tData[2][0] =  -Math.sin(theta);
      tData[2][2] =   Math.cos(theta);
      matrix = multiply(tData, matrix);
   }
   
   public void rotateZ(double theta) {
      double tData[][] = new double[4][4];
      for (int i=0; i<4; i++) {tData[i][i] = 1.0;}
      tData[0][0] =   Math.cos(theta);
      tData[0][1] =  -Math.sin(theta);
      tData[1][0] =   Math.sin(theta);
      tData[1][1] =   Math.cos(theta);
      matrix = multiply(tData, matrix);
   }
   
   public void scale(double a, double b, double c) {
      double tData[][] = new double[4][4];
      for (int i=0; i<4; i++) {tData[i][i] = 1.0;}
      tData[0][0] = a;
      tData[1][1] = b;
      tData[2][2] = c;
      matrix = multiply(tData, matrix);
   }
   
   private void transform(double src[], double dst[]) {
      double s[] = new double[4];
      System.arraycopy(src, 0, s, 0, 3);
      s[3] = 1;
      for (int i = 0; i < dst.length; i++) {
         dst[i] = 0;
         for (int j = 0; j < s.length; j++) {
            dst[i] += globalMatrix[i][j] * s[j];
         }
      }
   }
   
   @Override
   public String toString() {
      StringBuilder s = new StringBuilder();
      for (int i = 0; i < 4; i++) {
         for (int j = 0; j < 4; j++) {
            s.append(String.format("%5.2f ", globalMatrix[i][j]));
         }
         s.append("\n");
      }
      return s.toString();
   }
   
 // ----------------------------------------------------------------------------
   public static void main(String[] args) {
      Geometry g = new Geometry();
      g.identity();
      g.scale(2, 3, 4);
      System.out.println(g);
   }
}
