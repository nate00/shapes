package shapes;

/**
 * Represents a direction in the 2D plane. Directions can be used to control
 * which way shapes face and which way they move.
 * <p>
 * Directions are represented in degrees (or radians) starting with 0 degrees
 * pointing to the right, and increasing counterclockwise (like in a unit
 * circle). For example, up is 90 degrees and left is 180 degrees.
 */
public class Direction {
  private double degrees;

  public static Direction RIGHT = new Direction(0.0);
  public static Direction LEFT = new Direction(180.0);
  public static Direction DOWN = new Direction(270.0);
  public static Direction UP = new Direction(90.0);

  public Direction(double degrees) {
    setDegrees(degrees);
  }

  public Direction(Point origin, Point terminus) {
    if (origin == null) {
      throw new IllegalArgumentException("origin must not be null");
    }
    if (terminus == null) {
      throw new IllegalArgumentException("terminus must not be null");
    }
    double deltaX = terminus.getX() - origin.getX();
    double deltaY = terminus.getY() - origin.getY();
    double radians = Math.atan2(deltaY, deltaX);
    setRadians(radians);
  }

  public static Direction inRadians(double radians) {
    return new Direction(radians / Math.PI * 180.0);
  }

  public static Direction inDegrees(double degrees) {
    return new Direction(degrees);
  }

  public double toRadians() {
    return degrees / 180.0 * Math.PI;
  }

  public double toDegrees() {
    return degrees;
  }

  private void setDegrees(double degrees) {
    degrees %= 360.0;
    if (degrees < 0.0) {
      degrees += 360.0;
    }
    this.degrees = degrees;
  }

  private void setRadians(double radians) {
    double degrees = radians / Math.PI * 180.0;
    setDegrees(degrees);
  }

  public Direction reverse() {
    return new Direction(degrees + 180.0);
  }

  public Direction perpendicular() {
    return new Direction(degrees + 90.0);
  }

  public Direction rotationByRadians(double radians) {
    return Direction.inRadians(toRadians() + radians);
  }

  public Direction rotationByDegrees(double degrees) {
    return new Direction(this.degrees + degrees);
  }

  public Direction rotation(double degrees) {
    return rotationByDegrees(degrees);
  }

  public static Direction random() {
    return new Direction(Math.random() * 360.0);
  }

  public boolean isRightward() {
    return isWithinQuarterTurnOf(RIGHT);
  }

  public boolean isUpward() {
    return isWithinQuarterTurnOf(UP);
  }

  public boolean isLeftward() {
    return isWithinQuarterTurnOf(LEFT);
  }

  public boolean isDownward() {
    return isWithinQuarterTurnOf(DOWN);
  }

  boolean isWithinQuarterTurnOf(Direction dir) {
    return
      Math.abs(dir.toDegrees() - degrees) < 90.0 ||
      Math.abs(dir.toDegrees() - degrees - 360.0) < 90.0 ||
      Math.abs(dir.toDegrees() - degrees + 360.0) < 90.0;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Direction) {
      return equals((Direction) obj);
    }

    return false;
  }

  private boolean equals(Direction that) {
    return Geometry.equals(this.toDegrees(), that.toDegrees());
  }

  @Override
  public String toString() {
    return degrees + " degrees";
  }
}
