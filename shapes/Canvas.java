package shapes;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;

public class Canvas extends JPanel implements Runnable {

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
    setPreferredSize(new Dimension(Settings.CANVAS_WIDTH, Settings.CANVAS_HEIGHT));
    setSize(Settings.CANVAS_WIDTH, Settings.CANVAS_HEIGHT);
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
