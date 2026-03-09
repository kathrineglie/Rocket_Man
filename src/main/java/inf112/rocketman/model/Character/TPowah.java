package inf112.rocketman.model.Character;
import com.badlogic.gdx.math.Rectangle;

public class TPowah {
    private final Rectangle bounds;
    private float vy = 0;
    private float groundY = 120f;

    public TPowah (float x, float y, float width, float height) {

        this.bounds = new Rectangle(x, y, width, height);
        this.groundY = 170f;
    }

    public void update(float dt, boolean thrusting, float worldHeight, float thrust, float gravity, float maxVY) {
        float ay = gravity + (thrusting ? thrust : 0f);
        vy += ay * dt;

        if (vy > maxVY) vy = maxVY;
        if (vy < -maxVY) vy = -maxVY;

        bounds.y += vy * dt;

        if (bounds.y < groundY) {
            bounds.y = groundY;
            vy = 0;
        }
        if (bounds.y > worldHeight - bounds.height) {
            bounds.y = worldHeight - bounds.height;
            vy = 0;
        }
    }

    public Rectangle getBounds() {return bounds; }

    public float getY() {return bounds.y;}

    public float getX() {return bounds.x;}

    public float getWidth() {return bounds.width;}

    public float getHeight() {return bounds.height; }

    public void setX(float x) {this.bounds.x = x; }

    public void setY(float y) {this.bounds.y = y; }

    public void setVy(float vy) {this.vy = vy;}

}
