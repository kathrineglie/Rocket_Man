package inf112.rocketman.controller;

import inf112.rocketman.model.GameState;

public interface ControllableRocketManModel {
    void update(float dt, boolean thrusting);

    GameState getGameState();

    void startGame();
    void goToHomescreen();
    void pauseGame();
    void resumeGame();
    void endGame();

}
