package utility;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import main.RenderPanel;

public class InputHandler {
  RenderPanel rp;
  int prevX;
  int prevY;
  boolean shift = false;

  public InputHandler(RenderPanel rp) {
    this.rp = rp;
    rp.addMouseListener(new MouseListener() {
      public void mouseClicked(MouseEvent e) {
      }

      public void mouseEntered(MouseEvent e) {
      }

      public void mouseExited(MouseEvent e) {
      }

      public void mouseReleased(MouseEvent e) {
      }

      public void mousePressed(MouseEvent e) {
        prevX = e.getX();
        prevY = e.getY();
      }
    });
    rp.addMouseMotionListener(new MouseMotionListener() {
      public void mouseMoved(MouseEvent e) {
      }

      public void mouseDragged(MouseEvent e) {
        if (!shift) {
          rp.camRY -= ((e.getX() - prevX) * rp.rotateSensitivity);
          rp.camRX -= ((e.getY() - prevY) * rp.rotateSensitivity);
        }
        if (shift) {
          
        }
        prevX = e.getX();
        prevY = e.getY();
        rp.repaint();
      }
    });
    rp.addMouseWheelListener(new MouseWheelListener() {
      public void mouseWheelMoved(MouseWheelEvent e) {
        rp.scrollDist = Math.max(rp.minDist,
            Math.min(rp.scrollDist - e.getWheelRotation() * rp.scrollSensitivity, rp.maxDist));
        rp.repaint();
      }
    });
    rp.addKeyListener(new KeyListener() {
      public void keyTyped(KeyEvent e) {
      }

      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 16)
          shift = true;
      }

      public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == 16)
          shift = false;
      }
    });
  }
}