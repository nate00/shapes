import shapes.*;
import java.awt.Color;

public class MyCircle extends Circle {

  public MyCircle() {
    super();
    setRadius(10);
    setFill(true);
  }

  @Override
  public void update() {
    Direction d = Keyboard.getDirection(KeySet.WASD);
    move(d, 30);
  }

  @Override
  public void init() {
    setColor(Color.GREEN);
  }
}
