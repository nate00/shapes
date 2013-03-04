import shapes.*;
import java.awt.Color;

public class MyGame extends Game {
  private Circle hero;
  private Circle evil;

  @Override
  public void init() {
    evil = new Circle();
    evil.setColor(Color.BLACK);
    hero = new MyCircle(evil);

    getCanvas().addShape(evil);
    getCanvas().addShape(hero);
  }

  @Override
  public void update() {
    hero.update();
  }

  public static void main(String[] args) {
    new MyGame();
  }
}
