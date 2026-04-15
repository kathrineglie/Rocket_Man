package inf112.rocketman.model.movement;

import inf112.rocketman.model.Character.TPowah;

public interface MovementBehavior {
    void update(TPowah player, float dt, boolean movementInput, float worldHeight);
}
