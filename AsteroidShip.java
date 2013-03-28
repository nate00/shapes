import shapes.*;
import java.awt.Color;

public class AsteroidShip extends Triangle {
  AsteroidProjectile shot;

  @Override
  public void setup() {
    shot = new AsteroidProjectile();
    setColor(Color.WHITE);
    setFilled(false);
    System.out.println("Derp!");
    setSize(10);
  }

  public AsteroidProjectile getShot() {
    return shot;
  }

  @Override
  public void update() {
    System.out.println(getDirection().toString());
    if (Keyboard.keyIsPressed("Up")) {
      move(5);
    }
    if (Keyboard.keyIsPressed("Left")) {
      rotate(5);
    }
    if (Keyboard.keyIsPressed("Right")) {
      rotate(-5);
    }
    if (Keyboard.keyIsPressed("Space")) {
      shot.setCenter(getCenter());
      shot.setDirection(getDirection());
      shot.setSpeed(7);
      shot.setInvisible(false);
    }
  }
}
