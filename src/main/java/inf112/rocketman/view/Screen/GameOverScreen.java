package inf112.rocketman.view.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import inf112.rocketman.Main;
import inf112.rocketman.controller.RocketManController;

public class GameOverScreen extends AbstractMenuScreen{

    public GameOverScreen(Main game, RocketManController controller) {
        super(game, controller);
    }

    @Override
    public void render(float v) {
        controller.handleInput();

        ScreenUtils.clear(0.2f, 0.05f, 0.05f, 1);

        batch.begin();

        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        font.setColor(Color.RED);
        GlyphLayout layout = new GlyphLayout(font, "GAME OVER");

        float titleX = width / 2f - layout.width / 2f;
        float titleY = height / 2f + 140;

        font.draw(batch, layout, titleX, titleY );

        smallFont.setColor(Color.WHITE);

        GlyphLayout smallLayout = new GlyphLayout(smallFont, "PRESS ENTER TO RESTART");
        smallFont.draw(batch, smallLayout, width / 2f - smallLayout.width / 2f, height / 2f);

        batch.end();
    }

}
