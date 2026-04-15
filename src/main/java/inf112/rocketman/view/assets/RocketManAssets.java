package inf112.rocketman.view.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
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
    private Map<String, Music> music = new HashMap<>();
    private BitmapFont font;
    private BitmapFont titleFont;
    
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

        preloadSounds(List.of("Sounds/jetpack.mp3", "Sounds/coin.mp3", "Sounds/powerup.mp3", "Sounds/bird.mp3", "Sounds/game_over.mp3", "Sounds/robot.mp3", "Sounds/gravity_suit.mp3"));
        preloadMusic(List.of("Sounds/music.mp3", "Sounds/MeowMeow.mp3"));
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

    private void preloadMusic(List<String> names) {
        for (String musicName : names) {
            getMusic(musicName);
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
            if (file.exists()) {
                textures.put(name, new Texture(file));
            } else {
                throw new IllegalStateException("Texture file not found: " + name);
            }
        }
        return textures.get(name);
    }

    private Sound getSound(String name){
        if (!sounds.containsKey(name)){
            FileHandle file = Gdx.files.internal(name);
            if (file.exists()) {
                sounds.put(name, Gdx.audio.newSound(file));
            } else {
                sounds.put(name, null);
            }
        }
        return sounds.get(name);
    }

    private Music getMusic(String name) {
        if (!music.containsKey(name)) {
            FileHandle file = Gdx.files.internal(name);
            if (file.exists()) {
                Music m = Gdx.audio.newMusic(file);
                m.setLooping(true);
                music.put(name, m);
            } else {
                music.put(name, null);
            }
        }
        return music.get(name);
    }

    public void stopAllMusic() {
        music.values().forEach(m -> {
            if (m != null) {
                m.stop();
            }
        });
    }

    public void playExclusiveMusic(String musicName) {
        stopAllMusic();
        playMusic(musicName);
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

    public void playMusic(String musicName) {
        Music m = getMusic(musicName);
        if (m != null && !m.isPlaying()) {
            m.setLooping(true);
            m.setVolume(0.1f);
            m.play();
        }
    }

    public void stopMusic(String musicName) {
        Music m = getMusic(musicName);
        if (m != null) {
            m.stop();
        }
    }

    public long loopSound(String soundName){
        Sound s = getSound(soundName);
        if (s != null) {
            return s.loop(3f);
        }
        return -1;
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

        music.values().forEach(m -> {
            if (m != null)
                m.dispose();
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
