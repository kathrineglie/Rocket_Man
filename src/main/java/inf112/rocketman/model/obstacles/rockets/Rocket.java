package inf112.rocketman.model.obstacles.rockets;

import com.badlogic.gdx.math.Rectangle;
import inf112.rocketman.model.Velocity;
import inf112.rocketman.model.obstacles.Obstacle;

/**
 * Represents a rocket obstacle that becomes active after a short delay.
 *
 * <p>Before activation, the rocket waits for its spawn timer to finish.
 * When active, it starts moving like a normal obstacle.</p>
 */
public class Rocket extends Obstacle {

    private static boolean active = false;
    private float spawnTimer = 1.5f;

    /**
     * Creates a new rocket obstacle.
     *
     * @param bounds the bounds of the obstacle
     * @param velocity the velocity of the object
     * @param ground the ground of the player and the objects
     */
    public Rocket(Rectangle bounds, Velocity velocity, float ground) {
        super(bounds, velocity, ground);
    }

    @Override
    public void update(float dt) {
        if (!active) {
            spawnTimer -= dt;
            if (spawnTimer <= 0) {
                setState(true);
            }
        } else {
            super.update(dt);
        }
    }

    /**
     * Sets whether rockets are active
     *
     * @param state true if rockets should be active, false otherwise.
     */
    public static void setState(boolean state) {
        active = state;
    }

    /**
     * Checks whether the rocket is active.
     *
     * @return true if the rocket is active, false otherwise
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Returns the remaining spawn time before the rocket becomes active.
     *
     * @return the remaining spawn timer
     */
    public float getSpawnTimer() {
        return spawnTimer;
    }
}
