
import shapes.*;
import java.awt.Color;

public class MazeGame extends Game {
  Rectangle[] walls;
  Circle hero;

  @Override
  public void setup() {
    walls = new Rectangle[2];
    walls[0] = new Rectangle(new Point(200, 150), 400, 100);
    walls[1] = new Rectangle(new Point(300, 350), 400, 100);
    for (Rectangle wall : walls) {
      wall.setColor(Color.BLACK);
      wall.setSolid(true);
    }

    hero = new Circle(new Point(20, 20), 20);
    hero.setColor(Color.GREEN);
    Keyboard.printKeyPresses(true);
  }

  @Override
  public void update() {
    hero.move(Keyboard.direction(), 10);
  }

  public static void main(String[] args) {
    new MazeGame();
  }
}
