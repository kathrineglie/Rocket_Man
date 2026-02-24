package inf112.rocketman.controller;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import inf112.rocketman.model.GameBoard;
import inf112.rocketman.model.GameModel;
import inf112.rocketman.view.GridRenderer;
import inf112.rocketman.view.RocketManView;

public class RocketManController implements ApplicationListener {

    private RocketManView view;
    private GameModel model;

    @Override
    public void create() {
        view = new RocketManView();
        view.create(1000, 800);

        model = new GameModel(view.getWorldHeight());

        //Gdx.graphics.setForegroundFPS(60);
    }

    @Override
    public void render() {
        float dt = Gdx.graphics.getDeltaTime();
        boolean space = Gdx.input.isKeyPressed(Input.Keys.SPACE);

        model.update(dt, space);

        view.render(painter -> {
            painter.draw(0,0,
                    view.getWorldWidth(),
                    view.getWorldHeight(),
                    "background.png");

            painter.draw(model.getPlayerX(),
                    model.getPlayerY(),
                    64,
                    64,
                    "tevje.png");
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