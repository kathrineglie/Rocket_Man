package inf112.rocketman.controller;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import inf112.rocketman.model.GameBoard;
import inf112.rocketman.view.GridRenderer;
import inf112.rocketman.view.RocketManView;



public class RocketManController implements ApplicationListener {

    private RocketManView view;
    private GameBoard board;
    private GridRenderer gridRenderer;

    @Override
    public void create() {
        board = new GameBoard(20, 20, null);
        view = new RocketManView();
        view.create(1000, 800);

        gridRenderer = new GridRenderer(0f, 0f, true);

        Gdx.graphics.setForegroundFPS(60);
    }

    @Override
    public void render() {
        float worldW = view.getWorldWidth();
        float worldH = view.getWorldHeight();

        float dt = Gdx.graphics.getDeltaTime();

        boolean space = Gdx.input.isKeyPressed(Input.Keys.SPACE);

        float ay = GRAVITY + (space ? THRUST : 0f);

        playerVY += ay * dt;

        if (playerVY > MAX_VY) playerVY = MAX_VY;
        if (playerVY < -MAX_VY) playerVY = -MAX_VY;

        playerY += playerVY * dt;

        if (playerY < 0) {
            playerY = 0;
            playerVY = 0;
        }
        if (playerY > worldH - PLAYER_H) {
            playerY = worldH - PLAYER_H;
            playerVY = 0;
        }

        view.renderGrid(board, gridRenderer);

        view.render(painter -> {
            painter.draw(0, 0, worldW, worldH, "background.png");
            painter.draw(playerX, playerY, PLAYER_W, PLAYER_H, "tevje.png");
        });


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

    @Override public void resize(int width, int height) {
        view.resize(width, height);
    }

    @Override public void pause() {}
    @Override public void resume() {}

    @Override
    public void dispose() {
        view.dispose();
    }
}