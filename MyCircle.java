import java.awt.Color;

public class MyCircle extends Circle {
  @Override
  public void update() {
    Direction d = Keyboard.getDirection(KeySet.ARROWS);
    move(d, 10);
  }

  @Override
  public void init() {
    setCenter(new Point(200.0, 200.0));
    setRadius(200.0);
    setColor(Color.GREEN);
  }
}
