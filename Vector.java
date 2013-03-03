public class Vector {
  private Direction direction;
  private double magnitude;

  public static Vector UP = new Vector(Direction.UP, 1.0);
  public static Vector DOWN = new Vector(Direction.DOWN, 1.0);
  public static Vector LEFT = new Vector(Direction.LEFT, 1.0);
  public static Vector RIGHT = new Vector(Direction.RIGHT, 1.0);

  public Vector(Direction direction, double magnitude) {
    this.direction = direction;
    this.magnitude = magnitude;
  }

  public Vector(double x, double y) {
    direction = new Direction(0.0); // must be initialized to call setComponents
    setComponents(x, y);
  }

  public Vector add(Vector that) {
    return new Vector(this.getX() + that.getX(), this.getY() + that.getY());
  }

  public void setComponents(double x, double y) {
    direction.setRadians(Math.atan2(y, x));
    magnitude = Math.sqrt(x * x + y * y);
  }

  public double getX() {
    return magnitude * Math.cos(direction.getRadians());
  }

  public double getY() {
    return magnitude * Math.sin(direction.getRadians());
  }

  public Direction getDirection() {
    return direction;
  }

  public void setDirection(Direction direction) {
    this.direction = direction;
  }

  public double getMagnitude() {
    return magnitude;
  }

  public void setMagnitude(double magnitude) {
    this.magnitude = magnitude;
  }
}
