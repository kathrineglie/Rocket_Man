package inf112.rocketman.model.movement;

import inf112.rocketman.model.Character.TPowah;

public class BirdMovement implements MovementBehavior{

    private static final float BIRD_FLAP_STRENGTH = 550f;
    private static final float BIRD_GRAVITY = -1000f;
    private static final float BIRD_MAX_FALL_SPEED = 900f;


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
