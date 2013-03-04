import java.awt.Color;
import java.awt.Graphics2D;

public class Circle extends Shape {
  private double radius;
  private Color color;

  public Circle() {
    center = new Point(100, Settings.CANVAS_HEIGHT - 100);
    radius = 100;
    color = Color.RED;
    init();
  }

  public void render(Graphics2D g) {
    g.setColor(color);
    g.fillOval((int)(center.getCanvasX() - radius),
               (int)(center.getCanvasY() - radius),
               (int)(radius * 2),
               (int)(radius * 2));
  }

  // Override this!
  public void update() {}

  // Override this!
  public void init() {}

  public double getRadius() {
    return radius;
  }

  public Color getColor() {
    return color;
  }

  public void setCenter(Point center) {
    this.center = center;
  }

  public void setRadius(double radius) {
    this.radius = radius;
  }

  public void setColor(Color color) {
    this.color = color;
  }
}
