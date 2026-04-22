package inf112.rocketman.view.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import inf112.rocketman.view.TextureProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages textures and fonts used by the RocketMan view layer.
 *
 * <p>This class loads, stores, and disposes graphical assets such as
 * textures and fonts.</p>
 */
public class RocketManAssets implements TextureProvider {
    private final Map<String, Texture> textures = new HashMap<>();
    private BitmapFont font;
    private BitmapFont titleFont;

    /**
     * Loads fonts and preloads textures used by the game.
     */
    public void create(){
        font = new BitmapFont();

        FreeTypeFontGenerator bodyGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/font2.ttf"));
        FreeTypeFontGenerator titleGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/font.ttf"));

        FreeTypeFontGenerator.FreeTypeFontParameter bodyParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        bodyParameter.size = 32;
        bodyParameter.magFilter = Texture.TextureFilter.Linear;
        bodyParameter.minFilter = Texture.TextureFilter.Linear;

        FreeTypeFontGenerator.FreeTypeFontParameter titleParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        titleParameter.size = 90;
        titleParameter.magFilter = Texture.TextureFilter.Linear;
        titleParameter.minFilter = Texture.TextureFilter.Linear;

        this.font = bodyGenerator.generateFont(bodyParameter);
        this.titleFont = titleGenerator.generateFont(titleParameter);

        bodyGenerator.dispose();
        titleGenerator.dispose();

        preloadTextures(List.of(
                "tpowah/jetpack.png", "tpowah/jetpack_flames.png", "tpowah/run1.png", "tpowah/run2.png", "tpowah/run3.png", "tpowah/run4.png",
                "background/background.png",
                "obstacles/Rocket.png", "obstacles/warning.png",
                "powerups/bird.png", "powerups/birdUP.png",
                "powerups/box.png",
                "obstacles/activeLazer.png", "obstacles/harmlessLazer.png", "obstacles/inactiveLazer.png",
                "obstacles/flame.png",
                "obstacles/coin.png",
                "powerups/run1.png", "powerups/run2.png", "powerups/run3.png", "powerups/run4.png", "powerups/fly.png", "powerups/fly_flame.png",
                "tpowah/run1pirate.png", "tpowah/run2pirate.png", "tpowah/run3pirate.png", "tpowah/run4pirate.png", "tpowah/jetpack_flames_pirate.png", "tpowah/jetpack_pirate.png",
                "powerups/birdUp_pirate.png", "powerups/bird_pirate.png",
                "powerups/gravity1.png", "powerups/gravity2.png", "powerups/gravity3.png", "powerups/gravity4.png",
                "powerups/gravityUP1.png", "powerups/gravityUP2.png", "powerups/gravityUP3.png", "powerups/gravityUP4.png",
                "powerups/up.png", "powerups/down.png"
                ));

    }

    private void preloadTextures(List<String> names) {
        for (String fileName : names) {
            getTexture(fileName);
        }
    }

    /**
     * Example: load textures only once, and save them in a hash map.
     * You should do the same with sounds and other resources. LibGDX has a built-in
     * AssetManager that can help with this.
     * If you do new Texture() every time you draw, you'll slow the game down and
     * fill up memory.
     *
     * @param name the file name of the texture
     * @return the loaded texture
     */
    @Override
    public Texture getTexture(String name) {
        if (!textures.containsKey(name)) {
            FileHandle file = Gdx.files.internal(name);
            if (file.exists()) {
                textures.put(name, new Texture(file));
            } else {
                throw new IllegalStateException("Texture file not found: " + name);
            }
        }
        return textures.get(name);
    }

    /**
     * Returns the standard font used in the game.
     *
     * @return the standard font
     */
    public BitmapFont getFont(){
        return font;
    }

    /**
     * Disposes all loaded textures and fonts
     */
    public void dispose(){
        textures.values().forEach(tex -> {
            if (tex != null)
                tex.dispose();
        });

        if (font != null){
            font.dispose();
        }

        if (titleFont != null) {
            titleFont.dispose();
        }

    }

    /**
     * Returns the title font used in the game.
     *
     * @return the title font
     */
    public BitmapFont getTitleFont() {
        return titleFont;
    }

}
