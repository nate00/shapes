import shapes.*;
import java.awt.Color;

public class ExampleCircle extends Circle {

  @Override
  public void setup() {
    setCenter(new Point(750, 450));
    setRadius(20);

    setColor(Color.PINK);
  }

  @Override
  public void update() {
    // follow the arrow keys
    move(Keyboard.direction(), 10);
  }

  public ExampleCircle() {
    super();
    setup();
  }
}
