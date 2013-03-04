import java.awt.Color;

public class MyCircle extends Circle {
  @Override
  public void update() {
    Direction d = Keyboard.getDirection(KeySet.ARROWS);
    move(d, 10);
  }

  @Override
  public void init() {
    setColor(Color.GREEN);
  }
}
