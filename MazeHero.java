import shapes.*;
import java.awt.Color;

public class MazeHero extends Circle {

  @Override
  public void setup() {
    setCenter(new Point(20, 20));
    setRadius(20);

    setColor(Color.PINK);
  }

  @Override
  public void update() {
    move(Keyboard.direction(), 10);
  }
}
