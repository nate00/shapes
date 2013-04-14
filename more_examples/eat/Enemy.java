import shapes.*;
import java.awt.Color;

public class Enemy extends Rectangle {
  private int delay = 50;

  @Override
  public void setup() {
    setColor(new Color(252, 194, 64));
    setWidth(70);
    setHeight(10);
    setDirection(Direction.UP);
    setSpeed(7);

    setFilled(false);

    // Set location randomly, making sure to be entirely inside the game window.
    // (Note that width and height have swapped since we rotated the rectangle
    // by ninety degrees.)
    double x = Math.random() * (Game.WIDTH - 2 * getHeight()) + getHeight();
    double y = Math.random() * (Game.HEIGHT - 2 * getWidth()) + getWidth();
    setUpperLeft(new Point(x, y));
  }

  @Override
  public void update() {
    if (isActive()) {
      setFilled(true);
    } else {
      delay--;
    }
  }

  public boolean isActive() {
    return delay <= 0;
  }

  public Enemy() {
    super();
    setup();
  }
}
