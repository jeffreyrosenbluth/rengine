
/**
 *
 * @author jeffreyrosenbluth
 */
package com.applausecode;
import java.awt.*;
import java.util.List;

public class RengineApplet extends BufferedApplet
{
   private Geometry world;
   
   Torus torus = new Torus(16, 16, 0.4);
   Cylinder cylinder = new Cylinder(16,16);
   HalfSphere bowl = new HalfSphere(24, 24);
   Sphere apple = new Sphere(16,16);
   int width = 0, height = 0;
   double startTime = System.currentTimeMillis() / 1000.0;
   double t = 0;
   double pointA[] = {0,0,0};
   double pointB[] = {0,0,0};
   int a[] = {0,0};
   int b[] = {0,0};
   
   public RengineApplet() {
      world = new Geometry();
      world.addChild(bowl);
      bowl.addChild(apple);
//      cylinder.addChild(torus);
//      cylinder.addChild(sphere);
   }
   
   private void makeBowl() {
      bowl.color = Color.gray;
      bowl.transforms.clear();
      bowl.transforms.add(new Identity(bowl));
      bowl.transforms.add(new Scale(bowl, 0.5, 0.5, 0.5));
      bowl.transforms.add(new RotateX(bowl, 0.1-Math.PI / 2));
      bowl.transforms.add(new Translate(bowl, 0, 0, Math.min(-.2, -1/t)));
      bowl.transforms.add(new Translate(bowl, 0, -.25, 0));
   }
   
   private void makeApple() {
      apple.color = Color.red;
      apple.transforms.add(new Identity(apple));
      apple.transforms.add(new Scale(apple, .1,.1,.1));
      if (t > 2) {
         apple.transforms.add(new RotateX(apple, -t));
         apple.transforms.add(new Translate(apple, 1.5 * Math.max(-1.5, Math.exp(1/t)),0,0));
      }
      apple.transforms.add(new Translate(apple,-1.5,-2,0));
   }
   
   private void drawScene(Geometry shape, Graphics g) {
      int n = shape.getNumChildren();
      if (n == 0) {return;}
      makeBowl();
      makeApple();
      for (int i = 0; i < n; i++) {
         Geometry s = shape.getChild(i);
         s.setGlobalMatrix(shape.getGlobalMatrix());
         drawGeometry(s, s.transforms, g);
         drawScene(s, g);
      }
   }
   
   private void drawGeometry(Geometry shape, List<Transform> tList, Graphics g) {
      g.setColor(shape.color);
      for (Transform cmd : tList) {cmd.execute();}
      int[][] faces = shape.getFaces();
      for (int f = 0; f < faces.length ; f++) {
         for (int e = 0; e < faces[f].length; e++) {
            int e2 = (e + 1) % faces[f].length;
            int i = faces[f][e];
            int j = faces[f][e2];
            viewport(shape.getVertices()[i],a);
            viewport(shape.getVertices()[j],b);
            g.drawLine(a[0], a[1], b[0], b[1]);
         }
      }
   }
   
   @Override
   public void render(Graphics g) {
      setSize(960,640);
      width = getWidth();
      height = getHeight();
      t = System.currentTimeMillis() / 1000.0 - startTime;
      g.setColor(Color.black);
      g.fillRect(0, 0, width, height);
      drawScene(world, g);
   }

   public void viewport(double src[], int dst[]) {
      final double focalLength = 2;
      dst[0] = width / 2 + (int)(height * src[0] / (focalLength - src[2]));
      dst[1] = height / 2 - (int)(height * src[1] / (focalLength - src[2]));
   }
}
