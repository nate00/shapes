package shapes;

import java.util.*;
import java.awt.Color;
import java.awt.Graphics2D;

public class Circle extends Shape {
  private double radius;
  private Point center;

  public Circle() {
    // set default values
    center = new Point(100, Settings.CANVAS_HEIGHT - 100);
    radius = 100;
    setColor(Color.RED);
    setFill(false);
    setSpeechColor(Color.BLACK);
    init();
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
  public void init() {}

  public void move(Direction direction, double pixels) {
    if (direction == null) return;
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

  public double getRadius() {
    return radius;
  }

  public void setRadius(double radius) {
    this.radius = radius;
  }

  public Point getCenter() {
    return center;
  }
  
  public void setCenter(Point center) {
    this.center = center;
  }
}
