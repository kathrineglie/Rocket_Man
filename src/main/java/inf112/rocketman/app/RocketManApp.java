package inf112.rocketman.app;

import com.badlogic.gdx.ApplicationAdapter;
import inf112.rocketman.view.RocketManView;
import inf112.rocketman._example.view.Painter;

public class RocketManApp extends ApplicationAdapter {
    private RocketManView view;

    @Override
    public void create() {
        view = new RocketManView();
        view.create(800, 600);
    }

    @Override
    public void render() {
        view.render((Painter p) -> {
        });
    }

    @Override
    public void resize(int width, int height) {
        view.resize(width, height);
    }

    @Override
    public void dispose() {
        view.dispose();
    }
}