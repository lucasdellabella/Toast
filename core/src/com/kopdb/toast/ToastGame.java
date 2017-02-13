package com.kopdb.toast;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kopdb.toast.Screens.MainMenuScreen;

import javax.swing.text.View;

public class ToastGame extends Game {
	public static float BOX_2D_SCALE = 0.01f;//Multiply going in divide going out
    public static ObjectMap<String, Texture> typeTextures = new ObjectMap<>();
	public static ObjectMap<String, Sound> typeTouchSounds = new ObjectMap<>();
	public static ObjectMap<String, Sound> typeFlickSounds = new ObjectMap<>();
	private static SpriteBatch batch;
	private static World world;
	private static OrthographicCamera camera;
	private static Viewport viewport;

    @Override
	public void create () {
		batch = new SpriteBatch();
		world = new World(new Vector2(0, -300f * BOX_2D_SCALE), true);

        // Set up camera + viewport
		camera = new OrthographicCamera();
		getCamera().setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		float aspect = (float)Gdx.graphics.getHeight()/(float)Gdx.graphics.getWidth();
		viewport = new FitViewport(540, 540 * aspect, getCamera());
		viewport.apply();
		getCamera().position.set(getCamera().viewportWidth / 2, getCamera().viewportHeight / 2, 0);

        // Fill in typeTexture map
        typeTextures.put("stache", new Texture(Gdx.files.internal("Toasts/stacheToast.png")));
        typeTextures.put("happy", new Texture(Gdx.files.internal("Toasts/HappyToast.png")));
		typeTextures.put("chef", new Texture(Gdx.files.internal("Toasts/cheftoast.png")));
		typeTextures.put("explorer", new Texture(Gdx.files.internal("Toasts/explorertoast.png")));
		typeTextures.put("angry", new Texture(Gdx.files.internal("Toasts/AngryToast.png")));
		typeTextures.put("jelly", new Texture(Gdx.files.internal("Toasts/jellytoast.png")));
		typeTextures.put("sad", new Texture(Gdx.files.internal("Toasts/SadToast.png")));
		typeTextures.put("worried", new Texture(Gdx.files.internal("Toasts/WorriedToast.png")));

		// Fill in typeTouchSounds map
		typeTouchSounds.put("stache", Gdx.audio.newSound(Gdx.files.internal("audio/sadtouch.wav")));
		typeTouchSounds.put("happy", Gdx.audio.newSound(Gdx.files.internal("audio/sadtouch.wav")));
		typeTouchSounds.put("chef", Gdx.audio.newSound(Gdx.files.internal("audio/sadtouch.wav")));
		typeTouchSounds.put("explorer", Gdx.audio.newSound(Gdx.files.internal("audio/explorertouch.wav")));
		typeTouchSounds.put("angry", Gdx.audio.newSound(Gdx.files.internal("audio/explorertouch.wav")));
		typeTouchSounds.put("jelly", Gdx.audio.newSound(Gdx.files.internal("audio/explorertouch.wav")));
		typeTouchSounds.put("sad", Gdx.audio.newSound(Gdx.files.internal("audio/explorertouch.wav")));
		typeTouchSounds.put("worried", Gdx.audio.newSound(Gdx.files.internal("audio/explorertouch.wav")));

		// Fill in typeFlickSounds map
		typeFlickSounds.put("stache", Gdx.audio.newSound(Gdx.files.internal("audio/sadflick.wav")));
		typeFlickSounds.put("happy", Gdx.audio.newSound(Gdx.files.internal("audio/sadflick.wav")));
		typeFlickSounds.put("chef", Gdx.audio.newSound(Gdx.files.internal("audio/chefflick.wav")));
		typeFlickSounds.put("explorer", Gdx.audio.newSound(Gdx.files.internal("audio/explorerflick" +
				".wav")));
		typeFlickSounds.put("angry", Gdx.audio.newSound(Gdx.files.internal("audio/chefflick.wav")));
		typeFlickSounds.put("jelly", Gdx.audio.newSound(Gdx.files.internal("audio/chefflick.wav")));
		typeFlickSounds.put("sad", Gdx.audio.newSound(Gdx.files.internal("audio/chefflick.wav")));
		typeFlickSounds.put("worried", Gdx.audio.newSound(Gdx.files.internal("audio/chefflick.wav")));

		this.setScreen(new MainMenuScreen(this));
	}

    public static SpriteBatch getBatch() {
        return batch;
    }

    public static World getWorld() {
        return world;
    }

    public static OrthographicCamera getCamera() {
        return camera;
    }

    public static Viewport getViewport() {
        return viewport;
    }

	@Override
	public void dispose () {
		getBatch().dispose();
	}
}
