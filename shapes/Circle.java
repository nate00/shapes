package shapes;

import java.util.*;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * A circle that appears on screen and interacts with other shapes.
 * Many of <code>Circle</code>'s useful methods are in its superclass
 * {@link Shape}.
 */
public class Circle extends Shape {
  private double radius;

  public Circle(Point center, double radius) {
    super();
    setDefaults();
    setCenter(center);
    setRadius(radius);
  }

  public Circle() {
    super();
    setDefaults();
  }

  // call for a non-displaying circle (for geometrical calculations)
  Circle(Point center, double radius, boolean display) {
    System.out.println("No display!");
    setCenter(center);
    setRadius(radius);
  }

  private void setDefaults() {
    setCenter(new Point(200, 400));
    setRadius(10);
    setColor(Color.PINK);
    setFilled(true);
    setSpeechStyle(TextStyle.sansSerif());
  }

  public boolean contains(Point p) {
    return Geometry.distance(getCenter(), p) < getRadius();
  }

  public boolean contains(Shape s) {
    // TODO: move disambiguation into Geometry, make Shape.contains(Shape) non-abstract, remove this code.
    if (this.isDestroyed() || s.isDestroyed()) {
      return false;
    }
    if (s instanceof Circle) {
      Circle c = (Circle)s;
      double distance = Geometry.distance(c.getCenter(), this.getCenter());
      return distance + c.getRadius() < this.getRadius();
    } else if (s instanceof ConvexPolygon) {
      ConvexPolygon poly = (ConvexPolygon) s;
      for (Point corner : poly.getCorners()) {
        if (!this.contains(corner)) {
          return false;
        }
      }
      return true;
    } else {
      return false;
    }
  }

  void render(Graphics2D g) {
    renderSpeech(g);
    if (isInvisible()) {
      return;
    }
    g.setColor(getColor());
    if (isFilled()) {
      g.fillOval((int)(getCenter().getCanvasX() - radius),
                 (int)(getCenter().getCanvasY() - radius),
                 (int)(radius * 2),
                 (int)(radius * 2));
    } else {
      g.drawOval((int)(getCenter().getCanvasX() - radius),
                 (int)(getCenter().getCanvasY() - radius),
                 (int)(radius * 2),
                 (int)(radius * 2));
    }
  }

  Point maxMovement(Point target, Segment obstacle) {
    Segment path = new Segment(getCenter(), target);
    Point maxMove = Geometry.maxMovement(this, target, obstacle);
    maxMove = Geometry.insertGap(this, path, maxMove);
    return maxMove;
  }

  Point maxMovement(Point target, Shape obstacle) {
    Segment path = new Segment(getCenter(), target);
    Point maxMove = Geometry.maxMovement(this, target, obstacle);
    maxMove = Geometry.insertGap(this, path, maxMove);
    return maxMove;
  }

  // Override this!
  public void update() {}

  // Override this!
  public void setup() {}

  public boolean isOffscreen() {
    if (
      getCenter().getX() - radius > Game.WIDTH ||
      getCenter().getX() + radius < 0.0 ||
      getCenter().getY() - radius > Game.HEIGHT ||
      getCenter().getY() + radius < 0.0
    ) {
      return true;
    }

    if (
      getCenter().getX() < Game.WIDTH && getCenter().getX() > 0.0 ||
      getCenter().getY() < Game.HEIGHT && getCenter().getY() > 0.0
    ) {
      return false;
    }

    for (Point corner : Game.getCorners()) {
      if (Geometry.distance(getCenter(), corner) < radius) {
        return false;
      }
    }
    return true;
  }

  public double getRight() {
    return getCenter().getX() + getRadius();
  }
  public double getTop() {
    return getCenter().getY() + getRadius();
  }
  public double getLeft() {
    return getCenter().getX() - getRadius();
  }
  public double getBottom() {
    return getCenter().getY() - getRadius();
  }

  public double getRadius() {
    return radius;
  }

  public void setRadius(double radius) {
    if (radius <= 0) {
      throw new IllegalArgumentException("Radius must be positive.");
    }
    this.radius = radius;
  }

  public String toString() {
    return
      "Circle at " + getCenter().toString() +
      " with radius " + getRadius();
  }
}
