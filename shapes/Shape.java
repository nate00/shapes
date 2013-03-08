package shapes;

import java.awt.Color;
import java.awt.Graphics2D;

public abstract class Shape {
  private Color color;
  private boolean fill; 
  private boolean invisible; 
  private boolean solid;

  abstract public void update();
  abstract public void render(Graphics2D g);

  abstract public void setCenter(Point center);
  abstract public Point getCenter();

  public void setCenter(double x, double y) {
    setCenter(new Point(x, y));
  }

  abstract public void move(Direction direction, double pixels);

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

  public void setFill(boolean fill) {
    this.fill = fill;
  }

  public boolean getFill() {
    return fill;
  }

  public void setInvisible(boolean invisible) {
    this.invisible = invisible;
  }

  public boolean getInvisible() {
    return invisible;
  }
  
  public void setColor(Color color) {
    this.color = color;
  }

  public Color getColor() {
    return color;
  }

  public void setSolid(boolean solid) {
    if (this.solid == solid) {
      return;
    }

    if (solid) {
      Game.addSolid(this);
    } else {
      Game.removeSolid(this);
    }

    this.solid = solid;
  }

  public boolean getSolid() {
    return solid;
  }
}
