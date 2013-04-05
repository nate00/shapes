package shapes;

import java.awt.*;
import java.util.*;

public class Counter {
  private String name;
  private int value;
  private boolean destroyed = false;

  public Counter(String name, int initialValue) {
    if (name == null) {
      throw new IllegalArgumentException("name must not be null.");
    }
    this.name = name;
    this.value = initialValue;
    Game.addCounter(this);
  }

  public Counter() {
    this("Score", 0);
  }

  public void increaseBy(int amount) {
    value += amount;
  }

  public void decreaseBy(int amount) {
    value -= amount;
  }

  public void destroy() {
    destroyed = true;
    Game.removeCounter(this);
  }

  static void renderCounters(java.util.List<Counter> counters, TextStyle style, Graphics2D g) {
    Point windowTopLeft = new Point(0, Game.HEIGHT);
    Point countersTopLeft = windowTopLeft.translation(new Vector(10, -10));
    StringBuffer buf = new StringBuffer();
    Iterator<Counter> iter = counters.iterator();
    while (iter.hasNext()) {
      buf.append(iter.next().toString());
      if (iter.hasNext()) {
        buf.append('\n');
      }
    }
    style.renderString(
      buf.toString(),
      countersTopLeft,
      TextStyle.ReferencePointLocation.TOP_LEFT,
      g,
      null
    );
  }

  /* Getters and settings*/

  public void setName(String name) {
    if (name == null) {
      throw new IllegalArgumentException("name must not be null.");
    }
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setValue(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }

  public boolean isDestroyed() {
    return destroyed;
  }

  @Override
  public String toString() {
    return name + "  " + value;
  }
}
