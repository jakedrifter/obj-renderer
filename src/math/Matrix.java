package math;

public class Matrix {
  public double[] values;

  public Matrix(double[] values) {
    this.values = values;
  }

  public Matrix multiply(Matrix matrix) {
    double[] result = new double[9];
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 3; col++) {
        for (int currentIndex = 0; currentIndex < 3; currentIndex++) {
          result[row * 3 + col] += this.values[row * 3 + currentIndex] * matrix.values[currentIndex * 3 + col];

        }
      }
    }
    return new Matrix(result);
  }

  public V transform(V vertex) {
    return new V(
        vertex.x * values[0] + vertex.y * values[3] + vertex.z * values[6],
        vertex.x * values[1] + vertex.y * values[4] + vertex.z * values[7],
        vertex.x * values[2] + vertex.y * values[5] + vertex.z * values[8]);
  }
}
