package inf112.rocketman.view;

import inf112.rocketman.model.GameState;
import inf112.rocketman.model.Obstacles.IObstacle;
import inf112.rocketman.model.Character.TPowah;

import java.util.List;

public interface ViewableRocketManModel {
    float getBackgroundScrollX();
    List<IObstacle> getObstacles();
    TPowah getPlayer();

    GameState getGameState();

}

