package inf112.rocketman.view.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import inf112.rocketman.Main;
import inf112.rocketman.controller.RocketManController;

public class HomeScreen implements Screen {

    private final Main game;
    private RocketManController controller;
    private SpriteBatch batch;
    private BitmapFont font;
    private BitmapFont smallFont;

    public HomeScreen(Main game, RocketManController controller) {
        this.game = game;
        this.controller = controller;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        this.font = controller.getView().getAssets().getTitleFont();
        this.smallFont = controller.getView().getAssets().getFont();
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.05f, 0.05f, 0.1f, 1); // mørk blå bakgrunn

        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        batch.begin();

        GlyphLayout titleLayout = new GlyphLayout(font, "ROCKET MAN");
        float titleX = width / 2f - titleLayout.width / 2f;
        float titleY = height / 2f + 140;

        font.setColor(0,0,0,0.5f);
        font.draw(batch, titleLayout, titleX + 2, titleY - 2);

        font.setColor(Color.WHITE);
        font.draw(batch, titleLayout, titleX, titleY);

        float alpha = (float) Math.abs(Math.sin(TimeUtils.millis() / 400.0));
        smallFont.setColor(1, 1, 1, alpha);

        GlyphLayout subLayout = new GlyphLayout(smallFont, "Press SPACE to start");
        smallFont.draw(batch, subLayout, width / 2f - subLayout.width / 2f, height / 2f);

        smallFont.setColor(Color.WHITE);
        font.getData().setScale(1.0f);

        batch.end();

        controller.handleInput();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        //font.dispose();
    }
}
