/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.applausecode;
import java.awt.*;

/**
 *
 * @author jeffreyrosenbluth
 */

public abstract class BufferedApplet extends java.applet.Applet implements Runnable
{
   // YOU MUST DEFINE A METHOD TO RENDER THE APPLET

   public abstract void render(Graphics g);

   // A BACKGROUND THREAD CALLS REPAINT EVERY 30 MILLISECONDS,

   @Override
   public void start() { 
      if (t == null) {
         (t = new Thread(this)).start();
      } 
   }
   @Override
   @SuppressWarnings("empty-statement")
   public void run() { 
      try { 
         while (true) { 
            repaint(); 
            t.sleep(30); 
         } 
      }
      catch(Exception e){}; 
   }

   // WHICH CALLS UPDATE, WHICH CALLS YOUR RENDER METHOD.
   // THE IMAGE YOU'VE RENDERED IS THEN COPIED TO THE SCREEN.

   @Override
   public void update(Graphics g) {
      if (width != getWidth() || height != getHeight()) {
         image = createImage(width = getWidth(), height = getHeight());
         buffer = image.getGraphics();
      }
      render(buffer);
      g.drawImage(image,0,0,this);
   }

   private Thread t;
   private Image image;
   private Graphics buffer;
   private int width = 0, height = 0; 
}
