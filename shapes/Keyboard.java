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
  private static boolean printKeyPresses = false;

  public Keyboard() {
    if (keysPressed == null) {
      keysPressed =
        Collections.newSetFromMap(new ConcurrentHashMap<KeyEvent, Boolean>());
    }
  }

  /**
   * Print keypresses to standard output. Most of the methods in this class use
   * strings to represent keys, so this method can be used to determine which
   * strings represent which keys.
   * <p>
   * Here are some representative examples:
   * <ul>
   *  <li>
   *    The up arrow key: <code>"Up"</code>
   *  </li>
   *  <li>
   *    The shift key: <code>"Shift"</code>
   *  </li>
   *  <li>
   *    The space bar: <code>"Space"</code>
   *  </li>
   *  <li>
   *    The letter a: <code>"A"</code>
   *  </li>
   * </ul>
   *
   * @param printKeyPresses passing <code>true</code> prints all key presses.
   */
  public static void printKeyPresses(boolean printKeyPresses) {
    Keyboard.printKeyPresses = printKeyPresses;
  }

  /**
   * Returns true if the given key is being pressed.
   *
   * @param key string representing the key whose presses to detect. (See
   *            {@link #printKeyPresses} to find which strings represent
   *            which keys.)
   * @return    true if the key is being pressed, false otherwise.
   */
  public static boolean keyIsPressed(String key) {
    for (KeyEvent event : keysPressed) {
      if (key.equals(KeyEvent.getKeyText(event.getKeyCode()))) {
        return true;
      }
    }
    return false;
  }

  /**
   * Returns an array of strings representing all keys currently being pressed.
   * Returns an empty array if no keys are being pressed. See
   * {@link #printKeyPresses} to find which strings represent which keys.
   *
   * @return  an array of strings representing all keys currently being pressed.
   */
  public static String[] keys() {
    Iterator<KeyEvent> keyEventIterator = keysPressed.iterator();
    String[] keys = new String[keysPressed.size()];
    for (int i = 0; i < keys.length && keyEventIterator.hasNext(); i++) {
      keys[i] = keyToString(keyEventIterator.next());
    }

    return keys;
  }

  /**
   * Returns a string representing the most recently pressed key. Returns null
   * if no key is currently being pressed. See {@link #printKeyPresses} to find
   * which strings represent which keys.
   *
   * @return  a string representing the most recently pressed key.
   */
  public static String key() {
    if (mostRecentKeyPressed == null) {
      return null;
    }

    return keyToString(mostRecentKeyPressed);
  }

  /**
   * Returns the direction currently being pressed on the arrow keys. Returns
   * null if no direction is being pressed (or if the directions being pressed
   * cancel each other out, like left and right).
   *
   * @return  a <code>Direction</code> object representing the direction being
   *          pressed on the arrow keys.
   */
  public static Direction direction() {
    return direction(KeySet.ARROWS);
  }

  /**
   * Returns the direction currently being pressed on the given key set.
   * Returns null if no direction is being pressed (or if the directions being
   * pressed cancel each other out, like left and right).
   *
   * @param set the set of keys to get directions from, either
   *            {@link KeySet#ARROWS} or {@link KeySet#WASD}.
   * @return    a <code>Direction</code> object representing the direction
   *            being pressed on the given key set.
   */
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

  /**
   * You can ignore this method. This method gets called whenever a key is
   * pressed.
   */
  @Override
  public void keyPressed(KeyEvent keyPressed) {
    keysPressed.add(keyPressed);
    mostRecentKeyPressed = keyPressed;
    if (printKeyPresses) {
      System.out.println(keyToString(keyPressed));
    }
  }

  /**
   * You can ignore this method. This method gets called whenever a key is
   * released.
   */
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

  private static String keyToString(KeyEvent key) {
    return KeyEvent.getKeyText(mostRecentKeyPressed.getKeyCode());
  }
}
