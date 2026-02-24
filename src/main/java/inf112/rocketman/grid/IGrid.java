package inf112.rocketman.grid;

public interface IGrid extends GridDimension, Iterable<GridCell> {

    /**
     * Sets the value of a position in the grid
     * 
     * @param pos the position on the grid where the symbol is stored 
     * @param symbol the new value
     */
    void set(CellPosition pos, Character symbol);

    /**
     * Gets the symbol from the position you specify
     * 
     * @param pos  the position to get a value from
     * @return
     */
    Character get(CellPosition pos);

    /**
     * Checks if the position is on the grid
     * 
     * @param pos The position you check
     * @return
     */
    boolean positionIsOnGrid(CellPosition pos);
}
