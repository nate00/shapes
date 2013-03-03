/**
 * Not my code! Learning from http://zetcode.com/tutorials/javagamestutorial/animation/
 */

import java.awt.*;
import java.awt.geom.*;
import java.*;
import javax.swing.*;
import java.util.ArrayList;

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
    frame.setSize(800, 800);    // TODO: should be global constants
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

class Canvas extends JPanel implements Runnable {

  private Thread animator;
  private Game game;
  private ArrayList<Shape> shapes;

  private final int DELAY = 50; // TODO: FPS should be a global constant

  public Canvas(Game game) {
    super();

    this.game = game;

    shapes = new ArrayList<Shape>();

    setBackground(Color.BLUE);
    setDoubleBuffered(true);
  }

  public void addShape(Shape s) {
    shapes.add(s);
  }

  public void paint(Graphics g0) {
    super.paint(g0);

    Graphics2D g = (Graphics2D)g0;
    for (Shape s : shapes) {
      s.render(g);
    }

    Toolkit.getDefaultToolkit().sync();
    g0.dispose();
  }

  public void addNotify() {
    super.addNotify();
    animator = new Thread(this);
    animator.start();
  }

  public void run() {
    long beforeTime, timeDiff, sleep;

    beforeTime = System.currentTimeMillis();

    while (true) {

      game.update();
      repaint();

      timeDiff = System.currentTimeMillis() - beforeTime;
      sleep = DELAY - timeDiff;

      if (sleep < 0)
        sleep = 2;
      try {
        Thread.sleep(sleep);
      } catch (InterruptedException e) {
        System.out.println("interrupted");
      }

      beforeTime = System.currentTimeMillis();
    }
  }
}
