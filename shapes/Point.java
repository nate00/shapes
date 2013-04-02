package shapes;

// TODO: debugging
import java.awt.*;

/**
 * Represents a point in the 2D plane. Points are not displayed in the game.
 */
public class Point extends Shape {
  private double x, y;

  public Point(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public Point translation(Vector v) {
    Point p = new Point(x, y);
    p.setX(p.getX() + v.getXComponent());
    p.setY(p.getY() + v.getYComponent());
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
  
  int getCanvasX() {
    return (int)x;
  }

  int getCanvasY() {
    return (int)(Game.HEIGHT - y);
  }

  public void setX(double x) {
    this.x = x;
  }

  public void setY(double y) {
    this.y = y;
  }

  public static Point random() {
    double x = Math.random() * Game.WIDTH;
    double y = Math.random() * Game.HEIGHT;
    return new Point(x, y);
  }

  public double distanceTo(Point point) {
    return Geometry.distance(this, point);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof Point) {
      return this.equals((Point) obj);
    }

    return false;
  }

  private boolean equals(Point that) {
    return 
      Math.abs(this.getX() - that.getX()) < Geometry.TOLERANCE &&
      Math.abs(this.getY() - that.getY()) < Geometry.TOLERANCE;
  }

  @Override
  public String toString() {
    return String.format("(%f, %f)", x, y);
  }

  // TODO: for debugging
  public Point maxMovement(Point p, Shape s) { return Point.random(); }
  public boolean isOffscreen() { return false; }
  public boolean contains(Point p) { return false; }
  public boolean contains(Shape s) { return false; }
  public void render(Graphics2D g) {
    if (!visible) return;
    g.setColor(Color.YELLOW);
    g.fillOval((int)getCanvasX() - 2, (int)getCanvasY() - 2, 4, 4);
  }
  private boolean visible = false;
  public void update() { }
  public void setup() { }
  public void setVisible() {visible = true;}
  public double getBottom() {return 0;}
  public double getTop() {return 0;}
  public double getLeft() {return 0;}
  public double getRight() {return 0;}
  public Point maxMovement(Point p, Segment s) {return p;}

}
