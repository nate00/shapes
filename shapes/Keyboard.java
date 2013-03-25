package shapes;

import java.awt.event.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * Captures input from the keyboard. <code>Keyboard</code>, along with
 * {@link Mouse}, is how you make your game interactive.
 * <p>
 * For example, to make a shape move according to the arrow keys, you would
 * write:
 * <p>
 * <code>shape.move(Keyboard.direction(), 10);</code>
 */
public class Keyboard extends KeyAdapter {
  private static Set<KeyEvent> keysPressed;
  private static KeyEvent mostRecentKeyPressed;

  public Keyboard() {
    if (keysPressed == null) {
      keysPressed =
        Collections.newSetFromMap(new ConcurrentHashMap<KeyEvent, Boolean>());
    }
  }

  public static boolean keyIsPressed(String key) {
    for (KeyEvent event : keysPressed) {
      if (key.equals(KeyEvent.getKeyText(event.getKeyCode()))) {
        return true;
      }
    }
    return false;
  }

  public static String[] keys() {
    Iterator<KeyEvent> keyEventIterator = keysPressed.iterator();
    String[] keys = new String[keysPressed.size()];
    for (int i = 0; i < keys.length && keyEventIterator.hasNext(); i++) {
      keys[i] = KeyEvent.getKeyText(keyEventIterator.next().getKeyCode());
    }

    return keys;
  }

  public static String key() {
    if (mostRecentKeyPressed == null) {
      return null;
    }

    return KeyEvent.getKeyText(mostRecentKeyPressed.getKeyCode());
  }

  public static Direction direction() {
    return direction(KeySet.ARROWS);
  }

  // returns null if no arrows are pressed, or if 3+ arrows are pressed, or if
  // opposing keys are pressed
  public static Direction direction(KeySet set) {
    ArrayList<Vector> vectorsPressed = new ArrayList<Vector>();
    for (KeyEvent keyPressed : keysPressed) {
      Vector vectorPressed = set.getVector(keyPressed);
      if (vectorPressed != null) {
        vectorsPressed.add(vectorPressed);
      }
    }

    if (vectorsPressed.size() == 0) {
      return null;
    }
    Vector ret = vectorsPressed.get(0);
    for (int i = 1; i < vectorsPressed.size(); i++) {
      ret = ret.add(vectorsPressed.get(i));
    }
    if (Math.abs(ret.getMagnitude()) < Geometry.EPSILON) {
      return null;
    }
    return ret.getDirection();
  }

  @Override
  public void keyPressed(KeyEvent keyPressed) {
    keysPressed.add(keyPressed);
    mostRecentKeyPressed = keyPressed;
  }

  @Override
  public void keyReleased(KeyEvent keyReleased) {
    // remove from keysPressed
    Iterator<KeyEvent> iter = keysPressed.iterator();
    while (iter.hasNext()) {
      if (iter.next().getKeyCode() == keyReleased.getKeyCode()) {
        iter.remove();
      }
    }

    if (mostRecentKeyPressed != null &&
        mostRecentKeyPressed.getKeyCode() == keyReleased.getKeyCode()) {
      mostRecentKeyPressed = null;
    }
  }
}
