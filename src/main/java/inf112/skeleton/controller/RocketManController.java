package inf112.skeleton.controller;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import inf112.skeleton.model.GameBoard;
import inf112.skeleton.view.GridRenderer;
import inf112.skeleton.view.RocketManView;

public class RocketManController implements ApplicationListener {

    private RocketManView view;
    private GameBoard board;
    private GridRenderer gridRenderer;

    @Override
    public void create() {
        board = new GameBoard(20, 20);
        view = new RocketManView();
        view.create(1000, 800);

        gridRenderer = new GridRenderer(0f, 0f, true);

        Gdx.graphics.setForegroundFPS(60);
    }

    @Override
    public void render() {
        view.render(painter -> {});
        view.renderGrid(board, gridRenderer);
    }

    /*@Override
    public void render() {
        view.render(painter -> {});

        int rows = board.rows();
        int cols = board.cols();

        float worldW = (float) view.worldWidth();
        float worldH = (float) view.worldHeight();

        float cellSize = Math.min(worldW / cols, worldH / rows);

        float gridW = cols * cellSize;
        float gridH = rows * cellSize;

        float originX = (worldW - gridW) / 2f;
        float originY = (worldH - gridH) / 2f;

        GridRenderer renderer = new GridRenderer(cellSize, originX, originY, true);
        view.renderGrid(board, renderer);

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }*/

    @Override public void resize(int width, int height) { view.resize(width, height); }
    @Override public void pause() {}
    @Override public void resume() {}

    @Override
    public void dispose() {
        view.dispose();
    }
}