import shapes.*;
import java.awt.Color;

public class MazeGame extends Game {
  Rectangle[] walls;
  Rectangle finish;
  MazeHero hero;

  @Override
  public void setup() {
    // make walls
    walls = new Rectangle[2];
    for (int i = 0; i < walls.length; i++) {
      walls[i] = new Rectangle();
      walls[i].setWidth(700);
      walls[i].setHeight(100);
      walls[i].setColor(Color.BLACK);
    }
    walls[0].setUpperLeft(new Point(0, 200));
    walls[1].setUpperLeft(new Point(100, 400));

    // make finish line
    finish = new Rectangle();
    finish.setUpperLeft(new Point(700, 500));
    finish.setWidth(10);
    finish.setHeight(100);
    finish.setColor(Color.GREEN);

    // make hero
    hero = new MazeHero();

    // prevent hero from leaving window
    setBorderSolid(true);
  }

  @Override
  public void update() {
    for (Rectangle wall : walls) {
      if (hero.isTouching(wall)) {
        hero.takeDamage();
      }
    }
    hero.move(Keyboard.direction(), 10);
    // celebrate if finished
    if (hero.isTouching(finish)) {
      hero.say("I win!!");
    }
  }

  public static void main(String[] args) {
    new MazeGame();
  }
}
