import shapes.*;
import java.awt.Color;
import java.util.*;

public class AsteroidsGame extends Game {
  private List<Asteroid> asteroids;
  private Ship ship;
  private Projectile shot;

  private static int INITIAL_ASTEROIDS = 10;

  @Override
  public void setup() {
    setBackgroundColor(Color.BLACK);
    setBorderBehavior(BorderBehavior.BOUNCE);
    getTitleStyle().setColor(Color.WHITE);

    asteroids = new ArrayList<Asteroid>();
    for (int i = 0; i < INITIAL_ASTEROIDS; i++) {
      Asteroid a = new Asteroid();
      a.setRadius(Asteroid.BIG_RADIUS);
      asteroids.add(a);
    }

    ship = new Ship();

    shot = ship.getShot();

    // display instructions
    getSubtitleStyle().setColor(Color.WHITE);
    setSubtitle("Steer with the arrow keys\nShoot with the space bar", 200);
  }

  @Override
  public void update() {
    if (asteroids.size() == 0) {
      setTitle("VICTORY IS YOURS\nDUDE", 10000);
      return;
    }
    List<Asteroid> newAsteroids = new ArrayList<Asteroid>();
    Iterator<Asteroid> iter = asteroids.iterator();
    while (iter.hasNext()) {
      Asteroid a = iter.next();
      if (ship.isTouching(a)) {
        ship.collide();
      }
      if (a.isTouching(shot)) {
        iter.remove();
        // big asteroids split, small asteroids just disappear
        if (a.getRadius() == Asteroid.BIG_RADIUS) {
          for (int i = 0; i < 2; i++) {
            Asteroid newAsteroid = new Asteroid();
            newAsteroid.setCenter(a.getCenter());
            newAsteroid.setDirection(Direction.random());
            newAsteroid.move(20);   // to get out of the way of the projectile
            newAsteroids.add(newAsteroid);
          }
        }
        a.destroy();
      }
    }
    asteroids.addAll(newAsteroids);
  }

  public static void main(String[] args) {
    new AsteroidsGame();
  }

  public AsteroidsGame() {
    super(false);
    setup();
    ready();
  }
}
