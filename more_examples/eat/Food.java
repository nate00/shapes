import shapes.*;
import java.awt.Color;

public class Food extends Circle {

  @Override
  public void setup() {
    setColor(new Color(77, 139, 77));
    setRadius(10);
    setCenter(randomCenter());
  }

  @Override
  public void update() {
  }
  
  public void respawn() {
    setCenter(randomCenter());
  }

  private Point randomCenter() {
    // pick location randomly, making sure to be entirely inside the game window
    double x = Math.random() * (Game.WIDTH - 2 * getRadius()) + getRadius();;
    double y = Math.random() * (Game.HEIGHT - 2 * getRadius()) + getRadius();
    return new Point(x, y);
  }


  public Food() {
    super();
    setup();
  }
}
