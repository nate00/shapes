package more_examples.asteroids;

import shapes.*;
import java.awt.Color;

public class Asteroid extends Circle {
  public static int BIG_RADIUS = 20;
  public static int SMALL_RADIUS = 5;

  @Override
  public void setup() {
    setFilled(false);
    setColor(Color.WHITE);
    setSpeed(5);
    setDirection(Direction.random());
    setCenter(Point.random());
  }

  @Override
  public void update() {
  }

  public Asteroid() {
    super();
    setup();
  }
}
