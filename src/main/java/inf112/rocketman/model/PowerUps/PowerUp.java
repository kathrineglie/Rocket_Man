package inf112.rocketman.model.PowerUps;

import inf112.rocketman.model.Obstacles.Obstacle;

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
     * @param x the x-coordinate of the power-up
     * @param y the y-coordinate of the power-up
     * @param width the width of the power-up
     * @param height the height of the power-up
     * @param vx the horizontal velocity
     * @param type the type of the power-up
     */
    public PowerUp(float x, float y, float width, float height, float vx, PowerUpType type) {
        super(x, y, width, height, vx, 0);
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