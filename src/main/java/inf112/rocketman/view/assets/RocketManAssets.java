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
    private Map<String, Texture> textures = new HashMap<>();
    private BitmapFont font;
    private BitmapFont titleFont;

    /**
     * Loads fonts and preloads textures used by the game.
     */
    public void create(){
        font = new BitmapFont();

        FreeTypeFontGenerator bodyGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/font2.ttf"));
        FreeTypeFontGenerator titleGenerator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/font.ttf"));

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
                "TPowah/jetpack.png", "TPowah/jetpack_flames.png", "TPowah/run1.png", "TPowah/run2.png", "TPowah/run3.png", "TPowah/run4.png",
                "Background/background.png",
                "Obstacles/Rocket.png", "Obstacles/warning.png",
                "PowerUps/bird.png", "PowerUps/birdUP.png",
                "PowerUps/box.png",
                "Obstacles/activeLazer.png", "Obstacles/harmlessLazer.png", "Obstacles/inactiveLazer.png",
                "Obstacles/flame.png",
                "Obstacles/coin.png",
                "PowerUps/run1.png", "PowerUps/run2.png", "PowerUps/run3.png", "PowerUps/run4.png", "PowerUps/fly.png", "PowerUps/fly_flame.png",
                "TPowah/run1pirate.png", "TPowah/run2pirate.png", "TPowah/run3pirate.png", "TPowah/jetpack_flames_pirate.png",
                "PowerUps/birdUp_pirate.png", "PowerUps/bird_pirate.png",
                "PowerUps/gravity1.png", "PowerUps/gravity2.png", "PowerUps/gravity3.png", "PowerUps/gravity4.png",
                "PowerUps/gravityUP1.png", "PowerUps/gravityUP2.png", "PowerUps/gravityUP3.png", "PowerUps/gravityUP4.png",
                "PowerUps/up.png", "PowerUps/down.png"
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
