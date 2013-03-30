package shapes;

import java.awt.*;
import java.awt.geom.*;

public class TextStyle {
  private String fontName;
  private int fontSize;
  private Color color;
  private boolean bold;
  private boolean italic;

  enum ReferencePointLocation { CENTER, BOTTOM_LEFT, TOP_LEFT };

  public TextStyle(String fontName, int fontSize, Color color) {
    setFontName(fontName);
    setFontSize(fontSize);
    setColor(color);
    setBold(bold);
    setItalic(italic);
  }

  void applyTo(Graphics2D g) {
    g.setColor(color);
    g.setFont(getFont());
  }

  Font getFont() {
    int style;
    if (!bold && !italic) {
      style = Font.PLAIN;
    } else {
      style = (bold ? Font.BOLD : 0) | (italic ? Font.ITALIC : 0);
    }
    
    return new Font(fontName, style, fontSize);
  }

  void renderString(
    String string,
    Graphics2D g,
    Point referencePoint,
    ReferencePointLocation referenceLocation
  ) {
    FontMetrics metrics = g.getFontMetrics(getFont());
    double width = metrics.stringWidth(string);
    // getAscent() includes room for accents, etc., so we shrink it by
    // an arbitrary amount
    double height = metrics.getAscent() * 0.8;
    Vector offset = null;
    switch (referenceLocation) {
      case CENTER:
        offset = new Vector(width / -2.0, height / -2.0);
        break;
      case BOTTOM_LEFT:
        offset = new Vector(0, 0);
        break;
      case TOP_LEFT:
        offset = new Vector(0, -1.0 * height);
        break;
    }
    Point bottomLeft = referencePoint.translation(offset);
    applyTo(g);
    g.drawString(string, bottomLeft.getCanvasX(), bottomLeft.getCanvasY());
  }

  public static TextStyle monospaced() {
    Font font = new Font(Font.MONOSPACED, Font.PLAIN, 16);
    return new TextStyle(font.getFontName(), 16, Color.BLACK);
  }

  public static TextStyle serif() {
    Font font = new Font(Font.SERIF, Font.PLAIN, 16);
    return new TextStyle(font.getFontName(), 16, Color.BLACK);
  }
  
  public static TextStyle sansSerif() {
    Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 16);
    return new TextStyle(font.getFontName(), 16, Color.BLACK);
  }
  
  /* Getters and setters */

  public void setFontName(String fontName) {
    this.fontName = fontName;
  }

  public String getFontName() {
    return fontName;
  }

  public void setFontSize(int fontSize) {
    this.fontSize = fontSize;
  }

  public int getFontSize() {
    return fontSize;
  }

  public void setColor(Color color) {
    this.color = color;
  }

  public Color getColor() {
    return color;
  }

  public void setBold(boolean bold) {
    this.bold = bold;
  }

  public boolean isBold() {
    return bold;
  }

  public void setItalic(boolean italic) {
    this.italic = italic;
  }

  public boolean isItalic() {
    return italic;
  }
}
