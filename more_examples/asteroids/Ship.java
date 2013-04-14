import shapes.*;
import java.awt.Color;

public class Ship extends Triangle {
  private Projectile shot;
  private boolean invincible;
  private int invincibleTimer;
  private Counter lives;

  private static int INVINCIBLE_DURATION = 80;
  private static int INITIAL_LIVES = 3;

  @Override
  public void setup() {
    shot = new Projectile();
    setColor(Color.WHITE);
    setCenter(new Point(Game.WIDTH / 2, Game.HEIGHT / 2));
    setFilled(false);
    setSize(10);
    invincible = true;
    invincibleTimer = INVINCIBLE_DURATION;

    TextStyle speechStyle = TextStyle.sansSerif();
    speechStyle.setColor(Color.WHITE);
    speechStyle.setBackgroundColor(null);
    setSpeechStyle(speechStyle);

    // set up the lives counter
    lives = new Counter("Lives", INITIAL_LIVES);
    TextStyle counterStyle = TextStyle.sansSerif();
    counterStyle.setColor(Color.WHITE);
    Game.setCounterStyle(counterStyle);
  }

  public Projectile getShot() {
    return shot;
  }

  @Override
  public void update() {
    // blink if invisible
    if (invincible) {
      setInvisible(!isInvisible());
      invincibleTimer--;
      if (invincibleTimer <= 0) {
        invincible = false;
      }
    } else {
      setInvisible(false);
    }

    // movement
    if (Keyboard.keyIsPressed(Keyboard.UP)) {
      move(5);
    }
    if (Keyboard.keyIsPressed(Keyboard.LEFT)) {
      rotate(5);
    }
    if (Keyboard.keyIsPressed(Keyboard.RIGHT)) {
      rotate(-5);
    }

    // shoot if the spacebar is pressed
    if (Keyboard.keyIsPressed(Keyboard.SPACE)) {
      shot.setCenter(getCenter());
      shot.setDirection(getDirection());
      shot.setLaunched(true);
    }
  }

  public void collide() {
    if (invincible) {
      // collisions don't matter when invincible
      return;
    }

    lives.decreaseBy(1);
    if (lives.getValue() < 0) {
      Game.setTitle("GAME OVER", 10000);
    }
    invincible = true;
    invincibleTimer = INVINCIBLE_DURATION;
    setCenter(Game.WIDTH / 2, Game.HEIGHT / 2);
    say("Ouch!", 40);
  }


  public Ship() {
    super();
    setup();
  }
}
