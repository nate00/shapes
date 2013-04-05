package shapes;

import java.awt.event.*;

/**
 * Used in calls to {@link Keyboard#direction(KeySet)} to specify which
 * direction keys to listen to. Either the arrow keys or WASD.
 */
public enum KeySet {
  ARROWS(Keyboard.UP, Keyboard.DOWN, Keyboard.LEFT, Keyboard.RIGHT),
  WASD(Keyboard.W, Keyboard.S, Keyboard.A, Keyboard.D);

  public int UP, DOWN, LEFT, RIGHT;

  private KeySet(int up, int down, int left, int right) {
    UP = up;
    DOWN = down;
    LEFT = left;
    RIGHT = right;
  }

  public Vector getVector(int keyCode) {
    if (keyCode == UP)
      return Vector.UP;
    if (keyCode == DOWN)
      return Vector.DOWN;
    if (keyCode == LEFT)
      return Vector.LEFT;
    if (keyCode == RIGHT)
      return Vector.RIGHT;

    return null;
  }
}
