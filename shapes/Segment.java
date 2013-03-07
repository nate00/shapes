package shapes;

class Segment {
  Point start, end;

  Segment(Point start, Point end) {
    setEndpoints(start, end);
  }

  void setEndpoints(Point start, Point end) {
    this.start = start;
    this.end = end;
  }

  double slope() {
    return Geometry.slope(start, end);
  }

  double length() {
    return Geometry.distance(start, end);
  }
}

