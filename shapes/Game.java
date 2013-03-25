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

public abstract class Game {
  private static JFrame frame;
  private static Canvas canvas;
  // TODO: figure out what the fuck Game is.
  // static methods?
  // singleton?
  // passed as a parameter?
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
