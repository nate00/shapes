import shapes.*;
import java.awt.Color;
import java.util.*;

public class EatGame extends Game {
  Triangle hero = new Triangle(new Point(), 20);
  java.util.List<Enemy> enemies = new ArrayList<Enemy>();
  Food food = new Food();
  Counter eatenCount = new Counter("Eaten", 0);

  boolean gameOver = false;

  @Override
  public void setup() {
    // set up game window
    setBackgroundColor(new Color(75, 57, 41));
    setBorderBehavior(Game.BorderBehavior.BOUNCE);

    // set up text styles
    Color offWhite = new Color(206, 214, 180);
    Game.getCounterStyle().setColor(offWhite);
    Game.getTitleStyle().setColor(offWhite);
    
    // set up hero
    hero.setColor(offWhite);
    hero.setSpeed(15);
  }

  @Override
  public void update() {
    if (gameOver) {
      setTitle("You ate " + eatenCount.getValue(), 100000);
      return;
    }

    // rotate hero according to arrow keys
    if (Keyboard.keyIsPressed(Keyboard.LEFT)) {
      hero.rotate(5);
    }
    if (Keyboard.keyIsPressed(Keyboard.RIGHT)) {
      hero.rotate(-5);
    }

    // kill hero if touching enemy
    for (Enemy enemy : enemies) {
      if (enemy.isActive() && hero.isTouching(enemy)) {
        setTitle("Game over", 80);
        gameOver = true;
      }
    }

    // eat if hero touching food
    if (hero.isTouching(food)) {
      enemies.add(new Enemy());
      food.respawn();
      eatenCount.increaseBy(1);
    }
  }

  public static void main(String[] args) {
    new EatGame();
  }

  public EatGame() {
    super(false);
    setup();
    ready();
  }
}
