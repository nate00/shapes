import shapes.*;
import java.awt.Color;

public class AsteroidProjectile extends Rectangle {

  @Override
  public void setup() {
    setColor(Color.WHITE);
    setFilled(false);
    setWidth(10);
    setHeight(1);
    setInvisible(true);
  }

  @Override
  public void update() {
  }
}
