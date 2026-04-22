package inf112.rocketman.model.obstacles;

import com.badlogic.gdx.math.Rectangle;

/**
 * Interface for obstacles in the game world.
 *
 * <p>An obstacle has a position, size, velocity, and a hitbox,
 * and can be updated and checked against the screen bounds.</p>
 */
public interface IObstacle {

    /**
     * Updates the frame
     *
     * @param dt the time between the last update (delta_time)
     */
    void update(float dt);

    /**
     * Gets the current rectangle of the obstacle
     *
     * @return The rectangle/hitbox of the specified obstacle
     */
    Rectangle getHitBox();

    /**
     * Checks if the obstacle is currently on the screen or not.
     *
     * @param worldWidth width of the game
     * @param worldHeight height of the game
     * @return true if the obstacle is on screen and false if it is off screen
     */
    boolean isOfScreen(float worldWidth, float worldHeight);

    /**
     * Returns the obstacle offset.
     *
     * @return the obstacle offset
     */
    float getOffSet();

    /**
     * Returns the x-coordinate of the obstacle.
     *
     * @return the x-coordinate
     */
    float getX();

    /**
     * Returns the y-coordinate of the obstacle.
     *
     * @return the y-coordinate
     */
    float getY();

    /**
     * Returns the width of the obstacle.
     *
     * @return the obstacle width
     */
    float getWidth();

    /**
     * Returns the height of the obstacle.
     *
     * @return the obstacle height
     */
    float getHeight();

    /**
     * Returns the horizontal velocity of the obstacle.
     *
     * @return the horizontal velocity
     */
    float getVX();

    /**
     * Sets the horizontal velocity of the obstacle.
     *
     * @param bgSpeed the new horizontal velocity
     */
    void setVX(float bgSpeed);

}
