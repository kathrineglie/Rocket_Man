package inf112.skeleton.view;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import inf112.skeleton.grid.IGrid;

public class GridRenderer {

    private final float cellSize;   
    private final float originX;
    private final float originY;
    private final boolean flipRows; 

    public GridRenderer(float cellSize, float originX, float originY, boolean flipRows) {
        this.cellSize = cellSize;
        this.originX = originX;
        this.originY = originY;
        this.flipRows = flipRows;
    }

    public void draw(ShapeRenderer renderer, IGrid grid) {
        int cols = grid.cols();
        int rows = grid.rows();

        float width = cols * cellSize;
        float height = rows * cellSize;

        renderer.begin(ShapeRenderer.ShapeType.Line);

        for (int c = 0; c <= cols; c++) {
            float x = originX + c * cellSize;
            renderer.line(x, originY, x, originY + height);
        }

        for (int r = 0; r <= rows; r++) {
            float y = originY + r * cellSize;
            renderer.line(originX, y, originX + width, y);
        }

        renderer.end();
    }

    public float cellX(int col) {
        return originX + col * cellSize;
    }

    public float cellY(int row, int totalRows) {
        int drawRow = flipRows ? (totalRows - 1 - row) : row;
        return originY + drawRow * cellSize;
    }
}