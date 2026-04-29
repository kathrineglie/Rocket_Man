package inf112.rocketman.model.movement;

import inf112.rocketman.model.character.TPowah;

/**
 * This is the normal/default movement of the character without any powerups.
 * When space is pressed/held in, the character should move upwards, and when space is not pressed, it should fall down
 */
public class NormalMovement implements MovementBehavior{

    private static final float NORMAL_THRUST = 4000f;
    private static final float NORMAL_GRAVITY = -1000f;
    private static final float NORMAL_MAX_VY = 700f;

    /**
     * Updates the player using the normal/default movement as explained above
     *
     * @param player the character you are playing with
     * @param dt time passed since the last frame
     * @param movementInput the input from the player. True if space is pressed
     * @param worldHeight the height of the game
     */
    @Override
    public void update(TPowah player, float dt, boolean movementInput, float worldHeight, float margin) {
        float ay = NORMAL_GRAVITY + (movementInput ? NORMAL_THRUST : 0f);
        player.setVy(player.getVY() + ay * dt);

        if (player.getVY() > NORMAL_MAX_VY) {player.setVy(NORMAL_MAX_VY); }
        if (player.getVY() < - NORMAL_MAX_VY) {player.setVy(-NORMAL_MAX_VY); }

        player.setY(player.getY() + player.getVY() * dt);

        player.keepPlayerInsideBounds(worldHeight, margin);
    }
}
