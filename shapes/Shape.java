package shapes;

import java.awt.Color;
import java.awt.Graphics2D;

public abstract class Shape {
  private Color color;
  private boolean fill; 
  private boolean invisible; 
  private boolean solid;
  private String speech;
  private int speechDuration;
  private Color speechColor;
  private boolean destroyed;
  private Direction direction;
  private double speed;

  abstract public void update();
  abstract public void render(Graphics2D g);

  // true if this shape contains s entirely
  abstract public boolean contains(Shape s);
  abstract public boolean contains(Point p);

  abstract public void setCenter(Point center);
  abstract public Point getCenter();

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

  abstract public void move(Direction direction, double pixels);

  public void move(double pixels) {
    move(getDirection(), pixels);
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

  public Direction towards(Shape s) {
    Vector v = new Vector(this.getCenter(), s.getCenter());
    return v.getDirection();
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
}
