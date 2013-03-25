package shapes;

import java.awt.*;
import java.awt.event.*;

public class Mouse implements MouseMotionListener, MouseListener {
  private static Point click;
  
  // will be null when mouse is not down
  public static Point clickLocation() {
    return click;
  }

  public void mousePressed(MouseEvent event) {
    updateClick(event);
  }

  private void updateClick(MouseEvent event) {
    click = new Point(
      Geometry.fromCanvasX(event.getX()),
      Geometry.fromCanvasY(event.getY())
    );
  }

  public void mouseDragged(MouseEvent event) {
    updateClick(event);
  }

  public void mouseReleased(MouseEvent event) {
    click = null;
  }

  public void mouseMoved(MouseEvent event) {}
  public void mouseExited(MouseEvent event) {}
  public void mouseEntered(MouseEvent event) {}
  public void mouseClicked(MouseEvent event) {}
}
