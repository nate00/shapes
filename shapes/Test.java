package shapes;

public class Test {
  public static void main(String[] args) {
    Segment[] segments = {
      new Segment(new Point(100, 0), new Point(0, 100)),
      new Segment(new Point(0, 0), new Point(100, 100)),
      new Segment(new Point(100, 100), new Point(100, 100)),
      new Segment(new Point(100, 0), new Point(100, 100)),
      new Segment(new Point(0, 100), new Point(100, 100)),
      new Segment(new Point(0, 0), new Point(100, 100)),

      new Segment(new Point(0, 50), new Point(100, 50)),
      new Segment(new Point(50, 0), new Point(50, 100)),
      new Segment(new Point(20, 37), new Point(0, 400)),
      new Segment(new Point(0, 0), new Point(100, 100)),
    };
    for (int i = 0; i + 1 < segments.length; i++) {
      intersectionTest(segments[i], segments[i+1]);
    }
  }

  public static void intersectionTest(Segment s, Segment t) {
    System.out.println("FIRST SEGMENT: " + s.toString());
    System.out.println("SECOND SEGMENT: " + t.toString());
    System.out.println("INTERSECTION: " + Geometry.lineIntersection(s, t));
    System.out.println("INTERSECTION: " + Geometry.segmentIntersection(s, t));
    System.out.println("--------------------------------------");
  }

  public static void setDegreesTest(double degrees) {
    Direction d = new Direction(degrees);
    System.out.println(degrees + " " + d.getDegrees());
  }
}
