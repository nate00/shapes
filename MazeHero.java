import shapes.*;
import java.awt.Color;

public class MazeHero extends Circle {

  public MazeHero() {
    super();
    // add code
  }
  private int health;
  @Override
  public void setup() {
    health = 50;
    setCenter(new Point(20, 20));
    setRadius(20);

    setColor(Color.PINK);
  }

  @Override
  public void update() {
    if (health <= 0) {
      setColor(Color.RED);
    }
  }

  public void takeDamage() {
    health--;
  }
}
