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

  private static Map<Integer, java.util.List<Shape>> layerContents;
  private static java.util.List<Integer> layers;
  private static Map<Shape, Integer> layerOf;

  private static boolean borderSolid;

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

    frame = new JFrame();
    Mouse mouse = new Mouse();
    frame.addMouseMotionListener(mouse);
    frame.addMouseListener(mouse);
    frame.addKeyListener(new Keyboard());
    frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);

    setBackgroundColor(Color.BLUE);
    borderSolid = false;
    setup();

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

  /**
   * Set the solidity of the window borders. The window borders are not solid
   * by default.
   *
   * @param solid true to prevent shapes from leaving the window, false to
   *              allow them to leave.
   */
  public static void setBorderSolid(boolean solid) {
    Game.borderSolid = solid;
  }

  /**
   * Returns true if the window borders are solid.
   *
   * @return  true if shapes are prevented from leaving the window, false if
   *          they are allowed to leave.
   */
  public static boolean isBorderSolid() {
    return borderSolid;
  }
}
