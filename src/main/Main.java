package main;

import javax.swing.JFrame;

public class Main {
  public static void main(String[] args) {
    JFrame window = new JFrame();
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setResizable(true);
    window.setTitle("3d rendering");
    RenderPanel rp = new RenderPanel();
    window.add(rp);
    window.pack();
    window.setLocationRelativeTo(null);
    window.setVisible(true);
  }
}