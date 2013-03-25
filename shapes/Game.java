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

  public static final int HEIGHT = 500;
  public static final int WIDTH = 500;
  
  public Game() {
    canvas = new Canvas(this);
    solidShapes =
      Collections.newSetFromMap(new ConcurrentHashMap<Shape, Boolean>());
    allShapes = 
      Collections.newSetFromMap(new ConcurrentHashMap<Shape, Boolean>());

    frame = new JFrame();
    Mouse mouse = new Mouse();
    frame.addMouseMotionListener(mouse);
    frame.addMouseListener(mouse);
    frame.addKeyListener(new Keyboard());
    frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);

    setBackgroundColor(Color.BLUE);
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
  }

  public static Shape[] getAllShapes() {
    return allShapes.toArray(new Shape[0]);
  }

  public static Color getBackgroundColor() {
    return canvas.getBackground();
  }

  public static void setBackgroundColor(Color backgroundColor) {
    canvas.setBackground(backgroundColor);
  }

  public static Point[] getCorners() {
    return canvas.getCorners();
  }

  static Segment[] getBorders() {
    return canvas.getBorders();
  }
}
