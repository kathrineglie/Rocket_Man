package inf112.rocketman.model.powerups;

import com.badlogic.gdx.math.Rectangle;
import inf112.rocketman.model.Velocity;
import inf112.rocketman.model.obstacles.Obstacle;

/**
 * Represents a power-up obstacle in the game.
 *
 * <p>A power-up has a specific type that determines its effect when collected.</p>
 */
public class PowerUp extends Obstacle {
    private final PowerUpType type;

    /**
     * Creates a new power-up
     *
     * @param bounds the bounds of the coin
     * @param velocity the velocity of the powerup box
     * @param ground the ground of the player and obstacles
     * @param type the type of the power-up
     */
    public PowerUp(Rectangle bounds, Velocity velocity, float ground, PowerUpType type) {
        super(bounds, velocity, ground);
        this.type = type;
    }

    /**
     * Returns the type of this power-up
     *
     * @return the power-up type
     */
    public PowerUpType getType() {
        return type;
    }
}