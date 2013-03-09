package shapes;

import java.util.*;
import java.awt.Color;
import java.awt.Graphics2D;

public class Circle extends Shape {
  private double radius;
  private Point center;

  public Circle() {
    super();
    // set default values
    center = new Point(Game.getCanvas().WIDTH / 2, Game.getCanvas().HEIGHT / 2);
    radius = 100;
    setColor(Color.RED);
    setFill(true);
    setSpeechColor(Color.BLACK);
    setup();
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
    } else {
      return false;
    }
  }

  public void render(Graphics2D g) {
    renderSpeech(g);
    if (getInvisible()) {
      return;
    }
    g.setColor(getColor());
    if (getFill()) {
      g.fillOval((int)(center.getCanvasX() - radius),
                 (int)(center.getCanvasY() - radius),
                 (int)(radius * 2),
                 (int)(radius * 2));
    } else {
      g.drawOval((int)(center.getCanvasX() - radius),
                 (int)(center.getCanvasY() - radius),
                 (int)(radius * 2),
                 (int)(radius * 2));
    }
  }

  // Override this!
  public void update() {}

  // Override this!
  public void setup() {}

  public void move(Direction direction, double pixels) {
    if (direction == null || Math.abs(pixels) < Geometry.EPSILON) {
      return;
    }
    Point end = center.translation(new Vector(direction, pixels));
    Point maxMovement = end;
    Set<Shape> solids = Game.getSolids();
    for (Shape solid : solids) {
      Point blockedEnd = Geometry.maxMovement(this, end, (Circle)solid);
      if (Geometry.distance(center, blockedEnd) < Geometry.distance(center, maxMovement)) {
        maxMovement = blockedEnd;
      }
    }
    center = maxMovement;
  }

  public boolean isOffscreen() {
    if (
      center.getX() - radius > Game.getCanvas().WIDTH ||
      center.getX() + radius < 0.0 ||
      center.getY() - radius > Game.getCanvas().HEIGHT ||
      center.getY() + radius < 0.0
    ) {
      return true;
    }

    if (
      center.getX() < Game.getCanvas().WIDTH && center.getX() > 0.0 ||
      center.getY() < Game.getCanvas().HEIGHT && center.getY() > 0.0
    ) {
      return false;
    }

    for (Point corner : Game.getCanvas().getCorners()) {
      if (Geometry.distance(center, corner) < radius) {
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

  public Point getCenter() {
    return center;
  }
  
  public void setCenter(Point center) {
    this.center = center;
  }
}
