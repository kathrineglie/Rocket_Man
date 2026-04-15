package inf112.rocketman.model.Coins;

import com.badlogic.gdx.math.Rectangle;

/**
 * Represents a coin in the game world.
 *
 * <p>A coin has a position, size, velocity, and a hitbox used for collision detection.</p>
 */
public class Coin {
    private Rectangle hitbox;
    private float x, y, width, height, vx;

    protected Coin(float x, float y, float width, float height, float vx) {
        this.hitbox = new Rectangle(x,y,width,height);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.vx = vx;
    }

    /**
     * Updates the coin's position based on its horizontal velocity.
     *
     * @param dt the time elapsed since the last frame
     */
    public void update(float dt) {
        x += vx * dt;
        hitbox.set(x,y,width,height);
    }

    /**
     * Returns the hitbox used for collision detection.
     *
     * @return the coin's hitbox
     */
    public Rectangle getHitbox() {
        return hitbox;
    }

    /**
     * Checks whether the coin is outside the visible game area.
     *
     * @param worldHeight the height of the game world
     * @param margin the allowed margin around the world
     * @return true if the coin is outside the allowed area, false otherwise
     */
    public boolean isOfScreen(float worldHeight, float margin) {
        return (x + width < margin || y < margin || y + height > worldHeight - margin);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void setVX(float bgSpeed) {this.vx = bgSpeed; }
}
