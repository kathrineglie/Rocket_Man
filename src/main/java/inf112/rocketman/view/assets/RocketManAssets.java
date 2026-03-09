package inf112.rocketman.view.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import inf112.rocketman.view.TextureProvider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RocketManAssets implements TextureProvider {
    private Map<String, Texture> textures = new HashMap<>();
    private Map<String, Sound> sounds = new HashMap<>();
    private BitmapFont font;
    private BitmapFont titleFont;

    public void create(){
        font = new BitmapFont();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 90;
        parameter.magFilter = Texture.TextureFilter.Linear;
        parameter.minFilter = Texture.TextureFilter.Linear;
        this.titleFont = generator.generateFont(parameter);

        generator.dispose();


        preloadTextures(List.of("TPowah/TPowah.png", "TPowah/TPowahFlames.png", "Background/background.png", "Obstacles/Rocket.png", "Obstacles/warning.png", "PowerUps/Bird.png", "PowerUps/Box.png","Obstacles/activeLazer.png", "Obstacles/harmlessLazer.png", "Obstacles/inactiveLazer.png"));
        preloadSounds(List.of("Sounds/Teleport/MP3/Teleport.mp3"));
    }

    private void preloadTextures(List<String> names) {
        for (String fileName : names) {
            getTexture(fileName);
        }
    }

    private void preloadSounds(List<String> names){
        for(String soundName : names){
            getSound(soundName);
        }
    }

    /*
     * Example: load textures only once, and save them in a hash map.
     *
     * You should do the same with sounds and other resources. LibGDX has a built-in
     * AssetManager that can help with this.
     *
     * If you do new Texture() every time you draw, you'll slow the game down and
     * fill up memory.
     *
     * @param name
     *
     * @return
     */
    @Override
    public Texture getTexture(String name) {
        if (!textures.containsKey(name)) {
            FileHandle file = Gdx.files.internal(name);
            if (file != null) {
                textures.put(name, new Texture(file));
            } else {
                textures.put(name, null);
            }
        }
        return textures.get(name);
    }

    private Sound getSound(String name){
        if (!sounds.containsKey(name)){
            FileHandle file = Gdx.files.internal(name);
            if (file != null) {
                sounds.put(name, Gdx.audio.newSound(file));
            } else {
                sounds.put(name, null);
            }
        }
        return sounds.get(name);
    }

    public BitmapFont getFont(){
        return font;
    }


    public long playSound(String soundName) {
        Sound sou = getSound(soundName);
        if (sou != null){
            return sou.play();
        }
        return -1;
    }

    public void stopSound(String soundName, long soundId){
        Sound s = getSound(soundName);
        if (s!=null && soundId !=-1){
            s.stop(soundId);
        }
    }

    public void dispose(){
        sounds.values().forEach(sou -> {
            if (sou != null)
                sou.dispose();
        });
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
    public BitmapFont getTitleFont() {
        return titleFont;
    }

}
