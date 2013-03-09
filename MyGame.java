import shapes.*;
import java.awt.Color;

public class MyGame extends Game {
  private Circle hero;
  private Circle[] evil;
  private Circle dipshit;

  @Override
  public void setup() {
    evil = new Circle[3];
    evil[0] = new Circle();
    evil[1] = new Circle();
    evil[2] = new Circle();
    evil[0].setCenter(0, 0);
    evil[1].setCenter(300, 300);
    evil[2].setCenter(0, 500);

    for (Circle c : evil) {
      c.setColor(Color.BLACK);
      c.setSolid(true);
    }

    hero = new MyCircle();

    evil[1].setSolid(false);

    dipshit = new Circle();
    dipshit.setColor(Color.RED);
    dipshit.setDirection(Direction.LEFT);
    dipshit.setSpeed(2);
    dipshit.setRadius(5);
    dipshit.setCenter(new Point(250, 250));
  }

  @Override
  public void update() {
    for (Circle c : evil) {
      c.move(c.towards(hero), 2);
      if (c.contains(hero)) {
        c.say("Nom!", 20);
      }
    }
    if (hero.isOffscreen()) {
      evil[0].say("off!", 20);
    }
  }

  public static void main(String[] args) {
    new MyGame();
  }
}
