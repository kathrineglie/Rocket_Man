package inf112.rocketman.model.obstacles;

import com.badlogic.gdx.math.Rectangle;

/**
 * Represents a general obstacle in the game world.
 *
 * <p>An obstacle has a position, size, velocity, and a rectangular  htibox
 * used for collision detection.</p>
 */
public class Obstacle implements IObstacle {
    protected float x, y;
    protected float width, height;
    protected float vx, vy; // the velocity of the object
    protected float HITBOX_OFFSET = 3;
    protected float GROUND = 170f;

    protected Rectangle hitbox = new Rectangle();

    /**
     * Creates a new obstacle with the given position, size, and velocity.
     *
     * @param x the x-coordinate of the obstacle
     * @param y the y-coordinate of the obstacle
     * @param width the width of the obstacle
     * @param height the height of the obstacle
     * @param vx the horizontal velocity
     * @param vy the vertical velocity
     */
    protected Obstacle (float x, float y,
                     float width, float height,
                     float vx, float vy) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.vx = vx;
        this.vy = vy;

        this.hitbox.set(x + HITBOX_OFFSET,
                y + HITBOX_OFFSET,
                width - 2*HITBOX_OFFSET,
                height - 2*HITBOX_OFFSET);
    }

    //
    @Override
    public void update(float dt) {
        x += vx * dt;
        y += vy * dt;

        hitbox.set(x + HITBOX_OFFSET,
                y + HITBOX_OFFSET,
                width - 2*HITBOX_OFFSET,
                height - 2*HITBOX_OFFSET);
    }

    @Override
    public Rectangle getHitBox() {
        return hitbox;
    }

    @Override
    public boolean isOfScreen(float worldWidth, float worldHeight) {
        return (x + width < 0 || y + height < GROUND || y > worldHeight);
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
