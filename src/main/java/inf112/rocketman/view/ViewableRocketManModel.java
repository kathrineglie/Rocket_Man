package inf112.rocketman.view;

import inf112.rocketman.grid.IGrid;
import inf112.rocketman.model.GameBoard;

public interface ViewableRocketManModel {
    float getPlayerX();
    float getPlayerY();

    IGrid getGrid();
}
