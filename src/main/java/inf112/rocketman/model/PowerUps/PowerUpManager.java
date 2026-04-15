package inf112.rocketman.model.PowerUps;

import com.badlogic.gdx.math.Rectangle;
import inf112.rocketman.model.Character.TPowah;
import inf112.rocketman.model.PowerUps.PowerUp;
import inf112.rocketman.model.PowerUps.PowerUpFactory;
import inf112.rocketman.model.PowerUps.PowerUpType;

import java.util.Random;

public class PowerUpManager {
    private static final float MIN_POWER_UP_SPAWN_INTERVAL = 8f;
    private static final float MAX_POWER_UP_SPAWN_INTERVAL = 30f;

    private final PowerUpFactory powerUpFactory;
    private final Random random = new Random();

    private PowerUp powerUp;
    private float powerUpTimer;
    private boolean collectedPowerUpThisFrame = false;

    public PowerUpManager(PowerUpFactory powerUpFactory) {
        this.powerUpFactory = powerUpFactory;
        this.powerUpTimer = getRandomPowerUpSpawnInterval();
    }

    /**
     * Updates the power-up system by counting down the spawn timer,
     * spawning a new power-up, and moving or removing the current one.
     *
     * @param dt the time since the last frame
     * @param player the player
     * @param worldWidth world width
     * @param worldHeight world height
     * @param ground ground level
     * @param margin screen margin
     * @param bgSpeed current background speed
     */
    public void update(float dt,
                       TPowah player,
                       float worldWidth,
                       float worldHeight,
                       float ground,
                       float margin,
                       float bgSpeed) {

        collectedPowerUpThisFrame = false;

        if (player.getActivePowerUp() != PowerUpType.NORMAL) {
            return;
        }

        powerUpTimer -= dt;

        if (powerUp == null && powerUpTimer <= 0f) {
            powerUp = powerUpFactory.newPowerUp(worldWidth, worldHeight, ground, margin, bgSpeed);
            powerUpTimer = getRandomPowerUpSpawnInterval();
        }

        if (powerUp != null) {
            powerUp.update(dt);
            powerUp.setVX(bgSpeed);

            if (powerUp.isOfScreen(worldWidth, worldHeight)) {
                powerUp = null;
            }
        }
    }

    /**
     * Checks if the player overlaps with the current power-up.
     *
     * @param player the player
     * @return true if a power-up was collected this frame
     */
    public boolean checkCollision(TPowah player) {
        if (powerUp == null) {
            return false;
        }

        Rectangle playerHitbox = player.getHitBox();
        if (playerHitbox.overlaps(powerUp.getHitBox())) {
            player.setPowerUp(powerUp.getType());
            player.setVy(0);
            collectedPowerUpThisFrame = true;
            powerUp = null;
            powerUpTimer = getRandomPowerUpSpawnInterval();
            return true;
        }

        return false;
    }

    /**
     * Resets the power-up state for a new game.
     */
    public void reset() {
        powerUp = null;
        powerUpTimer = getRandomPowerUpSpawnInterval();
        collectedPowerUpThisFrame = false;
    }

    /**
     * Removes the current power-up without affecting the player's active power-up.
     */
    public void clear() {
        powerUp = null;
        collectedPowerUpThisFrame = false;
    }

    /**
     * Returns a random delay before the next power-up can spawn.
     *
     * @return a random spawn interval between the minimum and maximum limit
     */
    private float getRandomPowerUpSpawnInterval() {
        return random.nextFloat(MIN_POWER_UP_SPAWN_INTERVAL, MAX_POWER_UP_SPAWN_INTERVAL);
    }

    public PowerUp getPowerUp() {
        return powerUp;
    }

    public boolean didCollectPowerUpThisFrame() {
        return collectedPowerUpThisFrame;
    }

    protected void setPowerUpForTesting(PowerUp powerUp) {
        this.powerUp = powerUp;
    }
}