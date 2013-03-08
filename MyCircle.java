import shapes.*;
import java.awt.Color;

public class MyCircle extends Circle {

  @Override
  public void setup() {
    setRadius(50);
    setFill(true);
    setColor(Color.GREEN);
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
}
