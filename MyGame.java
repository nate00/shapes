public class MyGame extends Game {
  private Circle circle;

  @Override
  public void init() {
    circle = new MyCircle();
    getCanvas().addShape(circle);
  }

  @Override
  public void update() {
    circle.update();
  }

  public static void main(String[] args) {
    new MyGame();
  }
}
