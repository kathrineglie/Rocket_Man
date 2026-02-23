package inf112.skeleton.model;


import inf112.skeleton.grid.Grid;

public class GameBoard extends Grid {

    public GameBoard(int rows, int cols){
        super(rows, cols, '-');
    }

    public GameBoard(int rows, int cols, Character defaultValue) {
        super(rows, cols, '-');
    }
}
    
