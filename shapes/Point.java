package shapes;

public class Point {
  private double x, y;

  public Point(double x, double y) {
    this.x = x;
    this.y = y;
  }

  // doesn't move if direction is null
  public void move(Direction direction, double pixels) {
    if (direction == null) {
      return;
    }
    Vector v = new Vector(direction, pixels);
    x = x + v.getX();
    y = y + v.getY();
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }
  
  public double getCanvasX() {
    return x;
  }

  public double getCanvasY() {
    return Settings.CANVAS_HEIGHT - y;
  }

  public void setX(double x) {
    this.x = x;
  }

  public void setY(double y) {
    this.y = y;
  }
}
