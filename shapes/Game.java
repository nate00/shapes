package shapes;

/**
 * Adapted from http://zetcode.com/tutorials/javagamestutorial/animation/
 */

import java.awt.*;
import java.awt.geom.*;
import java.*;
import javax.swing.*;
import java.util.*;
import java.util.concurrent.*;
import java.lang.*;

/**
 * A game built using the Shapes framework.
 * <p>
 * <code>Game</code> is one of the two most important classes (along with
 * {@link Shape}) for building a game using the Shapes framework. To make
 * a game, you will subclass <code>Game</code> and override the
 * {@link #setup()} and {@link #update()} functions. In <code>setup()</code>,
 * you will write code that will execute when your game begins, and in
 * <code>update()</code> you will write code that executes once per frame.
 * <p>
 * <code>Game</code> also has a few useful static methods and constants. For
 * example, you can call <code>Game.getAllShapes()</code> to get all the shapes
 * that currently exist in your game.
 */
public abstract class Game {
  private static JFrame frame;
  private static Canvas canvas;
  private static Set<Shape> solidShapes;
  private static Set<Shape> allShapes;

  private static java.util.List<Counter> counters;
  private static TextStyle counterStyle;

  private static Map<Integer, java.util.List<Shape>> layerContents;
  private static java.util.List<Integer> layers;
  private static Map<Shape, Integer> layerOf;

  private static String title;
  private static TextStyle titleStyle;
  private static int titleDuration;

  private static String subtitle;
  private static TextStyle subtitleStyle;
  private static int subtitleDuration;

  public enum BorderBehavior { NONE, SOLID, BOUNCE };
  private static BorderBehavior borderBehavior;

  public static final int HEIGHT = 500;
  public static final int WIDTH = 800;
  
  public Game() {
    canvas = new Canvas(this);
    solidShapes =
      Collections.newSetFromMap(new ConcurrentHashMap<Shape, Boolean>());
    allShapes = 
      Collections.newSetFromMap(new ConcurrentHashMap<Shape, Boolean>());

    // TODO: sort out which data structures actually have to support concurrency
    layerContents = new ConcurrentHashMap<Integer, java.util.List<Shape>>();
    layers = new CopyOnWriteArrayList<Integer>();
    layerOf = new ConcurrentHashMap<Shape, Integer>();

    counters = new ArrayList<Counter>();

    frame = new JFrame();
    Mouse mouse = new Mouse();
    frame.addMouseMotionListener(mouse);
    frame.addMouseListener(mouse);
    frame.addKeyListener(new Keyboard());
    frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);

