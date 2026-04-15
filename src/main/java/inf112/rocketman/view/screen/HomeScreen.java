package inf112.rocketman.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import inf112.rocketman.Main;
import inf112.rocketman.controller.RocketManController;

public class HomeScreen extends inf112.rocketman.view.screen.AbstractMenuScreen implements InputProcessor {

    private String playerName = "";
    private boolean editingName = false;
    private final RocketManController controller;

    public HomeScreen(Main game, RocketManController controller, SpriteBatch batch) {
        super(game, controller, batch);
        this.controller = controller;
        Gdx.input.setInputProcessor(this);

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0.05f, 0.05f, 0.1f, 1);

        float width = Gdx.graphics.getWidth();
        float height = Gdx.graphics.getHeight();

        float titleY = height / 2f + 140;
        float hintY = height / 2f + 75;
        float nameY = height / 2f + 20;
        float startY = height / 2f - 40;

        float boxWidth = 300;
        float boxHeight = 50;
        float boxX = width / 2f - boxWidth / 2f;
        float boxY = nameY - 35;

        float qMarkX = width - 60;
        float qMarkY = height - 60;

        batch.begin();

        GlyphLayout titleLayout = new GlyphLayout(font, "ROCKET MAN");
        float titleX = width / 2f - titleLayout.width / 2f;

        font.setColor(0, 0, 0, 0.5f);
        font.draw(batch, titleLayout, titleX + 2, titleY - 2);

        font.setColor(Color.WHITE);
        font.draw(batch, titleLayout, titleX, titleY);

        smallFont.setColor(Color.WHITE);

        if (!editingName && playerName.isBlank()) {
            GlyphLayout hintLayout = new GlyphLayout(smallFont, "Click name to enter your name");
            smallFont.draw(batch, hintLayout, width / 2f - hintLayout.width / 2f, hintY);
        }

        boolean showText = editingName && (TimeUtils.millis() / 500) % 2 == 0;
        String nameText = showText
                ? "Name: " + playerName + "|"
                : "Name: " + playerName;

        GlyphLayout nameLayout = new GlyphLayout(smallFont, nameText);
        float nameX = width / 2f - nameLayout.width / 2f;

        smallFont.draw(batch, nameLayout, nameX, nameY);

        float alpha = (float) Math.abs(Math.sin(TimeUtils.millis() / 400.0));
        smallFont.setColor(1, 1, 1, alpha);
        GlyphLayout subLayout = new GlyphLayout(smallFont, "Press SPACE to start");
        smallFont.draw(batch, subLayout, width / 2f - subLayout.width / 2f, startY);

        font.setColor(Color.WHITE);
        font.getData().setScale(1.0f);

        GlyphLayout questionLayout = new GlyphLayout(font, "?");
        font.draw(batch, questionLayout, qMarkX, qMarkY);

        batch.end();

        if (Gdx.input.justTouched()) {
            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (mouseX >= qMarkX && mouseX <= qMarkX + questionLayout.width
                    && mouseY >= qMarkY - questionLayout.height && mouseY <= qMarkY) {
                controller.showInstruction();
            }

            if (mouseX > boxX && mouseX < boxX + boxWidth
                    && mouseY > boxY && mouseY < boxY + boxHeight) {
                editingName = true;
            } else {
                editingName = false;
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && !playerName.isBlank()) {
            playerName = playerName.stripTrailing();
            editingName = false;
            controller.startGame(playerName);
        }
    }

    @Override
    public boolean keyTyped(char character) {
        if (!editingName) {
            return false;
        }

        if (character == '\b') {
            if (!playerName.isEmpty()) {
                playerName = playerName.substring(0, playerName.length() - 1);
            }
            return true;
        }

        if (character == '\r' || character == '\n') {
            editingName = false;
            return true;
        }

        if (Character.isLetterOrDigit(character)) {
            if (playerName.length() < 12) {
                playerName += character;
            }
            return true;
        }

        if (character == ' ') {
            if (!playerName.isEmpty() && !playerName.endsWith(" ") && playerName.length() < 12) {
                playerName += character;
            }
            return true;
        }

        return false;
    }

    @Override public boolean keyDown(int keycode) { return false; }
    @Override public boolean keyUp(int keycode) { return false; }
    @Override public boolean touchDown(int screenX, int screenY, int pointer, int button) { return false; }
    @Override public boolean touchUp(int screenX, int screenY, int pointer, int button) { return false; }
    @Override public boolean touchCancelled(int screenX, int screenY, int pointer, int button) { return false; }
    @Override public boolean touchDragged(int screenX, int screenY, int pointer) { return false; }
    @Override public boolean mouseMoved(int screenX, int screenY) { return false; }
    @Override public boolean scrolled(float amountX, float amountY) { return false; }


}
