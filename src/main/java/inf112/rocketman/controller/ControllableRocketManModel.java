package inf112.rocketman.controller;

import inf112.rocketman.model.GameState;

/**
 * Interface representing the controllable parts of the RocketMan game model.
 * It provides methods for updating the game state and managing the game lifecycle
 * from a controller's perspective.
 */
public interface ControllableRocketManModel {
    /**
     * updates the game state based on elapsed time and player input.
     *
     * @param dt The delta dime (seconds) since last update.
     * @param thrusting True if the player is currently applying thrust to the rocket.
     */

    void update(float dt, boolean thrusting);

    /**
     * Retrieves the current state of the game.
     *
     * @return The current {@link GameState}.
     */
    GameState getGameState();

    /**
     * @return The total height of the game world in world units.
     */
    float getWorldHeight();

    /**
     * @return The total width of the game world in world units.
     */
    float getWorldWidth();

    /**
     * Transitions the game from the menu/home screen to active playing state.
     */
    void startNewGame();

    /**
     * Transitions the game back to the home screen.
     */
    void goToHomescreen();

    /**
     * Pause the ongoing game.
     */
    void pauseGame();

    /**
     * Resumes the game form a paused state
     */
    void resumeGame();

    /**
     * Signals that the game has ended (e.g., player crashed).
     */
    void endGame();

    void showInstructions();

    /**
     * Sets the name of the player that is currently playing
     */
    void setPlayerName(String playerName);

}
