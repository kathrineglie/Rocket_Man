package inf112.rocketman.model.movement;

import inf112.rocketman.model.Character.TPowah;

/**
 * This class updates the character everytime space is pressed. It will not do anything different when holding space.
 * When space is pressed, the gravity will change and it will run on top of the screen instead of the bottom.
 * You can also switch the gravity while you are in the middle of the air.
 */
public class GravitySuitMovement implements MovementBehavior{

    private static final float GRAVITY_SUIT_GRAVITY = -1500;
    private boolean gravityUp = false;

    /**
     * Updates the player using the gravity movement as explained above
     *
     * @param player the character you are playing with
     * @param dt time passed since the last frame
     * @param movementInput the input from the player. True if space is pressed
     * @param worldHeight the height of the game
     */
    @Override
    public void update(TPowah player, float dt, boolean movementInput, float worldHeight) {
        if (gravityUp) {
            player.setVy(player.getVY() - GRAVITY_SUIT_GRAVITY * dt);
        } else {
            player.setVy(player.getVY() + GRAVITY_SUIT_GRAVITY * dt);
        }

        player.setY(player.getY() + player.getVY() * dt);

        if (movementInput) {
            gravityUp = !gravityUp;
            player.setVy(0);
        }

        player.keepPlayerInsideBounds(worldHeight);
    }

    public boolean isGravityUp() {
        return gravityUp;
    }
}
