import shapes.*;
import java.awt.Color;

public class ExampleGame extends Game {
  Rectangle[] walls = new Rectangle[2];
  Rectangle finish;
  ExampleCircle hero;

  @Override
  public void setup() {
    // make walls
//    walls = new Rectangle[2];
    for (int i = 0; i < walls.length; i++) {
      walls[i] = new Rectangle();
      walls[i].setWidth(700);
      walls[i].setHeight(100);
      walls[i].setColor(Color.BLACK);
      walls[i].setSolid(true);  // prevent hero from going through walls
    }
    walls[0].setUpperLeft(new Point(0, 200));
    walls[1].setUpperLeft(new Point(100, 400));

    // make finish line
    finish = new Rectangle();
    finish.setUpperLeft(new Point(100, 100));
    finish.setWidth(10);
    finish.setHeight(100);
    finish.setColor(Color.GREEN);

    // make hero
    hero = new ExampleCircle();

    // prevent hero from leaving window
    Game.setBorderBehavior(Game.BorderBehavior.SOLID);
  }

  @Override
  public void update() {
    // celebrate if finished
    if (hero.isTouching(finish)) {
      TextStyle titleStyle = new TextStyle("Times New Roman", 60, Color.GREEN);
      Game.setTitleStyle(titleStyle);
      Game.setTitle("You win!", 1000000);
    }
  }

  public static void main(String[] args) {
    new ExampleGame();
  }

  public ExampleGame() {
    super(false);
    setup();
    ready();
  }
}
