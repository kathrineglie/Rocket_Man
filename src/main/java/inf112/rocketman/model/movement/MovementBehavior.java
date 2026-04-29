package inf112.rocketman.model.movement;

import inf112.rocketman.model.character.TPowah;

public interface MovementBehavior {
    /**
     * This method updates the player's movement depending on what powerup it currently has
     *
     * @param player the character you are playing with
     * @param dt time passed since the last frame
     * @param movementInput the input from the player. True if space is pressed
     * @param worldHeight the height of the game
     * @param margin the distance from the edge of the screen that defines the playable area
     */
    void update(TPowah player, float dt, boolean movementInput, float worldHeight, float margin);
}
