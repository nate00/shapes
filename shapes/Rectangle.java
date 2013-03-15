package shapes;

public class Rectangle extends ConvexPolygon {
  private double width, height;

  public Rectangle(Point center, double width, double height) {
    this.center = center;
    this.width = width;
    this.height = height;
  }

  public Rectangle() {
    this(new Point(100, 100), 50, 100);
  }

  // Override this!
  public void setup() {}

  // Override this!
  public void update() {}

  Point[] getUnrotatedCorners() {
    return new Point[] {
      getCenter().translation(new Vector(-1 * width / 2, height / 2)),
      getCenter().translation(new Vector(width / 2,      height / 2)),
      getCenter().translation(new Vector(width / 2,      -1 * height / 2)),
      getCenter().translation(new Vector(-1 * width / 2, -1 * height / 2))
    };
  }

  public double getHeight() {
    return height;
  }

  public void setHeight(double height) {
    this.height = height;
  }

  public double getWidth() {
    return width;
  }

  public void setWidth(double width) {
    this.width = width;
  }
}
