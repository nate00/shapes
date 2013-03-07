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
    init();
  }

  public void render(Graphics2D g) {
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
    center.move(direction, pixels);
    Set<Shape> solids = Game.getSolids();
    for (Shape solid : solids) {
      if (this.isTouching(solid)) {
        this.setColor(Color.PINK);
        return;
      }
    }
    this.setColor(Color.WHITE);
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
