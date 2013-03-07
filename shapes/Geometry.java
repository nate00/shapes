package shapes;

import static java.lang.Math.*;

abstract class Geometry {

  static final double EPSILON = 0.5;

  static boolean touching(Shape a, Shape b) {
    double distance = distance(a, b);
    if (distance == Double.NaN) return false;
    return distance < EPSILON;
  }

  // Returns NaN if the code for the given shape pair hasn't been written yet.
  static double distance(Shape a, Shape b) {
    if (a instanceof Circle && b instanceof Circle) {
      Circle c1 = (Circle)a;
      Circle c2 = (Circle)b;
      double distance = distance(c1.getCenter(), c2.getCenter()) -
                        c1.getRadius() - c2.getRadius();
      return distance;
    } else {
      return Double.NaN;
    }
  }

  // Returns a segment perpendicular to the original segment, starting at the
  // given point and ending on the segment (or corresponding line).
  static Segment perpendicularThrough(Segment original, Point through) {
    
  }

  static double slope(Point a, Point b) {
    return (a.getY() - b.getY()) / (a.getX() - b.getX());
  }

  static double distance(Point a, Point b) {
    return sqrt(sq(a.getX() - b.getX()) + sq(a.getY() - b.getY()));
  }

  static double sq(double x) {
    return x*x;
  }
}
