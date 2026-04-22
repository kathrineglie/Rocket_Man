package inf112.rocketman.model.character;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import inf112.rocketman.model.powerups.PowerUpType;

public interface ITPowah {

    /**
     * Updates the characters position depending on what powerup is active
     *
     * @param dt dt time between each frame
     * @param movementInput The controller input
     * @param worldHeight Height of the gameworld. So it is screen height minus the margin
     */
    void update(float dt, boolean movementInput, float worldHeight);

    /**
     * Gets the correct bounds for the character and is used to draw the character
     *
     * @return a rectangle that represents the bounds for the character
     */
    Rectangle getBounds();

    /**
     * Gets the current hitbox which is slightly smaller than the actual bounds of the character with an offset
     *
     * @return a Rectangle representing the hitbox for collisions for the character
     */
    Rectangle getHitBox();

    /**
     * Gets the current hitbox as a polygon and is slightly smaller than the actual bounds of the character with an offset
     *
     * @return a polygon that is being used to check collision with other polygon obstacles
     */
    Polygon getPolyHitBox();

    /**
     * Sets the power up state of the character to Normal or a specific powerup.
     *
     * @param powerUp The name of the powerup you want to set to the character
     */
    void setPowerUp(PowerUpType powerUp);

    /**
     * Gets the current acive powerup for the player
     *
     * @return the current powerup
     */
    PowerUpType getActivePowerUp();

    /**
     * Checks if the current character has a powerup
     *
     * @return true if the character has a powerup that is not normal
     */
    boolean hasPowerUp();

    /**
     * Checks if the character is currently on ground
     *
     * @return true if the character is on ground
     */
    boolean onGround();

    /**
     * Checks if the character is going down
     *
     * @return true if the character is going down
     */
    boolean isGoingDown();

    /**
     * Checks if the character is going up
     *
     * @return true if the character is going up
     */
    boolean isGoingUp();

    /**
     * Gets the current X value for the actual bounds and not the hitbox
     * @return x cooridnate of the character
     */
    float getX();

    /**
     * Gets the current Y value for the actual bounds and not the hitbox
     *
     * @return y coordinate of the character
     */
    float getY();

    /**
     * Gets the current width for the actual bounds and not the hitbox
     *
     * @return width of the character
     */
    float getWidth();

    /**
     * Gets the current height for the actual bounds and not the hitbox
     *
     * @return height of the character
     */
    float getHeight();

    /**
     * Gets the vertical velocity of the character
     *
     * @return vertical velocity
     */
    float getVY();

    /**
     * Sets the bound-value x to a new value x
     * @param x the new x-value for the bounds
     */
    void setX(float x);
    /**
     * Sets the bound-value y to a new value y
     * @param y the new y-value for the bounds
     */
    void setY(float y);
    /**
     * Sets the vy value for the character to a new value
     * @param vy the new vy-value
     */
    void setVy(float vy);

}

