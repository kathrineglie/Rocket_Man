package inf112.rocketman.view.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.badlogic.gdx.files.FileHandle;

/**
 * Manages sound effects and music used in the game.
 *
 * <p>This class loads, stores, plays, and disposes audio resources.</p>
 */
public class RocketManAudio {
    private final Map<String, Sound> sounds = new HashMap<>();
    private final Map<String, Music> music = new HashMap<>();

    /**
     * Loads and preloads all sound effects and music used by the game.
     */
    public void create() {
        preloadSounds(List.of(
                "Sounds/jetpack.mp3",
                "Sounds/coin.mp3",
                "Sounds/powerup.mp3",
                "Sounds/bird.mp3",
                "Sounds/game_over.mp3",
                "Sounds/robot.mp3",
                "Sounds/gravity_suit.mp3"
        ));

        preloadMusic(List.of(
                "Sounds/music.mp3",
                "Sounds/MeowMeow.mp3"
        ));
    }

    private void preloadMusic(List<String> names) {
        for (String musicName : names) {
            getMusic(musicName);
        }
    }

    private void preloadSounds(List<String> names) {
        for (String soundName : names) {
            getSound(soundName);
        }
    }

    private Sound getSound(String name) {
        if (!sounds.containsKey(name)) {
            FileHandle file = Gdx.files.internal(name);
            if (file.exists()) {
                sounds.put(name, Gdx.audio.newSound(file));
            } else {
                throw new IllegalStateException("Sound file not found: " + name);
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
                throw new IllegalStateException("Music file not found: " + name);
            }
        }
        return music.get(name);
    }

    /**
     * Plays a sound effect once.
     *
     * @param soundName the file name of the sound effect
     * @return the id of the played sound instance
     */
    public long playSound(String soundName) {
        Sound sound = getSound(soundName);
        return sound.play();
    }

    /**
     * Plays a sound effect in a loop.
     *
     * @param soundName the file name of the sound effect
     * @return the id of the looping sound instance
     */
    public long loopSound(String soundName) {
        Sound sound = getSound(soundName);
        return sound.loop(3f);
    }

    /**
     * Stops a specific playing sound instance.
     *
     * @param soundName the file name of the sound effect
     * @param soundId the id of the sound instance to stop
     */
    public void stopSound(String soundName, long soundId) {
        Sound sound = getSound(soundName);
        if (soundId != -1) {
            sound.stop(soundId);
        }
    }

    /**
     * Plays a music track if it is not already playing.
     *
     * @param musicName the file name of the music track
     */
    public void playMusic(String musicName) {
        Music m = getMusic(musicName);
        if (!m.isPlaying()) {
            m.setLooping(true);
            m.setVolume(0.1f);
            m.play();
        }
    }

    /**
     * Stops the given music track.
     *
     * @param musicName the file name of the music track
     */
    public void stopMusic(String musicName) {
        Music m = getMusic(musicName);
        m.stop();
    }

    /**
     * Stops all loaded music tracks.
     */
    public void stopAllMusic() {
        music.values().forEach(m -> {
            if (m != null) {
                m.stop();
            }
        });
    }

    /**
     * Stops all music and then plays the given track.
     *
     * @param musicName the file name of the music track
     */
    public void playExclusiveMusic(String musicName) {
        stopAllMusic();
        playMusic(musicName);
    }

    /**
     * Disposes all loaded sound effects and music tracks.
     */
    public void dispose() {
        sounds.values().forEach(sound -> {
            if (sound != null) {
                sound.dispose();
            }
        });

        music.values().forEach(m -> {
            if (m != null) {
                m.dispose();
            }
        });
    }
}
