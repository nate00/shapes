package shapes;

/**
 * Adapted from http://zetcode.com/tutorials/javagamestutorial/animation/
 */

import java.awt.*;
import java.awt.geom.*;
import java.*;
import javax.swing.*;
import java.util.*;

public abstract class Game {
  private static JFrame frame;
  private static Canvas canvas;
  // TODO: figure out what the fuck Game is.
  // static methods?
  // singleton?
  // passed as a parameter?
  private static Set<Shape> solidShapes;
  private static Set<Shape> allShapes;
  
  public Game() {
    canvas = new Canvas(this);
    solidShapes = new HashSet<Shape>();
    allShapes = new HashSet<Shape>();

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

  public void autoUpdate() {
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

  public static Canvas getCanvas() {
    return canvas;
  }

  public static Set<Shape> getSolids() {
    return solidShapes;
  }

  public static void addSolid(Shape shape) {
    solidShapes.add(shape);
  }

  public static void removeSolid(Shape shape) {
    solidShapes.remove(shape);
  }

  public static void addShape(Shape shape) {
    allShapes.add(shape);
  }

  public static void removeShape(Shape shape) {
    allShapes.remove(shape);
  }

  public static Shape[] getAllShapes() {
    return allShapes.toArray(new Shape[0]);
  }

  public static Color getBackgroundColor() {
    return getCanvas().getBackground();
  }

  public static void setBackgroundColor(Color backgroundColor) {
    getCanvas().setBackground(backgroundColor);
  }
}
