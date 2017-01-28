package com.kopdb.toast;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.kopdb.toast.Screens.GameScreen;
import com.kopdb.toast.Screens.MainMenuScreen;

public class ToastGame extends Game {
	public static float BOX_2D_SCALE = 0.01f;//Multiply going in divide going out
	public static SpriteBatch batch;
	public static World world;
	public static OrthographicCamera camera;
	public static Viewport viewport;

	@Override
	public void create () {
		batch = new SpriteBatch();
		world = new World(new Vector2(0, -98f * BOX_2D_SCALE), true);
		this.setScreen(new MainMenuScreen());
		// Set up camera + viewport
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
		viewport.apply();
		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
