package shapes;

/**
 * Adapted from http://zetcode.com/tutorials/javagamestutorial/animation/
 */

import java.awt.*;
import java.awt.geom.*;
import java.*;
import javax.swing.*;
import java.util.*;

public class Game {
  private JFrame frame;
  private Canvas canvas;
  // TODO: figure out what the fuck Game is.
  // static methods?
  // singleton?
  // passed as a parameter?
  private static Set<Shape> solidShapes;
  
  public Game() {
    canvas = new Canvas(this);
    solidShapes = new HashSet<Shape>();

    init();

    frame = new JFrame();
    frame.addKeyListener(new Keyboard());
    frame.add(canvas);
    frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setResizable(false);
    frame.setVisible(true);
  }

  // Override this!
  public void init() {
  }

  // Override this!
  public void update() {
  }

  public Canvas getCanvas() {
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

  public static void main(String[] args) {
    new Game();
  }
}
