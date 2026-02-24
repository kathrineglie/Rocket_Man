package inf112.rocketman.view;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import inf112.rocketman.grid.IGrid;

public class GridRenderer {

    //private final float cellSize;
    private final float originX;
    private final float originY;
    private final boolean flipRows;

    private float cellW;
    private float cellH;

    public GridRenderer(float originX, float originY, boolean flipRows) {
        //this.cellSize = cellSize;
        this.originX = originX;
        this.originY = originY;
        this.flipRows = flipRows;
    }

    public void draw(ShapeRenderer renderer, IGrid grid, float worldW, float worldH) {
        int cols = grid.cols();
        int rows = grid.rows();

        cellW = worldW / cols;
        cellH = worldH / rows;

        //float width = cols * cellSize;
        //float height = rows * cellSize;

        renderer.begin(ShapeRenderer.ShapeType.Line);

        for (int c = 0; c <= cols; c++) {
            float x = originX + c * cellW;
            renderer.line(x, originY, x, originY + worldH);
        }

        for (int r = 0; r <= rows; r++) {
            float y = originY + r * cellH;
            renderer.line(originX, y, originX + worldW, y);
        }

        renderer.end();
    }

    public float cellX(int col) {

        return originX + col * cellW;
    }

    public float cellY(int row, int totalRows) {
        int drawRow = flipRows ? (totalRows - 1 - row) : row;
        return originY + drawRow * cellH;
    }
}