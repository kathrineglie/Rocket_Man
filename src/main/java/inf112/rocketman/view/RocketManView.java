package inf112.rocketman.view;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.*;

import inf112.rocketman._example.view.Painter;
import inf112.rocketman.grid.IGrid;


public class RocketManView implements Painter {

   
    private Viewport viewport; // defines screen / world size, aspect ratio and camera
	private SpriteBatch batch; // used for drawing images / textures
	private BitmapFont font; // used for writing
	private ShapeRenderer shape; // used for drawing shapes
	private Map<String, Texture> textures = new HashMap<>();
	private Sound blippSound;
	private GridRenderer gridRenderer;
	// unused

	public void create(double worldWidth, double worldHeight) {

		this.viewport = new FillViewport((float) worldWidth, (float) worldHeight);
		this.batch = new SpriteBatch();
		this.shape = new ShapeRenderer();
		this.blippSound = Gdx.audio.newSound(Gdx.files.internal("blipp.ogg"));
		this.gridRenderer = new GridRenderer(0f, 0f, true);

		this.font = new BitmapFont();
		font.setColor(Color.RED);

		preloadTextures();

		Gdx.graphics.setForegroundFPS(60);
	}

	/*
	 * Loading everything at startup prevents glitches later on.
	 */
	private void preloadTextures() {
		for (String fileName : List.of("tevje.png", "background.png")) {
			getTexture(fileName);
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
	private Texture getTexture(String name) {
		if (!textures.containsKey(name)) {
			FileHandle file = Gdx.files.internal(name);
			if (file != null)
				textures.put(name, new Texture(file));
			else
				textures.put(name, null);
		}
		return textures.get(name);
	}

	public void dispose() {

		batch.dispose();
		shape.dispose();
		blippSound.dispose();
		font.dispose();
		textures.values().forEach(tex -> {
			if (tex != null)
				tex.dispose();
		});
	}

	@Override
	public void draw(double x, double y, double w, double h, String textureName) {
		Texture texture = getTexture(textureName);
		if (texture != null) {
			batch.draw(texture, //
					(float) x, //
					(float) y, //
					(float) w, //
					(float) h);
		}
	}

	public void playSound() {
		// TODO: play different sounds
		blippSound.play();
	}

	/**
	 * Example:
	 * 
	 * {@snippet :
	 * appView.render(painter -> {
	 * 	painter.draw(x, y, w, h, tex1);
	 * 	painter.draw(x, y, w, h, tex2);
	 * });
	 * }
	 * 
	 * TODO: probably easier to just accept a list of objects to draw.
	 * 
	 * See lectures for better examples.
	 * 
	 * Doing it this way has the advantage of making sure that SpriteBatch's begin()
	 * and end() methods are called properly.
	 * 
	 * @param drawCommands
	 */
	public void render(ViewableRocketManModel model) {

		ScreenUtils.clear(Color.WHITE);

		float worldW = viewport.getWorldWidth();
		float worldH = viewport.getWorldHeight();

		renderGrid(model.getGrid(), gridRenderer);

		batch.begin();

		batch.setProjectionMatrix(viewport.getCamera().combined);

		draw(0, 0, worldW, worldH, "background.png");
		draw(model.getPlayerX(), model.getPlayerY(), model.getPlayerWidth(), model.getPlayerHeight(), "tevje.png");

		batch.end();
	}

	public void resize(int width, int height) {

		viewport.update(width, height, true);
	}

	public double worldWidth() {
		return viewport.getWorldWidth();
	}

	public double worldHeight() {
		return viewport.getWorldHeight();
	}

	public void renderGrid(IGrid grid, GridRenderer renderer){
		shape.setProjectionMatrix(viewport.getCamera().combined);
		shape.setColor(Color.LIGHT_GRAY);

		float worldW = viewport.getWorldWidth();
		float worldH = viewport.getWorldHeight();
		renderer.draw(shape, grid, worldW, worldH);
	}

	public float getWorldWidth(){
		return viewport.getWorldWidth();
	}

	public float getWorldHeight(){
		return viewport.getWorldHeight();
	}

}
