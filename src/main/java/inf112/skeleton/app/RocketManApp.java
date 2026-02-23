package inf112.skeleton.app;

import com.badlogic.gdx.ApplicationAdapter;
import inf112.skeleton.view.RocketManView;
import inf112.skeleton._example.view.Painter;

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
            // nothing yet — grid is drawn inside RocketManView.render()
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