package shapes;

import java.awt.*;

public abstract class ConvexPolygon extends Shape {

  protected boolean displaysRotation;
  abstract Point[] getUnrotatedCorners();

  public Point[] getCorners() {
    Point[] corners = getUnrotatedCorners();
    if (
      !displaysRotation ||
      direction == null ||
      Math.abs(direction.getRadians()) < Geometry.EPSILON
    ) {
      return corners;
    }

    double angle = direction.getRadians();
    for (int i = 0; i < corners.length; i++) {
      double xOffset = corners[i].getX() - center.getX();
      double yOffset = corners[i].getY() - center.getY();
      corners[i] = new Point(
        center.getX() + xOffset * Math.cos(angle) - yOffset * Math.sin(angle),
        center.getY() + xOffset * Math.sin(angle) + yOffset * Math.cos(angle)
      );
    }

    return corners;
  }

  Segment[] getSides() {
    Point[] corners = getCorners();
    Segment[] sides = new Segment[corners.length];
    for (int i = 0; i < corners.length; i++) {
      sides[i] = new Segment(corners[i], corners[(i + 1) % corners.length]);
    }
    return sides;
  }


  Point maxMovement(Point target, Shape obstacle) {
    return Geometry.maxMovement(this, target, obstacle);
  }

  public boolean isOffscreen() {
    for (Point corner : getCorners()) {
      if (!Geometry.offscreen(corner)) {
        return false;
      }
    }
    return true;
  }

  public boolean contains(Shape shape) {
    if (shape instanceof ConvexPolygon) {
      Point[] corners = ((ConvexPolygon) shape).getCorners();
      for (Point corner : corners) {
        if (!this.contains(corner)) {
          return false;
        }
      }
      return true;
    } else if (shape instanceof Circle) {
      Circle circle = (Circle) shape;
      if (!this.contains(circle.getCenter())) {
        return false;
      }
      for (Segment side : getSides()) {
        double distance =
          Geometry.perpendicularThrough(side, circle.getCenter()).length();
        if (distance < circle.getRadius()) {
          return false;
        }
      }
      return true;
    }
    return false;
  }

  public boolean contains(Point p) {
    // the point is inside if all cross products have the same sign
    boolean positive = false;
    boolean first = true;
    for (Segment side : getSides()) {
      boolean pos =
        Geometry.cross(side.vector(), new Vector(side.getStart(), p)) > 0;
      if (first) {
        positive = pos;
        first = false;
        continue;
      }
      if (pos != positive) {
        return false;
      }
    }
    return true;
  }

  private void renderSpeech(Graphics2D g) {
    // TODO
  }

  void render(Graphics2D g) {
    renderSpeech(g);
    if (this.isInvisible()) {
      return;
    }

    g.setColor(getColor());
    Point[] corners = getCorners();
    int[] x = new int[corners.length];
    int[] y = new int[corners.length];
    for (int i = 0; i < corners.length; i++) {
      x[i] = (int)corners[i].getCanvasX();
      y[i] = (int)corners[i].getCanvasY();
    }
    if (this.isFilled()) {
      g.fillPolygon(x, y, corners.length);
    } else {
      g.drawPolygon(x, y, corners.length);
    }
  }

  public void setDisplaysRotation(boolean displaysRotation) {
    this.displaysRotation = displaysRotation;
  }

  public boolean displaysRotation() {
    return displaysRotation;
  }
}
