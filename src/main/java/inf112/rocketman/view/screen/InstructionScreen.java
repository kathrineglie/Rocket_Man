package inf112.rocketman.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.ScreenUtils;
import inf112.rocketman.Main;
import inf112.rocketman.controller.RocketManController;

public class InstructionScreen extends AbstractMenuScreen {

    public InstructionScreen(Main game, RocketManController controller) {
        super(game, controller);
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0.05f, 0.05f, 0.1f, 1);
        batch.begin();

        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        float centerX = width / 2f;

        drawCentered(font, "HOW TO PLAY", width / 2f, height - 150);

        drawCentered(smallFont, "Avoid rockets and survive as long as possible", centerX, height - 250);

        drawCentered(smallFont, "CONTROLS", centerX, height - 350);

        drawCentered(smallFont, "SPACE- hold to activate jetpack", centerX, height -420);
        drawCentered(smallFont, "ESC- return to main menu", centerX, height - 470);
        drawCentered(smallFont, "ENTER- restart after crashing", centerX, height-520);

        batch.end();

        controller.handleInput();
    }

    private void drawCentered(BitmapFont font, String text, float centerX, float y) {
        GlyphLayout layout = new GlyphLayout(font, text);
        font.draw(batch, layout, centerX - layout.width / 2f, y);
    }
}
