package shapes;

import java.awt.*;

/**
 * An isosceles triangle that appears on screen and interacts with other
 * shapes. Many of <code>Triangle</code>'s useful methods are in its
 * superclasses {@link Shape} and {@link ConvexPolygon}.
 * <p>
 */
public class Triangle extends ConvexPolygon {
  private double size;

  public Triangle(Point center, double size) {
    super();
    this.center = center;
    this.size = size;
    setColor(Color.YELLOW);
    setup();
  }

  public Triangle() {
    this(new Point(20, 400), 10);
  }

  // Override this!
  public void setup() {}

  // Override this!
  public void update() {}

  // Contract: zeroth corner is the tip
  Point[] getUnrotatedCorners() {
    return new Point[] {
      getCenter().translation(new Vector(size, 0)),
      getCenter().translation(new Vector(-size, size)),
      getCenter().translation(new Vector(-size, -size))
    };
  }

  public Point getTip() {
    return getCorners()[0];
  }

  /**
   * Set this triangle's size. The length of the triangle's side opposite the
   * tip is equal to <code>size</code>, and the length of the altitude to that
   * size is <code>size * 2</code>.
   *
   * @param size  the new size of the triangle.
   */
  public void setSize(double size) {
    this.size = size;
  }

  public double getSize() {
    return size;
  }
}
