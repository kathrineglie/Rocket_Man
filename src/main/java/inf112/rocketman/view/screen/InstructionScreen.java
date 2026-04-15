package inf112.rocketman.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import inf112.rocketman.Main;
import inf112.rocketman.controller.RocketManController;

public class InstructionScreen extends AbstractMenuScreen {
    private SpriteBatch batch;

    public InstructionScreen(Main game, RocketManController controller, SpriteBatch batch) {
        super(game, controller, batch);
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0.05f, 0.05f, 0.1f, 1);
        batch.begin();

        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();
        float centerX = width / 2f;

        font.setColor(Color.WHITE);
        smallFont.setColor(Color.WHITE);

        font.getData().setScale(0.45f);
        drawCentered(font, "HOW TO PLAY", centerX, height - 40);
        font.getData().setScale(1.0f);

        smallFont.getData().setScale(0.5f);
        float y = height - 90;
        float lineSpacing = 28f;
        float sectionSpacing = 40f;

        drawCentered(smallFont, "Rocket Man is a survival game where your goal is to stay alive for as long as possible.", centerX, y);
        y -= lineSpacing;
        drawCentered(smallFont, "Avoid obstacles, collect coins, and use power-ups to survive longer.", centerX, y);

        y -= sectionSpacing;
        drawCentered(smallFont, "CONTROLS", centerX, y);

        y -= lineSpacing;
        drawCentered(smallFont, "SPACE - hold to activate the jetpack", centerX, y);
        y -= lineSpacing;
        drawCentered(smallFont, "P - pause the game", centerX, y);
        y -= lineSpacing;
        drawCentered(smallFont, "ESC - return to the main menu", centerX, y);
        y -= lineSpacing;
        drawCentered(smallFont, "ENTER - restart after crashing", centerX, y);

        y -= sectionSpacing;
        drawCentered(smallFont, "OBSTACLES", centerX, y);

        y -= lineSpacing;
        drawCentered(smallFont, "Rockets move fast and can quickly end your run.", centerX, y);
        y -= lineSpacing;
        drawCentered(smallFont, "Lasers force you to react carefully.", centerX, y);
        y -= lineSpacing;
        drawCentered(smallFont, "Flames are dangerous and should be avoided at all costs.", centerX, y);

        y -= sectionSpacing;
        drawCentered(smallFont, "POWER-UPS", centerX, y);

        y -= lineSpacing;
        drawCentered(smallFont, "Power-ups can give you special abilities.", centerX, y);
        y -= lineSpacing;
        drawCentered(smallFont, "The bird power-up changes your movement and can help you escape danger.", centerX, y);

        y -= sectionSpacing;
        drawCentered(smallFont, "COINS", centerX, y);

        y -= lineSpacing;
        drawCentered(smallFont, "Coins increase your coin count when collected during gameplay.", centerX, y);

        y -= sectionSpacing;
        drawCentered(smallFont, "GOAL", centerX, y);

        y -= lineSpacing;
        drawCentered(smallFont, "Stay alive, dodge obstacles, and get the highest score possible.", centerX, y);

        y -= sectionSpacing;
        drawCentered(smallFont, "Press ESC to return", centerX, y);

        smallFont.getData().setScale(1.0f);
        batch.end();

        controller.handleInput();
    }

    private void drawCentered(BitmapFont font, String text, float centerX, float y) {
        GlyphLayout layout = new GlyphLayout(font, text);
        font.draw(batch, layout, centerX - layout.width / 2f, y);
    }
}
