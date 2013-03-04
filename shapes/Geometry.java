package shapes;

import static java.lang.Math.*;

public abstract class Geometry {

  public static final double EPSILON = 0.5;

  public static boolean touching(Shape a, Shape b) {
    if (a instanceof Circle && b instanceof Circle) {
      Circle c1 = (Circle)a;
      Circle c2 = (Circle)b;
      double distance = distance(c1.getCenter(), c2.getCenter()) -
                        c1.getRadius() - c2.getRadius();
      return distance < EPSILON;
    } else {
      return false;
    }
  }

  public static double distance(Point a, Point b) {
    return sqrt(sq(a.getX() - b.getX()) + sq(a.getY() - b.getY()));
  }

  public static double sq(double x) {
    return x*x;
  }
}
