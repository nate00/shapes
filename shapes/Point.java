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
    move(v);
  }

  public void move(Vector v) {
    x = x + v.getXComponent();
    y = y + v.getYComponent();
  }

  public Point translation(Vector v) {
    Point p = new Point(x, y);
    p.move(v);
    return p;
  }

  public Point translation(Direction direction, double pixels) {
    return translation(new Vector(direction, pixels));
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
    return Game.getCanvas().HEIGHT - y;
  }

  public void setX(double x) {
    this.x = x;
  }

  public void setY(double y) {
    this.y = y;
  }

  public static Point random() {
    double x = Math.random() * Game.getCanvas().WIDTH;
    double y = Math.random() * Game.getCanvas().HEIGHT;
    return new Point(x, y);
  }

  public String toString() {
    return String.format("(%f, %f)", x, y);
  }
}
