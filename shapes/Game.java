package shapes;
/**
 * Adapted from http://zetcode.com/tutorials/javagamestutorial/animation/
 */

import java.awt.*;
import java.awt.geom.*;
import java.*;
import javax.swing.*;

public class Game {
  private JFrame frame;
  private Canvas canvas;
  
  public Game() {
    canvas = new Canvas(this);

    init();

    frame = new JFrame();
    frame.addKeyListener(new Keyboard());
    frame.add(canvas);
    frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
    frame.pack();
    //frame.setSize(Settings.CANVAS_WIDTH, Settings.CANVAS_HEIGHT);
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
    frame.setResizable(false);
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

  public static void main(String[] args) {
    new Game();
  }
}

