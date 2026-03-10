package inf112.rocketman.model.Character;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;

public class TPowah {
    private final Rectangle bounds;
    private float vy = 0;
    private float groundY = 120f;
    private static final int HITBOX_OFFSET = 10;

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

    /**
     * The actual size of the character
     *
     * @return
     */
    public Rectangle getBounds() {
        return bounds;
    }

    /**
     * Returns the hitbox for the character which is a bit smaller due to hitbox offset
     *
     * @return
     */
    public Rectangle getHitBox() {
        return new Rectangle(bounds.getX() + HITBOX_OFFSET,
                bounds.getY() + HITBOX_OFFSET,
                bounds.getWidth() - HITBOX_OFFSET*2,
                bounds.getHeight() - HITBOX_OFFSET*2);
    }

    /**
     * Returns the hitbox as a polygon
     *
     * @return the hitbox for the player as a polygon
     */
    public Polygon getPolyHitBox() {
        float[] vertices = new float[] {
                0, 0,
                0, bounds.height,
                bounds.width, bounds.height,
                bounds.width, 0
        };

        Polygon polygon = new Polygon(vertices);
        polygon.setPosition(bounds.x, bounds.y);
        return polygon;
    }

    public float getY() {return bounds.y;}

    public float getX() {return bounds.x;}

    public float getWidth() {return bounds.width;}

    public float getHeight() {return bounds.height; }

    public void setX(float x) {this.bounds.x = x; }

    public void setY(float y) {this.bounds.y = y; }

    public void setVy(float vy) {this.vy = vy;}

}
