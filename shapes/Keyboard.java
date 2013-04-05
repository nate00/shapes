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
  private static Set<Integer> keysPressed;
  private static Integer mostRecentKeyPressed;
  private static boolean printKeyPresses = false;

  public Keyboard() {
    if (keysPressed == null) {
      keysPressed =
        Collections.newSetFromMap(new ConcurrentHashMap<Integer, Boolean>());
    }
  }

  /**
   * Print keypresses to standard output. Most of the methods in this class use
   * codes to represent keys, so this method can be used to determine which
   * codes represent which keys.
   * <p>
   * This class has constants equal to the codes for most common keys. For
   * example,
   * <code>boolean upIsPressed = Keyboard.keyIsPressed(Keyboard.UP);</code> can
   * be used to determine whether the up arrow is pressed.
   *
   * @param printKeyPresses passing <code>true</code> prints all key presses.
   */
  public static void printKeyPresses(boolean printKeyPresses) {
    Keyboard.printKeyPresses = printKeyPresses;
  }

  /**
   * Returns true if the given key is being pressed.
   *
   * @param keyCode integer representing the key whose presses to detect. (See
   *                {@link #printKeyPresses} to find which strings represent
   *                which keys.)
   * @return        true if the key is being pressed, false otherwise.
   */
  public static boolean keyIsPressed(int keyCode) {
    for (int pressedCode : keysPressed) {
      if (keyCode == pressedCode) {
        return true;
      }
    }
    return false;
  }

  /**
   * Returns an array of integers representing all keys currently being pressed.
   * Returns an empty array if no keys are being pressed. See
   * {@link #printKeyPresses} to find which codes represent which keys.
   *
   * @return  an array of integers representing all keys currently being pressed.
   */
  public static int[] keys() {
    Iterator<Integer> keyPressedIterator = keysPressed.iterator();
    int[] keys = new int[keysPressed.size()];
    for (int i = 0; i < keys.length && keyPressedIterator.hasNext(); i++) {
      keys[i] = keyPressedIterator.next();
    }

    return keys;
  }

  /**
   * Returns an integer code representing the most recently pressed key.
   * Returns <code>Keyboard.NO_KEY</code>
   * if no key is currently being pressed. See {@link #printKeyPresses} to find
   * which integer codes represent which keys.
   *
   * @return  an integer code representing the most recently pressed key.
   */
  public static int key() {
    return mostRecentKeyPressed;
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
    if (set == null) {
      throw new IllegalArgumentException("set must not be null.");
    }
    ArrayList<Vector> vectorsPressed = new ArrayList<Vector>();
    for (int keyPressed : keysPressed) {
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
  public void keyPressed(KeyEvent keyPressedEvent) {
    keysPressed.add(keyPressedEvent.getKeyCode());
    mostRecentKeyPressed = keyPressedEvent.getKeyCode();
    if (printKeyPresses) {
      System.out.println(keyPressedEvent.getKeyCode());
    }
  }

  /**
   * You can ignore this method. This method gets called whenever a key is
   * released.
   */
  @Override
  public void keyReleased(KeyEvent keyReleasedEvent) {
    // remove from keysPressed
    Iterator<Integer> iter = keysPressed.iterator();
    while (iter.hasNext()) {
      if (iter.next() == keyReleasedEvent.getKeyCode()) {
        iter.remove();
      }
    }

    if (mostRecentKeyPressed == keyReleasedEvent.getKeyCode()) {
      mostRecentKeyPressed = NO_KEY;
    }
  }

  public final static int A = 65;
  public final static int B = 66;
  public final static int C = 67;
  public final static int D = 68;
  public final static int E = 69;
  public final static int F = 70;
  public final static int G = 71;
  public final static int H = 72;
  public final static int I = 73;
  public final static int J = 74;
  public final static int K = 75;
  public final static int L = 76;
  public final static int M = 77;
  public final static int N = 78;
  public final static int O = 79;
  public final static int P = 80;
  public final static int Q = 81;
  public final static int R = 82;
  public final static int S = 83;
  public final static int T = 84;
  public final static int U = 85;
  public final static int V = 86;
  public final static int W = 87;
  public final static int X = 88;
  public final static int Y = 89;
  public final static int Z = 90;

  public final static int NUMBER_0 = 48;
  public final static int NUMBER_1 = 49;
  public final static int NUMBER_2 = 50;
  public final static int NUMBER_3 = 51;
  public final static int NUMBER_4 = 52;
  public final static int NUMBER_5 = 53;
  public final static int NUMBER_6 = 54;
  public final static int NUMBER_7 = 55;
  public final static int NUMBER_8 = 56;
  public final static int NUMBER_9 = 57;

  public final static int LEFT = 37;
  public final static int UP = 38;
  public final static int RIGHT = 39;
  public final static int DOWN = 40;

  public final static int SPACE = 32;
  public final static int ENTER = 10;
  public final static int ESC = 27;
  public final static int SHIFT = 16;
  public final static int BACKSPACE = 8;

  public final static int NO_KEY = -1;
}
