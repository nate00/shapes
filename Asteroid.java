import shapes.*;
import java.awt.Color;

public class Asteroid extends Circle {

  @Override
  public void setup() {
    setFilled(false);
    setColor(Color.WHITE);
    setSpeed(5);
    setRadius(Math.random() * 30 + 5);
    setDirection(Direction.random());
    setCenter(Point.random());
  }

  @Override
  public void update() {
    if (isTouchingBorder()) {
      setDirection(Direction.random());
    }
  }
}
