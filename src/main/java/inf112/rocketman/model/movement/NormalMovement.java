package inf112.rocketman.model.movement;

import inf112.rocketman.model.Character.TPowah;

public class NormalMovement implements MovementBehavior{

    private static final float NORMAL_THRUST = 4000f;
    private static final float NORMAL_GRAVITY = -1000f;
    private static final float NORMAL_MAX_VY = 700f;


    @Override
    public void update(TPowah player, float dt, boolean movementInput, float worldHeight) {
        float ay = NORMAL_GRAVITY + (movementInput ? NORMAL_THRUST : 0f);
        player.setVy(player.getVY() + ay * dt);

        if (player.getVY() > NORMAL_MAX_VY) {player.setVy(NORMAL_MAX_VY); }
        if (player.getVY() < - NORMAL_MAX_VY) {player.setVy(-NORMAL_MAX_VY); }

        player.setY(player.getY() + player.getVY() * dt);

        player.keepPlayerInsideBounds(worldHeight);
    }
}
