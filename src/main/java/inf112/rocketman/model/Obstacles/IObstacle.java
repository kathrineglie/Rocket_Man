package inf112.rocketman.model.Obstacles;

import com.badlogic.gdx.math.Rectangle;

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

    float getOffSet();

    float getX();

    float getY();

    float getWidth();

    float getHeight();

}
