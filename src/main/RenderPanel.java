package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import javax.swing.JPanel;
import math.Triangle;
import math.Matrix;
import math.Quad;
import math.V;
import utility.InputHandler;

public class RenderPanel extends JPanel {
  public double camRX = 0;
  public double camRY = 0;
  public double rotateSensitivity = 0.2;
  public double scrollSensitivity = .02;
  public double panSensitivity = 1;
  public int screenW = 820;
  public int screenH = 580;
  public double dist = (15 * screenH / 580);
  public double scrollDist = 1;
  public double maxDist = 100;
  public double minDist = .01;
  public double focalLength;
  public int maxVerts = 10000;

  public RenderPanel() {
    this.setPreferredSize(new Dimension(screenW, screenH));
    this.setDoubleBuffered(true);
    this.setFocusable(true);
    this.setBackground(new Color(60,60,60));
    loader.load("map");
  }

  public InputHandler input = new InputHandler(this);
  public Loader loader = new Loader(this);
  public ArrayList<Triangle> tris = new ArrayList<>();
  public ArrayList<Quad> quads = new ArrayList<>();

  public void paint(Graphics g) {
    super.paint(g);
    Graphics2D g2 = (Graphics2D) g;

    if (getWidth() < getHeight())
      dist = (15 * getWidth() / 580) * scrollDist;
    else
      dist = (15 * getHeight() / 580) * scrollDist;
    focalLength = 480 * (dist / 15) / scrollDist;

    double yRotate = Math.toRadians(camRY);
    Matrix yRotateTransform = new Matrix(new double[] {
        Math.cos(yRotate), 0, Math.sin(yRotate),
        0, 1, 0,
        -Math.sin(yRotate), 0, Math.cos(yRotate)
    });
    double xRotate = Math.toRadians(camRX);
    Matrix xRotateTransform = new Matrix(new double[] {
        1, 0, 0,
        0, Math.cos(xRotate), -Math.sin(xRotate),
        0, Math.sin(xRotate), Math.cos(xRotate)
    });
    Matrix transform = yRotateTransform.multiply(xRotateTransform);

    g2.translate(getWidth() / 2, getHeight() / 2);
    g2.setColor(new Color(244,166,55));
    for (Triangle t : tris) {
      //points
      V dv1 = new V(0, 0, 0);
      V dv2 = new V(0, 0, 0);
      V dv3 = new V(0, 0, 0);
      // scale
      dv1.x = t.v1.x * dist;
      dv1.y = t.v1.y * dist;
      dv1.z = t.v1.z * dist;
      dv2.x = t.v2.x * dist;
      dv2.y = t.v2.y * dist;
      dv2.z = t.v2.z * dist;
      dv3.x = t.v3.x * dist;
      dv3.y = t.v3.y * dist;
      dv3.z = t.v3.z * dist;
      // rotate
      V v1 = transform.transform(dv1);
      V v2 = transform.transform(dv2);
      V v3 = transform.transform(dv3);
      V pv1 = v1;
      V pv2 = v2;
      V pv3 = v3;
      // add perspective
      pv1.x = ((focalLength * v1.x) / (focalLength - v1.z));
      pv1.y = ((focalLength * v1.y) / (focalLength - v1.z));
      pv2.x = ((focalLength * v2.x) / (focalLength - v2.z));
      pv2.y = ((focalLength * v2.y) / (focalLength - v2.z));
      pv3.x = ((focalLength * v3.x) / (focalLength - v3.z));
      pv3.y = ((focalLength * v3.y) / (focalLength - v3.z));
      // draw triangle
      if ((pv1.z / dist) * scrollDist < 32 && (pv2.z / dist) * scrollDist < 32 && (pv3.z / dist) * scrollDist < 32) {
        Path2D path = new Path2D.Double();
        path.moveTo(pv1.x, -pv1.y);
        path.lineTo(pv2.x, -pv2.y);
        path.lineTo(pv3.x, -pv3.y);
        path.closePath();
        g2.draw(path);
      }
    }
    for (Quad t : quads) {
      //points
      V dv1 = new V(0, 0, 0);
      V dv2 = new V(0, 0, 0);
      V dv3 = new V(0, 0, 0);
      V dv4 = new V(0, 0, 0);
      // scale
      dv1.x = t.v1.x * dist;
      dv1.y = t.v1.y * dist;
      dv1.z = t.v1.z * dist;
      dv2.x = t.v2.x * dist;
      dv2.y = t.v2.y * dist;
      dv2.z = t.v2.z * dist;
      dv3.x = t.v3.x * dist;
      dv3.y = t.v3.y * dist;
      dv3.z = t.v3.z * dist;
      dv4.x = t.v4.x * dist;
      dv4.y = t.v4.y * dist;
      dv4.z = t.v4.z * dist;
      // rotate
      V v1 = transform.transform(dv1);
      V v2 = transform.transform(dv2);
      V v3 = transform.transform(dv3);
      V v4 = transform.transform(dv4);
      V pv1 = v1;
      V pv2 = v2;
      V pv3 = v3;
      V pv4 = v4;
      // add perspective
      pv1.x = ((focalLength * v1.x) / (focalLength - v1.z));
      pv1.y = ((focalLength * v1.y) / (focalLength - v1.z));
      pv2.x = ((focalLength * v2.x) / (focalLength - v2.z));
      pv2.y = ((focalLength * v2.y) / (focalLength - v2.z));
      pv3.x = ((focalLength * v3.x) / (focalLength - v3.z));
      pv3.y = ((focalLength * v3.y) / (focalLength - v3.z));
      pv4.x = ((focalLength * v4.x) / (focalLength - v4.z));
      pv4.y = ((focalLength * v4.y) / (focalLength - v4.z));
      // draw triangle
      if ((pv1.z / dist) * scrollDist < 32 && (pv2.z / dist) * scrollDist < 32 && (pv3.z / dist) * scrollDist < 32 && (pv4.z / dist) * scrollDist < 32) {
        Path2D path = new Path2D.Double();
        path.moveTo(pv1.x, -pv1.y);
        path.lineTo(pv2.x, -pv2.y);
        path.lineTo(pv3.x, -pv3.y);
        path.lineTo(pv4.x, -pv4.y);
        path.closePath();
        g2.draw(path);
      }
    }
    g2.dispose();
  }
}