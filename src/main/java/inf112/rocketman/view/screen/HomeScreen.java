package inf112.rocketman.view.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import inf112.rocketman.Main;
import inf112.rocketman.controller.RocketManController;

public class HomeScreen extends inf112.rocketman.view.screen.AbstractMenuScreen implements InputProcessor {

    private String playerName = "";
    private final RocketManController controller;

    public HomeScreen(Main game, RocketManController controller) {

        super(game, controller);
        this.controller = controller;
        Gdx.input.setInputProcessor(this);

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

        float qMarkX = width - 60;
        float qMarkY = height - 60;

        font.draw(batch, "?", qMarkX, qMarkY);

        if (Gdx.input.justTouched()) {
            float mouseX = Gdx.input.getX();
            float mouseY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (mouseX > qMarkX - 20 && mouseX < qMarkX + 40 && mouseY > qMarkY - 40 && mouseY < qMarkY + 20) {
                controller.showInstruction();
            }
        }

        GlyphLayout nameLayout = new GlyphLayout(smallFont, "Name: " +playerName);
        smallFont.draw(batch,nameLayout, width / 2f - nameLayout.width / 2f, height / 2f + 40);

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && !playerName.isBlank()) {
            playerName = playerName.stripTrailing();
            controller.startGame(playerName);
        }

        batch.end();

        controller.handleInput();

    }

    @Override
    public boolean keyTyped(char character) {
        if (character == '\b') {
            if (!playerName.isEmpty()) {
                playerName = playerName.substring(0, playerName.length() - 1);
            }
            return true;
        }

        if (character == '\r' || character == '\n') {
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
