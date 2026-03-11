package inf112.rocketman.view.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.ScreenUtils;
import inf112.rocketman.Main;
import inf112.rocketman.controller.RocketManController;

public class PauseScreen extends AbstractMenuScreen {

    public PauseScreen(Main game, RocketManController controller) {
        super(game, controller);
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0,0,0.2f,1);

        batch.begin();
        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        float centerX = width / 2f;

        font.setColor(3f, 1, 1, 2f);
        drawCentered(font, "PAUSED", centerX, height / 2f + 100);

        smallFont.setColor(Color.WHITE);
        drawCentered(smallFont, "Press P to resume", centerX, height / 2f);
        drawCentered(smallFont, "Press ESC to QUIT to Menu", centerX, height / 2f - 80);
        batch.end();

        controller.handleInput();

    }

    private void drawCentered(BitmapFont font, String text, float centerX, float y) {
        GlyphLayout layout = new GlyphLayout(font, text);
        font.draw(batch, layout, centerX - layout.width / 2f, y);
    }
}
