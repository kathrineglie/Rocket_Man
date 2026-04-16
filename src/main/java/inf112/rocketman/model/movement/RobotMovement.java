package inf112.rocketman.model.movement;

import inf112.rocketman.model.Character.TPowah;

/**
 * This class updates the character when space is pressed only when it is on the ground or falling down.
 * This movement is like a jump and it will jump higher when space is pressed for a longer amount of time.
 * If you hold in space while the character is going down, it will slow down the gravity.
 * If space is not pressed while going down, the gravity will be the normal gravity.
 */
public class RobotMovement implements MovementBehavior{

    private boolean robotIsJumping = false;
    private boolean robotGoingDown = false;

    private static final float ROBOT_BOOST = 20f;
    private static final float ROBOT_MIN_JUMP = 200f;
    private static final float ROBOT_SLOW_GRAVITY = -100f;
    private static final float ROBOT_GRAVITY = -800f;

    /**
     * Updates the player using the robot movement as explained above
     *
     * @param player the character you are playing with
     * @param dt time passed since the last frame
     * @param movementInput the input from the player. True if space is pressed
     * @param worldHeight the height of the game
     */
    @Override
    public void update(TPowah player, float dt, boolean movementInput, float worldHeight) {
        if (!movementInput && player.onGround()) {
            robotIsJumping = false;
            robotGoingDown = false;
        }

        if (movementInput && player.onGround() && !robotIsJumping && !robotGoingDown) {
            player.setVy(ROBOT_MIN_JUMP);
            robotIsJumping = true;
        }

        if (movementInput && !player.onGround() && robotIsJumping && !robotGoingDown) {
            player.setVy(player.getVY() + ROBOT_BOOST);
        }

        if ((!movementInput && robotIsJumping) || Math.abs((player.getY() + player.getHeight()) - worldHeight) < 0.0001f) {
            robotGoingDown = true;
        }

        if (movementInput && robotGoingDown && robotIsJumping) {
            player.setVy(player.getVY() + ROBOT_SLOW_GRAVITY * dt);
        } else {
            player.setVy(player.getVY() + ROBOT_GRAVITY * dt);
        }

        player.setY(player.getY() + player.getVY() * dt);
        player.keepPlayerInsideBounds(worldHeight);
    }

    public boolean getRobotIsJumping() {
        return robotIsJumping;
    }

    public boolean getRobotIsGoingDown() {
        return robotGoingDown;
    }
}
