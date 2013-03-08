import shapes.*;
import java.awt.Color;

public class MyCircle extends Circle {

  public MyCircle() {
    super();
    setRadius(50);
    setFill(true);
  }

  @Override
  public void update() {
    Direction d = Keyboard.getDirection(KeySet.WASD);
    move(d, 10);
    for (Shape s : Game.getSolids()) {
      if (isTouching(s)) {
        this.say("Ouch!", 20);
      }
    }
  }

  @Override
  public void init() {
    setColor(Color.GREEN);
  }
}
