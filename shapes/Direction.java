package shapes;

public class Direction {
  double degrees;

  public static Direction RIGHT = new Direction(0.0);
  public static Direction LEFT = new Direction(180.0);
  public static Direction DOWN = new Direction(270.0);
  public static Direction UP = new Direction(90.0);

  public Direction(double degrees) {
    setDegrees(degrees);
  }

  public double getRadians() {
    return degrees / 180.0 * Math.PI;
  }

  public void setRadians(double radians) {
    double degrees = radians / Math.PI * 180.0;
    setDegrees(degrees);
  }

  public double getDegrees() {
    return degrees;
  }

  public void setDegrees(double degrees) {
    degrees %= 360.0;
    if (degrees < 0.0) {
      degrees += 360.0;
    }
    this.degrees = degrees;
  }

  public String toString() {
    return degrees + " degrees";
  }
}
