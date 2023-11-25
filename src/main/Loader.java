package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import math.Quad;
import math.Triangle;
import math.V;

public class Loader {
  RenderPanel rp;

  public Loader(RenderPanel rp) {
    this.rp = rp;
  }

  public void load(String file) {
    BufferedReader r;
    int num = 1;
    String[] vLine = new String[rp.maxVerts];
    String line;
    try {
      r = new BufferedReader(new FileReader("src/res/" + file + ".obj"));
      line = r.readLine();
      while (line != null) {
        if (line.contains("v ") && !line.contains("#")) {
          vLine[num] = line;
          num++;
        }
        if (line.contains("f ") && !line.contains("#")) {
          String[] fLine = new String[5];
          fLine = line.split("\\s+");
          String[] f1 = fLine[1].split("/");
          String[] f2 = fLine[2].split("/");
          String[] f3 = fLine[3].split("/");
          String[] f4;
          if (fLine.length == 4) {
            String[] v1 = vLine[Integer.parseInt(f1[0])].split("\\s+");
            String[] v2 = vLine[Integer.parseInt(f2[0])].split("\\s+");
            String[] v3 = vLine[Integer.parseInt(f3[0])].split("\\s+");
            rp.tris.add(new Triangle(new V(Double.parseDouble(v1[1]),Double.parseDouble(v1[2]),Double.parseDouble(v1[3])),new V(Double.parseDouble(v2[1]),Double.parseDouble(v2[2]),Double.parseDouble(v2[3])),new V(Double.parseDouble(v3[1]),Double.parseDouble(v3[2]),Double.parseDouble(v3[3]))));
          } else {
            f4 = fLine[4].split("/");
            String[] v1 = vLine[Integer.parseInt(f1[0])].split("\\s+");
            String[] v2 = vLine[Integer.parseInt(f2[0])].split("\\s+");
            String[] v3 = vLine[Integer.parseInt(f3[0])].split("\\s+");
            String[] v4 = vLine[Integer.parseInt(f4[0])].split("\\s+");

            rp.quads.add(new Quad(new V(Double.parseDouble(v1[1]),Double.parseDouble(v1[2]),Double.parseDouble(v1[3])),new V(Double.parseDouble(v2[1]),Double.parseDouble(v2[2]),Double.parseDouble(v2[3])),new V(Double.parseDouble(v3[1]),Double.parseDouble(v3[2]),Double.parseDouble(v3[3])),new V(Double.parseDouble(v4[1]),Double.parseDouble(v4[2]),Double.parseDouble(v4[3]))));
          }
        }
        line = r.readLine();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
