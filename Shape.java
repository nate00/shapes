import java.awt.Color;
import java.awt.Graphics2D;

public abstract class Shape {
  protected Color color;
  protected Point center;

  abstract public void update();
  abstract public void render(Graphics2D g);

  public void move(Direction direction, double pixels) {
    center.move(direction, pixels);
  }

  public boolean isTouching(Shape that) {
    return Geometry.touching(this, that);
  }

  public void moveRight(double pixels) {
    move(Direction.RIGHT, pixels);
  }

  public void moveLeft(double pixels) {
    move(Direction.LEFT, pixels);
  }

  public void moveUp(double pixels) {
    move(Direction.UP, pixels);
  }
  
  public void moveDown(double pixels) {
    move(Direction.DOWN, pixels);
  }
  
  public Point getCenter() {
    return center;
  }
}
