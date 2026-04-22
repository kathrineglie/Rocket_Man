package inf112.rocketman.view;

import com.badlogic.gdx.graphics.Texture;

/**
 * Interface for providing textures to the view components.
 * This allows for a decoupled architecture where the renderer does not need
 * to know how textures are loaded or stored.
 */
public interface TextureProvider {

    /**
     * Retrieves a texture based on its file path or identifier.
     * Implementation should ideally cache textures to avoid redundant
     * disk I/O and memory consumption.
     *
     * @param name the internal path tp the texture asset (e.g., "obstacles/Rocket.png")
     * @return the {@link Texture} associated with the given name,
     * or null if the texture could not be found
     */
    Texture getTexture(String name);
}
