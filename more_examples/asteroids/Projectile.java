import shapes.*;
import java.awt.Color;

public class Projectile extends Rectangle {
  private boolean launched;

  @Override
  public void setup() {
    setColor(Color.WHITE);
    setFilled(false);
    setWidth(10);
    setHeight(1);
    setLaunched(false);
  }

  @Override
  public void update() {
    if (this.isTouchingBorder()) {
      setLaunched(false);
    }
  }

  public boolean isLaunched() {
    return launched;
  }

  public void setLaunched(boolean l) {
    launched = l;
    if (launched) {
      setSpeed(7);
      setInvisible(false);
    } else {
      setSpeed(0);
      setInvisible(true);
      setCenter(new Point(-100, -100)); // to avoid collisions
    }
  }

  public Projectile() {
    super();
    setup();
  }
}
