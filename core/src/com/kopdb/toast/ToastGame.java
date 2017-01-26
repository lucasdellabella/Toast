package com.kopdb.toast;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.kopdb.toast.Screens.GameScreen;

public class ToastGame extends Game {
	public static float box2dScale = 0.01f;//Multiply going in divide going out
	public SpriteBatch batch;
	public World world;

	@Override
	public void create () {
		batch = new SpriteBatch();
		world = new World(new Vector2(0, -98f * box2dScale), true);
		this.setScreen(new GameScreen(this));
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
