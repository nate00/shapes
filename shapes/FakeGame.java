/**
 * Not my code! Learning from http://zetcode.com/tutorials/javagamestutorial/animation/
 */

import java.awt.*;
import java.awt.geom.*;
import java.*;
import javax.swing.*;

public class FakeGame extends JFrame {
  public FakeGame() {
    add(new Board());
    setTitle("FakeGame");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(500, 500);
    setLocationRelativeTo(null);
    setVisible(true);
    setResizable(false);
  }

  public static void main(String[] args) {
    new FakeGame();
  }
}

class Board extends JPanel implements Runnable {

    private Thread animator;
    private int x, y;

    private final int DELAY = 50;


    public Board() {
        setBackground(Color.BLACK);
        setDoubleBuffered(true);

        x = y = 10;
    }

    public void addNotify() {
        super.addNotify();
        animator = new Thread(this);
        animator.start();
    }

    public void paint(Graphics g) {
        super.paint(g);

        Graphics2D g2d = (Graphics2D)g;
        g2d.setColor(Color.blue);
        g2d.fillRect(x, y, 100, 100);
        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }


    public void cycle() {

        x += 10;
        y += 10;

        if (y > 240) {
            y = -45;
            x = -45;
        }
    }

    public void run() {

        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();

        while (true) {

            cycle();
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
