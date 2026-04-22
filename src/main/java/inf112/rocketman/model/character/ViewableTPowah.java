package inf112.rocketman.model.character;

import com.badlogic.gdx.math.Rectangle;
import inf112.rocketman.model.powerups.PowerUpType;

public interface ViewableTPowah {

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
     * Gets the current hitbox which is slightly smaller than the actual bounds of the character with an offset
     *
     * @return a Rectangle representing the hitbox for collisions for the character
     */
    Rectangle getHitBox();

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
     * Gets the current movement input from the player
     * @return true if the player has pressed or is holding space
     */
    boolean getMovementInput();

    /**
     * Checks if the player is on the ceiling
     *
     * @param worldHeight the height of the game
     * @return true if the player is on the ceiling
     */
    boolean onCeiling(float worldHeight);
}
