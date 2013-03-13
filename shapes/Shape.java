package shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.*;

public abstract class Shape {
  private Color color;
  private boolean fill; 
  private boolean invisible; 
  private boolean solid;
  private String speech;
  private int speechDuration;
  private Color speechColor;
  private boolean destroyed;
  protected Direction direction;
  private double speed;
  protected Point center;

  abstract public void setup();
  abstract public void update();
  abstract public void render(Graphics2D g);

  // true if this shape contains s entirely
  abstract public boolean contains(Shape s);
  abstract public boolean contains(Point p);

  public void autoUpdate() {
    if (this.isSpeaking()) {
      speechDuration--;
    }
    if (Math.abs(speed) > Geometry.EPSILON) {
      move(getDirection(), speed);
    }
  }

  // Overriding constructors should call super() and call setup() at the end
  public Shape() {
    Game.addShape(this);
    // set default values
    setColor(Color.GREEN);
    setFill(true);
    setSpeechColor(Color.BLACK);
    destroyed = false;
  }

  public void setCenter(double x, double y) {
    setCenter(new Point(x, y));
  }

  boolean isTouching(Segment that) {
    return Geometry.touching(this, that);
  }

  public boolean isTouching(Shape that) {
    return Geometry.touching(this, that);
  }

  abstract public boolean isOffscreen();

  public boolean isTouchingBorder() {
    if (isOffscreen()) {
      return true;
    }
    for (Segment border : Game.getCanvas().getBorders()) { 
      if (isTouching(border)) {
        return true;
      }
    }
    return false;
  }

  abstract Point maxMovement(Point target, Shape obstacle);

  public void move(double pixels) {
    move(getDirection(), pixels);
  }

  public void move(Direction direction, double pixels) {
    if (direction == null || Math.abs(pixels) < Geometry.EPSILON) {
      return;
    }
    Point end = getCenter().translation(new Vector(direction, pixels));
    Point maxMovement = end;
    Set<Shape> solids = Game.getSolids();
    for (Shape solid : solids) {
      Point blockedEnd = this.maxMovement(end, solid);
      if (Geometry.distance(getCenter(), blockedEnd) < Geometry.distance(getCenter(), maxMovement)) {
        maxMovement = blockedEnd;
      }
    }
    setCenter(maxMovement);
  }

  public void test(Shape s) {
  }

  public void test(Circle c) {
  }

  // TODO: test
  public boolean isClicked() {
    return this.contains(Mouse.clickLocation());
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

  public void say(String speech) {
    this.speech = speech;
    this.speechDuration = -1;
  }
  
  public void say(String speech, int frames) {
    this.speech = speech;
    this.speechDuration = frames;
  }

  public String getSpeech() {
    return speech;
  }

  public void setSpeechColor(Color speechColor) {
    this.speechColor = speechColor;
  }

  public Color getSpeechColor() {
    return speechColor;
  }

  public boolean isSpeaking() {
    return speechDuration != 0;
  }

  // returns null if target is null
  public Direction towards(Point target) {
    if (target == null) return null;
    Vector v = new Vector(this.getCenter(), target);
    return v.getDirection();
  }

  public Direction towards(Shape target) {
    return towards(target.getCenter());
  }

  public void destroy() {
    destroyed = true;
  }

  public boolean isDestroyed() {
    return destroyed;
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

  public Direction getDirection() {
    return direction;
  }

  public void setDirection(Direction direction) {
    this.direction = direction;
  }

  public double getSpeed() {
    return speed;
  }

  public void setSpeed(double speed) {
    this.speed = speed;
  }

  public Point getCenter() {
    return center;
  }
  
  public void setCenter(Point center) {
    this.center = center;
  }
}
