package inf112.rocketman.model.TPowah;
import com.badlogic.gdx.math.Rectangle;

public class TPowah {
    private final Rectangle bounds;
    private float vy = 0;

    public TPowah (float x, float y, float width, float height) {
        this.bounds = new Rectangle(x, y, width, height);
    }

    public void update(float dt, boolean thrusting, float worldHeight, float thrust, float gravity, float maxVY) {
        float ay = gravity + (thrusting ? thrust : 0f);
        vy += ay * dt;

        if (vy > maxVY) vy = maxVY;
        if (vy < -maxVY) vy = -maxVY;

        bounds.y += vy * dt;

        // Bounds
        if (bounds.y < 0) {
            bounds.y = 0;
            vy = 0;
        }
        if (bounds.y > worldHeight - bounds.height) {
            bounds.y = worldHeight - bounds.height;
            vy = 0;
        }
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public float getY() {
        return bounds.y;
    }

    public float getX() {
        return bounds.x;
    }
}
