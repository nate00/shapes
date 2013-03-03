public class Test {
  public static void main(String[] args) {
    Keyboard k = new Keyboard();
    while (true) {
      String key = k.getKey();
      if (key != null) {
        System.out.println(" * " + Keyboard.getKey() + " * ");
      }
    }
  }

  public static void vectorGetXTest(Vector v) {
    System.out.println("Degrees: " + v.getDirection().getDegrees() + " Magnitude: " + v.getMagnitude() + " X: " + v.getX() + " Y: " + v.getY());
  }

  public static void setDegreesTest(double degrees) {
    Direction d = new Direction(degrees);
    System.out.println(degrees + " " + d.getDegrees());
  }
}