    setDefaults();
  }

  private void setDefaults() {
    setBackgroundColor(Color.BLUE);
    setBorderBehavior(BorderBehavior.NONE);

    TextStyle titleStyle = TextStyle.sansSerif();
    titleStyle.setFontSize(40);
    setTitleStyle(titleStyle);
    setCounterStyle(TextStyle.sansSerif());
    setSubtitleStyle(TextStyle.sansSerif());
  }
  
  protected void ready() {
    frame.add(canvas);
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setResizable(false);
    frame.setVisible(true);
  }

  // Override this!
  public void setup() {
  }

  // Override this!
  public void update() {
  }

  void autoUpdate() {
    for (Shape s : allShapes) {
      s.autoUpdate();
    }

    Iterator<Shape> iter = allShapes.iterator();
    while (iter.hasNext()) {
      Shape s = iter.next();
      s.update();
      if (s.isDestroyed()) {
        if (s.isSolid()) {
          removeSolid(s);
        }
        removeFromLayers(s);
        iter.remove();
      }
    }
  }

  static Set<Shape> getSolids() {
    return solidShapes;
  }

  static void addSolid(Shape shape) {
    solidShapes.add(shape);
  }

  static void removeSolid(Shape shape) {
    solidShapes.remove(shape);
  }

  static void addShape(Shape shape) {
    allShapes.add(shape);
  }

  static void removeShape(Shape shape) {
    allShapes.remove(shape);
    removeFromLayers(shape);
  }

  public static Shape[] getAllShapes() {
    return allShapes.toArray(new Shape[0]);
  }

  static void removeFromLayers(Shape shape) {
    if (!layerOf.containsKey(shape)) return;

    int oldLayer = layerOf.get(shape);
    layerContents.get(oldLayer).remove(shape);
    if (layerContents.get(oldLayer).isEmpty()) {
      layerContents.remove(oldLayer);
      layers.remove((Integer) oldLayer);
    }
    layerOf.remove(shape);
  }

  static void setLayer(Shape shape, int layer) {
    removeFromLayers(shape);

    // add new stuff
    if (!layerContents.containsKey(layer)) {
      layerContents.put(layer, new CopyOnWriteArrayList<Shape>());
      int insertionPoint = ~Collections.binarySearch(layers, layer);
      layers.add(insertionPoint, layer);
    }
    layerContents.get(layer).add(shape);
    layerOf.put(shape, layer);
  }

  static java.util.List<Shape> getLayerContents(int layer) {
    return layerContents.get(layer);
  }

  static int getLayerOf(Shape shape) {
    return layerOf.get(shape);
  }

  static java.util.List<Integer> getLayers() {
    return layers;
  }

  static void addCounter(Counter counter) {
    counters.add(counter);
  }

  static void removeCounter(Counter counter) {
    counters.remove(counter);
  }

  public static void setCounterStyle(TextStyle counterStyle) {
    Game.counterStyle = counterStyle;
  }

  public static TextStyle getCounterStyle() {
    return Game.counterStyle;
  }

  public static Color getBackgroundColor() {
    return canvas.getBackground();
  }

  public static void setBackgroundColor(Color backgroundColor) {
    canvas.setBackground(backgroundColor);
  }

  /**
   * Returns four points representing the corners of the window.
   *
   * @return  the corners of the window.
   */
  public static Point[] getCorners() {
    return canvas.getCorners();
  }

  static Segment[] getBorders() {
    return canvas.getBorders();
  }

  // not public because no user-defined methods should be executing while
  // a title is displayed.
  boolean hasTitle() {
    return titleDuration > 0;
  }

  public boolean hasSubtitle() {
    return subtitleDuration > 0 || subtitleDuration == -1;
  }

  public static void setTitleStyle(TextStyle titleStyle) {
    Game.titleStyle = titleStyle;
  }

  public static TextStyle getTitleStyle() {
    return titleStyle;
  }

  public static void setTitle(String title, int duration) {
    if (duration < 0) {
      throw new IllegalArgumentException("Duration cannot be negative.");
    }
    if (title == null) {
      throw new IllegalArgumentException("Title cannot be null.");
    }
    Game.title = title;
    Game.titleDuration = duration;
  }

  void renderTitle(Graphics2D g) {
    titleDuration--;
    titleStyle.renderString(
      title,
      new Point(WIDTH / 2.0, HEIGHT / 2.0),
      TextStyle.ReferencePointLocation.CENTER,
      g
    );
  }

  public static void setSubtitleStyle(TextStyle subtitleStyle) {
    Game.subtitleStyle = subtitleStyle;
  }

  public static TextStyle getSubtitleStyle() {
    return subtitleStyle;
  }

  public static void setSubtitle(String subtitle, int duration) {
    if (duration < 0) {
      throw new IllegalArgumentException("Duration cannot be negative.");
    }
    if (subtitle == null) {
      duration = 0;
    }
    Game.subtitle = subtitle;
    Game.subtitleDuration = duration;
  }

  public static void setSubtitle(String subtitle) {
    if (subtitle == null) {
      Game.subtitleDuration = 0;
    } else {
      Game.subtitleDuration = -1;
    }
    Game.subtitle = subtitle;
  }

  void renderSubtitle(Graphics2D g) {
    subtitleDuration--;
    subtitleStyle.renderString(
      subtitle,
      new Point(WIDTH / 2.0, 30),
      TextStyle.ReferencePointLocation.BOTTOM_CENTER,
      g
    );
  }

  void renderCounters(Graphics2D g) {
    Counter.renderCounters(counters, counterStyle, g);
  }

  /**
   * Set the behavior of the window borders. The options are:
   * <ul>
   *  <li><code>NONE</code>: allow shapes to leave the window.</li>
   *  <li><code>SOLID</code>: prevent shapes from leaving the window. (See
   *  {@link Shape#setSolid}.)</li>
   *  <li><code>BOUNCE</code>: make shapes bounce when they hit the window
   *  borders, i.e., reverse direction as if bouncing off a physical
   *  border.</li>
   * </ul>
   * <p>
   * Syntax example:
   * <code>
   *  Game.setBorderBehavior(Game.BorderBehavior.SOLID);
   * </code>
   *
   * @param behavior  specifies how shapes will act when they reach the border
   *                  of the window.
   */
  public static void setBorderBehavior(BorderBehavior behavior) {
    Game.borderBehavior = behavior;
  }

  /**
   * Returns the behavior of the border windows. The options are:
   * <ul>
   *  <li><code>NONE</code>: allow shapes to leave the window.</li>
   *  <li><code>SOLID</code>: prevent shapes from leaving the window. (See
   *  {@link Shape#setSolid}.)</li>
   *  <li><code>BOUNCE</code>: make shapes bounce when they hit the window
   *  borders, i.e., reverse direction as if bouncing off a physical
   *  border.</li>
   * </ul>
   *
   * @return  a <code>BorderBehavior</code> specifying the way shapes react
   *          when they reach the border of the window.
   */
  public static BorderBehavior getBorderBehavior() {
    return borderBehavior;
  }

  public enum Border { 
    TOP(Direction.UP, new Segment(0, HEIGHT, WIDTH, HEIGHT)),
    RIGHT(Direction.RIGHT, new Segment(WIDTH, HEIGHT, WIDTH, 0)),
    BOTTOM(Direction.DOWN, new Segment(WIDTH, 0, 0, 0)),
    LEFT(Direction.LEFT, new Segment(0, 0, 0, HEIGHT)),
    OFFSCREEN(null, null);

    private Direction direction;
    private Segment segment;
  
    private Border(Direction d, Segment seg) {
      direction = d;
      segment = seg;
    }

    public Direction getDirection() {
      return direction;
    }

    Segment getSegment() {
      return segment;
    }

    public static Border[] all() {
      return new Border[] { TOP, RIGHT, BOTTOM, LEFT };
    }
  };
}
