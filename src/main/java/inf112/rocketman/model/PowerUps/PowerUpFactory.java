package inf112.rocketman.model.PowerUps;

/**
 * Interface for classes that creates power-ups.
 */
public interface PowerUpFactory {

    /**
     * Creates a new power-up.
     *
     * @param worldWidth the width of the game world
     * @param worldHeight the height of the game world
     * @param ground the ground position
     * @param margin the spawn margin
     * @param bgSpeed the horizontal background speed
     * @return a new power-up
     */
    PowerUp newPowerUp(float worldWidth, float worldHeight, float ground, float margin, float bgSpeed);
}
