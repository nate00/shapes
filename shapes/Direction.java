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

  public static Direction inRadians(double radians) {
    return new Direction(radians / Math.PI * 180.0);
  }

  public static Direction inDegrees(double degrees) {
    return new Direction(degrees);
  }

  public double getRadians() {
    return degrees / 180.0 * Math.PI;
  }

  public double getDegrees() {
    return degrees;
  }

  private void setDegrees(double degrees) {
    degrees %= 360.0;
    if (degrees < 0.0) {
      degrees += 360.0;
    }
    this.degrees = degrees;
  }

  public Direction reverse() {
    return new Direction(degrees + 180.0);
  }

  public Direction perpendicular() {
    return new Direction(degrees + 90.0);
  }

  public Direction rotationByRadians(double radians) {
    return Direction.inRadians(getRadians() + radians);
  }

  public Direction rotationByDegrees(double degrees) {
    return new Direction(this.degrees + degrees);
  }

  public static Direction random() {
    return new Direction(Math.random() * 360.0);
  }

  public String toString() {
    return degrees + " degrees";
  }
}
