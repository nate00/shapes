import shapes.*;
import java.awt.Color;

public class AsteroidsGame extends Game {
  private Asteroid[] asteroids;
  private AsteroidShip ship;
  private AsteroidProjectile shot;

  @Override
  public void setup() {
    setBackgroundColor(Color.WHITE);

    asteroids = new Asteroid[10];
    for (int i = 0; i < asteroids.length; i++) {
      asteroids[i] = new Asteroid();
    }

    ship = new AsteroidShip();

    shot = ship.getShot();
  }

  @Override
  public void update() {
    for (Asteroid a : asteroids) {
      if (ship.isTouching(a)) {
        ship.setColor(Color.RED);
      }
      if (a.isTouching(shot)) {
        a.destroy();
      }
    }
  }

  public static void main(String[] args) {
    new AsteroidsGame();
  }
}
