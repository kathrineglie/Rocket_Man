package inf112.rocketman.model.movement;

import inf112.rocketman.model.Character.TPowah;

public class RobotMovement implements MovementBehavior{

    private boolean robotIsJumping = false;
    private boolean robotGoingDown = false;

    private static final float ROBOT_BOOST = 20f;
    private static final float ROBOT_MIN_JUMP = 200f;
    private static final float ROBOT_SLOW_GRAVITY = -100f;
    private static final float ROBOT_GRAVITY = -800f;

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
}
