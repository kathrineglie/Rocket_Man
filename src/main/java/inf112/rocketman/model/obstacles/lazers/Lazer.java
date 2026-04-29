package inf112.rocketman.model.obstacles.lazers;

import com.badlogic.gdx.math.Rectangle;
import inf112.rocketman.model.Velocity;
import inf112.rocketman.model.obstacles.Obstacle;

/**
 * Represents a lazer obstacle with multiple progression stages.
 *
 * <p>The lazer goes through harmless, warning, and active phases
 * before it starts moving normally.</p>
 */
public class Lazer extends Obstacle {
    private float harmlessLazerCount = 1.5f;
    private float warningLazerCount = 1.5f;
    private float activeLazerCount = 2f;
    private int progression = 1;

    /**
     * Creates a new laser obstacle.
     *
     * @param bounds the bounds of the obstacle
     * @param velocity the velocity of the object
     * @param ground the ground of the player and the objects
     */
    public Lazer(Rectangle bounds, Velocity velocity, float ground) {
        super(bounds, velocity, ground);
    }

    @Override
    public void update(float dt) {
        switch (progression) {
            case 1 -> {
                harmlessLazerCount -= dt;
                if (harmlessLazerCount <= 0) progression = 2;
            }
            case 2 -> {
                warningLazerCount -= dt;
                if (warningLazerCount <= 0) progression = 3;
            }
            case 3 -> {
                activeLazerCount -= dt;
                if (activeLazerCount <= 0) progression = 4;
            }
            default -> super.update(dt);
        }
    }

    /**
     * Sets the current progression level of the lazer.
     *
     * @param level the new progression level
     */
    public void setProgressionLevel(int level) {
        progression = level;
    }

    /**
     * Returns the current progression level of the lazer.
     *
     * @return the current progression level
     */
    public int getProgressionLevel() {
        return progression;
    }

    /**
     * Checks whether the lazer has finished its staged sequence.
     *
     * @return true if the lazer has reached its final stage, false otherwise
     */
    public boolean isFinished() {
        return progression == 4;
    }
}
