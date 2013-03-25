package shapes;

import java.util.*;
import java.awt.Color;
import java.awt.Graphics2D;

public class Circle extends Shape {
  private double radius;

  public Circle(Point center, double radius) {
    super();
    setDefaults();
    setCenter(center);
    setRadius(radius);
    setup();
  }

  public Circle() {
    super();
    setDefaults();
    setup();
  }

  private void setDefaults() {
    setCenter(new Point(200, 400));
    setRadius(10);
    setColor(Color.PINK);
    setFilled(true);
    setSpeechColor(Color.BLACK);
  }

  public void renderSpeech(Graphics2D g) {
    if (!isSpeaking()) {
      return;
    }
    g.setColor(getSpeechColor());
    Point upperLeft =
      getCenter().translation(new Vector(getRadius(), getRadius()));
    Vector heightOffset =
      new Vector(0.0, -1.0 * g.getFontMetrics(g.getFont()).getDescent());

    Point lowerLeft = upperLeft.translation(heightOffset);
    g.drawString(
      getSpeech(),
      (int)lowerLeft.getCanvasX(),
      (int)lowerLeft.getCanvasY()
    );
  }

  public boolean contains(Point p) {
    return Geometry.distance(getCenter(), p) < getRadius();
  }

  public boolean contains(Shape s) {
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

  public void render(Graphics2D g) {
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

  public Point maxMovement(Point target, Shape obstacle) {
    return Geometry.maxMovement(this, target, obstacle);
  }

  // Override this!
  public void update() {}

  // Override this!
  public void setup() {}

  public boolean isOffscreen() {
    if (
      getCenter().getX() - radius > Game.getCanvas().WIDTH ||
      getCenter().getX() + radius < 0.0 ||
      getCenter().getY() - radius > Game.getCanvas().HEIGHT ||
      getCenter().getY() + radius < 0.0
    ) {
      return true;
    }

    if (
      getCenter().getX() < Game.getCanvas().WIDTH && getCenter().getX() > 0.0 ||
      getCenter().getY() < Game.getCanvas().HEIGHT && getCenter().getY() > 0.0
    ) {
      return false;
    }

    for (Point corner : Game.getCanvas().getCorners()) {
      if (Geometry.distance(getCenter(), corner) < radius) {
        return false;
      }
    }
    return true;
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
}
