import shapes.*;
import java.awt.Color;

public class ExampleGame extends Game {

  // Put variables here!
  Circle circle;
  Rectangle[] walls = new Rectangle[2];
  Rectangle finish;

  @Override
  public void setup() {
    // set up the circle
    circle = new Circle();
    circle.setRadius(20.0);
    circle.setColor(Color.ORANGE);
    circle.setCenter(new Point(750.0, 450.0));

    // prevent shapes from leaving the game window
    Game.setBorderBehavior(Game.BorderBehavior.SOLID);

    // make walls
    for (int i = 0; i < walls.length; i = i + 1) {
      walls[i] = new Rectangle();
      walls[i].setWidth(700.0);
      walls[i].setHeight(100.0);
      walls[i].setColor(Color.BLACK);
      walls[i].setSolid(true);
    }
    walls[0].setUpperLeft(new Point(0.0, 200.0));
    walls[1].setUpperLeft(new Point(100.0, 400.0));

    // make finish line
    finish = new Rectangle();
    finish.setUpperLeft(new Point(100.0, 100.0));
    finish.setWidth(10.0);
    finish.setHeight(100.0);
    finish.setColor(Color.GREEN);
  }

  @Override
  public void update() {
    // move circle
    Direction movementDirection = Keyboard.direction();
    circle.move(movementDirection, 10.0);
    
    // celebrate if finished
    if (circle.isTouching(finish)) {
      // victory!
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
