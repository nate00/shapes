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

  /**
   * Initializes the Shape. When you subclass shape, you'll
   * override this method to do things like set the shape's color, set its
   * starting position, etc.
   */
  abstract public void setup();
  /**
   * Updates the shape once per frame. When you subclass shape, you'll
   * override this method to do things like move the shape, change its
   * size, etc.
   */
  abstract public void update();

  /**
   * Draws the shape to the canvas.
   */
  abstract void render(Graphics2D g);

  /**
   * Checks if this shape contains another given shape.
   *
   * @param  s  the shape that may be contained in this shape.
   * @return    true if s is entirely inside this shape, false otherwise.
   */
  abstract public boolean contains(Shape s);

  /**
   * Checks if this shape contains a given point.
   *
   * @param p   a point to check for inside this shape.
   * @return    true if p is inside or on the border of this shape.
   */
  abstract public boolean contains(Point p);

  /**
   * Updates the shape automatically.
   *
   * This update includes moving the shape when it has a speed, etc.
   */
  void autoUpdate() {
    if (this.isSpeaking()) {
      speechDuration--;
    }
    if (Math.abs(speed) > Geometry.EPSILON) {
      move(getDirection(), speed);
    }
  }

  // Overriding constructors should call super() and call setup() at the end
  /**
   * Constructs a new shape with default values.
   *
   * Overriding constructors should call super() to ensure that Game.addShape()
   * is called, and should call setup().
   */
  public Shape() {
    Game.addShape(this);
    // set default values
    setColor(Color.GREEN);
    setFill(true);
    setSpeechColor(Color.BLACK);
    destroyed = false;
  }

  /**
   * Changes the location of this shape.
   *
   * @param x the x-coordinate of this shape's new center
   * @param y the y-coordinate of this shape's new center
   */
  public void setCenter(double x, double y) {
    setCenter(new Point(x, y));
  }

  /**
   * Checks if this shape is touching a given segment.
   *
   * @param   seg the segment to check.
   * @return      true if this shape is touching <code>seg</code>, false 
   *              otherwise.
   */
  boolean isTouching(Segment seg) {
    return Geometry.touching(this, seg);
  }

  /**
   * Checks if this shape is touching another shape.
   *
   * @param   s the shape to check.
   * @return    true if this shape is touching <code>s</code>, false otherwise.
   */
  public boolean isTouching(Shape s) {
    return Geometry.touching(this, s);
  }

  /**
   * TODO
   */
  abstract public boolean isOffscreen();

  /**
   * Checks if this shape is touching the border of the window.
   *
   * @return  true if any part of the shape is touching the border of the
   *          window, or if the shape is entirely outside the window, and false
   *          otherwise.
   */
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

  /**
   * Finds the farthest this shape can go towards a target with an obstacle in
   * the way.
   *
   * If this shape tries to move to <code>target</code>, then
   * <code>obstacle</code> may get in its way. Finds the farthest this shape
   * can move towards <code>target</code>, and returns the location where the
   * shape's center would be if it advanced as far as possible.
   *
   * @param   target    the point towards which this shape is moving.
   * @param   obstacle  another shape which may obstruct this shape's movement.
   * @return            a point representing the farthest this shape's center
   *                    can move towards <code>target</code>.
   */
  abstract Point maxMovement(Point target, Shape obstacle);

  /**
   * Moves in the shape's direction.
   *
   * Moves <code>pixels</code> pixels in the direction set using
   * <code>setDirection()</code>. Won't move through solid shapes.
   *
   * @param   pixels  the distance to move.
   * @see     #move(Direction, double)
   * @see     #setDirection(Direction)
   */
  public void move(double pixels) {
    move(getDirection(), pixels);
  }

  /**
   * Moves in the given direction.
   *
   * Moves <code>pixels</code> pixels in the given direction.
   * Won't move through solid shapes.
   *
   * @param   pixels    the distance to move.
   * @param   direction the direction in which to move.
   * @see     #move(double)
   */
  public void move(Direction direction, double pixels) {
    if (direction == null || Math.abs(pixels) < Geometry.EPSILON) {
      return;
    }
    Point end = getCenter().translation(new Vector(direction, pixels));
    System.out.println(end.toString());
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

//  public void test(Shape s) {
//  }
//
//  public void test(Circle c) {
//  }

  // TODO: test
  /**
   * Checks if this shape was clicked since the previous frame.
   *
   * Returns true if:
   * <ul>
   *  <li>the mouse button was clicked inside this shape since the last
   *  frame</li>
   *  <li>the mouse was dragged into this shape while the mouse button was
   *  down, and is currently inside this shape</li>
   * </ul>
   *
   * @return  true if this shape was clicked since the previous frame, false otherwise.
   * @see     Mouse#clickLocation()
   */
  public boolean isClicked() {
    return this.contains(Mouse.clickLocation());
  }

  /**
   * Moves right.
   * <p>
   * Moves <code>pixels</code> pixels to the right.
   * Won't move through solid shapes.
   *
   * @param   pixels  the distance to move.
   * @see     #move(Direction, double)
   */
  public void moveRight(double pixels) {
    move(Direction.RIGHT, pixels);
  }

  /**
   * Moves left.
   * <p>
   * Moves <code>pixels</code> pixels to the left.
   * Won't move through solid shapes.
   *
   * @param   pixels  the distance to move.
   * @see     #move(Direction, double)
   */
  public void moveLeft(double pixels) {
    move(Direction.LEFT, pixels);
  }

  /**
   * Moves up.
   * <p>
   * Moves <code>pixels</code> pixels to the up.
   * Won't move through solid shapes.
   *
   * @param   pixels  the distance to move.
   * @see     #move(Direction, double)
   */
  public void moveUp(double pixels) {
    move(Direction.UP, pixels);
  }
  
  /**
   * Moves down.
   * <p>
   * Moves <code>pixels</code> pixels to the down.
   * Won't move through solid shapes.
   *
   * @param   pixels  the distance to move.
   * @see     #move(Direction, double)
   */
  public void moveDown(double pixels) {
    move(Direction.DOWN, pixels);
  }

  /**
   * Displays text near this shape.
   *
   * Continues speaking until this method is called again with new text.
   *
   * @param   speech  what the shape says.
   * @see     #say(String, int)
   * @see     #setSpeechColor(Color)
   * @see     #getSpeech()
   * @see     #isSpeaking()
   */
  public void say(String speech) {
    this.speech = speech;
    this.speechDuration = -1;
  }
  
  /**
   * Displays text near this shape for a limited time.
   *
   * @param   speech  what the shape says.
   * @param   frames  how long the shape speaks.
   * @see     #say(String)
   * @see     #setSpeechColor(Color)
   * @see     #getSpeech()
   * @see     #isSpeaking()
   */
  public void say(String speech, int frames) {
    this.speech = speech;
    this.speechDuration = frames;
  }

  /**
   * Returns what this shape is saying.
   *
   * @return  what this shape is saying, or <code>null</code> if the shape
   *          isn't saying anything.
   * @see     #say(String, int)
   * @see     #say(String)
   */
  public String getSpeech() {
    if (isSpeaking()) {
      return speech;
    } else {
      return null;
    }
  }

  /**
   * Sets the color of this shape's speech.
   *
   * @param   speechColor the color of this shape's speech.
   * @see     #getSpeechColor()
   */
  public void setSpeechColor(Color speechColor) {
    this.speechColor = speechColor;
  }

  /**
   * Returns the color of this shape's speech.
   *
   * @return  the color of this shape's speech.
   * @see     #setSpeechColor(Color)
   */
  public Color getSpeechColor() {
    return speechColor;
  }

  /**
   * Returns true if the shape is currently speaking.
   *
   * @return  true if the shape is speaking, false otherwise.
   * @see     #say(String, int)
   * @see     #say(String)
   * @see     #getSpeech()
   */
  public boolean isSpeaking() {
    return speechDuration != 0;
  }

  /**
   * Returns the direction of a given point relative to this shape.
   *
   * Can be used to {@link #move} this shape towards a point:
   * <code>this.move(this.towards(targetPoint), 10.0);</code>
   *
   * @param   target  the point to aim towards.
   * @return          the direction of <code>target</code> relative to this
   *                  shape, or null if <code>target</code> is null.
   * @see             #towards(Shape)
   * @see             #move(Direction, double)
   * @see             #setDirection(Direction)
   */
  public Direction towards(Point target) {
    if (target == null) return null;
    Vector v = new Vector(this.getCenter(), target);
    return v.getDirection();
  }

  /**
   * Returns the direction of a shape relative to this shape.
   *
   * Can be used to {@link #move} this shape towards another shape:
   * <code>this.move(this.towards(targetShape), 10.0);</code>
   *
   * @param   target  the shape to aim towards.
   * @return          the direction of <code>target</code>'s relative to this
   *                  shape's center.
   * @see             #towards(Point)
   * @see             #move(Direction, double)
   * @see             #setDirection(Direction)
   */
  public Direction towards(Shape target) {
    return towards(target.getCenter());
  }

  /**
   * Destroys this shape so it will no longer be rendered.
   *
   * Call this method when you have finished using the shape.
   */
  public void destroy() {
    // TODO: who remove the same from Game?
    destroyed = true;
  }

  /**
   * Returns true if this shape has been destroyed.
   *
   * @return  true if {@link #destroy()} has been called on this shape,
   *          false otherwise.
   */
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
