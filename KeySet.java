import java.awt.event.KeyEvent;

public enum KeySet {
  ARROWS(KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT),
  WASD(KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D);

  public int UP, DOWN, LEFT, RIGHT;

  private KeySet(int up, int down, int left, int right) {
    UP = up;
    DOWN = down;
    LEFT = left;
    RIGHT = right;
  }

  public Vector getVector(KeyEvent key) {
    int code = key.getKeyCode();
    if (code == UP)
      return Vector.UP;
    if (code == DOWN)
      return Vector.DOWN;
    if (code == LEFT)
      return Vector.LEFT;
    if (code == RIGHT)
      return Vector.RIGHT;

    return null;
  }
}
