package inf112.rocketman.model.movement;

import inf112.rocketman.model.Character.TPowah;

public class GravitySuitMovement implements MovementBehavior{

    private static final float GRAVITY_SUIT_GRAVITY = -1500;
    private boolean gravityUp = false;

    @Override
    public void update(TPowah player, float dt, boolean movementInput, float worldHeight) {
        float ceiling = worldHeight - player.getHeight();

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
}
