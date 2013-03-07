import shapes.*;
import java.awt.Color;

public class MyCircle extends Circle {
  private Shape enemy;

  public MyCircle(Shape enemy) {
    this.enemy = enemy;
  }

  @Override
  public void update() {
    Direction d = Keyboard.getDirection(KeySet.WASD);
    move(d, 10);
    if (this.isTouching(enemy)) {
      setInvisible(false);
    } else {
      setInvisible(true);
    }
  }

  @Override
  public void init() {
    setColor(Color.GREEN);
  }
}
