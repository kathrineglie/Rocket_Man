package inf112.rocketman.view;

import inf112.rocketman.grid.IGrid;
import inf112.rocketman.model.GameBoard;
import inf112.rocketman.model.Obstacles.IObstacle;

import java.util.List;

public interface ViewableRocketManModel {
    float getPlayerX();
    float getPlayerY();
    float getPlayerWidth();
    float getPlayerHeight();
    float getBackgroundScrollX();
    List<IObstacle> getObstacles();
}

