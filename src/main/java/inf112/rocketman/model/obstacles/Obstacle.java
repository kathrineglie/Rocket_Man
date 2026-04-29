package inf112.rocketman.model.obstacles;

import com.badlogic.gdx.math.Rectangle;
import inf112.rocketman.model.Velocity;
import inf112.rocketman.model.WorldDimensions;

/**
 * Represents a general obstacle in the game world.
 *
 * <p>An obstacle has a position, size, velocity, and a rectangular  hitbox
 * used for collision detection.</p>
 */
public class Obstacle implements IObstacle {
    protected float x;
    protected float y;
    protected float width;
    protected float height;
    protected float vx;
    protected float vy; // the velocity of the object
    protected static final float HITBOX_OFFSET = 3;
    protected float ground;

    protected Rectangle hitbox = new Rectangle();

    /**
     * Creates a new obstacle with the given position, size, and velocity.
     *
     * @param bounds the bounds for the obstacle
     * @param velocity the velocity of the obstacle
     */
    protected Obstacle (Rectangle bounds, Velocity velocity, float ground) {
        this.x = bounds.getX();
        this.y = bounds.getY();
        this.width = bounds.getWidth();
        this.height = bounds.getHeight();
        this.vx = velocity.vx();
        this.vy = velocity.vy();
        this.ground = ground;

        updateHitbox();

        this.hitbox.set(x + HITBOX_OFFSET,
                y + HITBOX_OFFSET,
                width - 2*HITBOX_OFFSET,
                height - 2*HITBOX_OFFSET);
    }

    protected void updateHitbox() {
        this.hitbox.set(x + HITBOX_OFFSET,
                y + HITBOX_OFFSET,
                width - 2*HITBOX_OFFSET,
                height - 2*HITBOX_OFFSET);
    }

    @Override
    public void update(float dt) {
        x += vx * dt;
        y += vy * dt;

        updateHitbox();
    }

    @Override
    public Rectangle getHitBox() {
        return hitbox;
    }

    @Override
    public boolean isOfScreen(WorldDimensions dimensions) {
        return (x + width < 0 || y + height < ground || y > dimensions.worldHeight());
    }

    @Override
    public float getOffSet() {
        return HITBOX_OFFSET;
    }

    /**
     * Returns the x-coordinate of the obstacle.
     *
     * @return the x-coordinate
     */
    public float getX() {
        return x;
    }

    /**
     * Returns the y-coordinate of the obstacle.
     *
     * @return the y-coordinate
     */
    public float getY() {
        return y;
    }

    /**
     * Returns the width of the obstacle.
     *
     * @return the obstacle width
     */
    public float getWidth() {
        return width;
    }

    /**
     * Returns the height of the obstacle.
     *
     * @return the obstacle height
     */
    public float getHeight() {
        return height;
    }

    /**
     * Returns the horizontal velocity of the obstacle.
     *
     * @return the horizontal velocity
     */
    public float getVX() {return vx; }

    @Override
    public void setVX(float bgSpeed) {
        vx = bgSpeed;
    }
}
