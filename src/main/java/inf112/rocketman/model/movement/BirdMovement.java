package inf112.rocketman.model.movement;

import inf112.rocketman.model.Character.TPowah;

/**
 * This class updates the character everytime space is pressed. It does not react any different to space if you are holding it
 * Everytime you press space it will do a small jump/flap up and then fall down with the specified gravity.
 */
public class BirdMovement implements MovementBehavior{

    private static final float BIRD_FLAP_STRENGTH = 550f;
    private static final float BIRD_GRAVITY = -1000f;
    private static final float BIRD_MAX_FALL_SPEED = 900f;

    /**
     * Updates the player using the bird movement as explained above
     *
     * @param player the character you are playing with
     * @param dt time passed since the last frame
     * @param movementInput the input from the player. True if space is pressed
     * @param worldHeight the height of the game
     */
    @Override
    public void update(TPowah player, float dt, boolean movementInput, float worldHeight) {

        if (movementInput){
            player.setVy(BIRD_FLAP_STRENGTH);
        }

        player.setVy(player.getVY() + BIRD_GRAVITY * dt);

        if (player.getVY() < -BIRD_MAX_FALL_SPEED){
            player.setVy(-BIRD_MAX_FALL_SPEED);
        }

        player.setY(player.getY() + player.getVY() * dt);
        player.keepPlayerInsideBounds(worldHeight);
    }
}
