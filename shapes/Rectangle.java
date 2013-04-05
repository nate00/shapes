package shapes;

import java.awt.*;

/**
 * A rectangle that appears on screen and interacts with other shapes.
 * Many of <code>Rectangles</code>'s useful methods are in its superclasses
 * {@link Shape} and {@link ConvexPolygon}.
 */
public class Rectangle extends ConvexPolygon {
  private double width, height;

  public Rectangle(Point center, double width, double height) {
    setCenter(center);
    setWidth(width);
    setHeight(height);

    // set defaults
    setColor(Color.GREEN);
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

  @Override
  double maxRadius() {
    return Geometry.hypoteneuse(width / 2, height / 2);
  }

  public void setUpperLeft(Point upperLeft) {
    if (upperLeft == null) {
      throw new IllegalArgumentException("upperLeft must not be null.");
    }
    setCenter(upperLeft.getX() + width / 2, upperLeft.getY() - height / 2);
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

  @Override
  public String toString() {
    return "Rectangle at " + getCenter().toString() +
      " with height " + getHeight() + " and width " + getWidth();
  }
}
