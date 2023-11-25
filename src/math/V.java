package math;

public class V {
  public double x;
  public double y;
  public double z;
  double vm = 1.5;

  public V(double x, double y, double z) {
    this.x = x * vm;
    this.y = y * vm;
    this.z = z * vm;
  }
}
