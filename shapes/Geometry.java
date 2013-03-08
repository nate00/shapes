package shapes;

import static java.lang.Math.*;

abstract class Geometry {

  // used for general double arithmetic
  static final double EPSILON = 0.001;
  // used for judging closeness (0.5 pixels apart == touching)
  static final double TOLERANCE = 0.5;

  static boolean touching(Shape s, Shape t) {
    double distance = distance(s, t);
    if (distance == Double.NaN) return false;
    return distance < TOLERANCE;
  }

  // Returns NaN if the code for the given shape pair hasn't been written yet.
  static double distance(Shape s, Shape t) {
    if (s instanceof Circle && t instanceof Circle) {
      Circle c1 = (Circle)s;
      Circle c2 = (Circle)t;
      double distance = distance(c1.getCenter(), c2.getCenter()) -
                        c1.getRadius() - c2.getRadius();
      return distance;
    } else {
      return Double.NaN;
    }
  }

  // returns null for parallel lines
  static Point lineIntersection(Segment s, Segment t) {
    double denominator = s.getA() * t.getB() - s.getB() * t.getA();
    if (Math.abs(denominator) < EPSILON) {
      return null;
    }

    double x = (s.getC() * t.getB() - s.getB() * t.getC()) / denominator;
    double y = (s.getA() * t.getC() - s.getC() * t.getA()) / denominator;
    return new Point(x, y);
  }

  static Point segmentIntersection(Segment s, Segment t) {
    Point intersection = lineIntersection(s, t);
    if (intersection == null) {
      return null;
    }
    if (s.contains(intersection) && t.contains(intersection)) {
      return intersection;
    } else {
      return null;
    }
  }

  // Returns a segment perpendicular to the original segment, starting at the
  // given point and ending on the segment (or corresponding line).
  static Segment perpendicularThrough(Segment original, Point through) {
    // perp is perpendicular and has the correct start, but does not have the
    // correct end
    Segment perp = new Segment(
      through,
      through.translation(original.vector().perpendicular())
    );

    Point intersection = lineIntersection(original, perp);

    return new Segment(through, intersection);
  }

  // if mover wants to go to end, but solid is in the way, how far can it go?
  static Point maxMovement(Circle mover, Point end, Circle solid) {
    Segment path = new Segment(mover.getCenter(), end);
    Segment solidToPath = perpendicularThrough(path, solid.getCenter());
    double distanceToPath = solidToPath.length();
    double distanceBetweenCenters = mover.getRadius() + solid.getRadius();

    if (distanceToPath > distanceBetweenCenters) {
      return end;   // no collision
    }

    // closest is the point on path closest to solid
    // stopPoint is where the center of mover will be after moving
    Point closest = solidToPath.getEnd();
    double distanceToStopPoint =
      sqrt(sq(distanceBetweenCenters) - sq(distanceToPath));
    Point stopPoint =
      closest.translation(path.direction().reverse(), distanceToStopPoint);
    return stopPoint;
  }

  // NOTE: this returns NaN if the points are horizontally aligned
  static double slope(Point s, Point t) {
    return (s.getY() - t.getY()) / (s.getX() - t.getX());
  }

  static double distance(Point s, Point t) {
    return sqrt(sq(s.getX() - t.getX()) + sq(s.getY() - t.getY()));
  }

  static double sq(double x) {
    return x*x;
  }
}
