package com.kopdb.toast.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Richard on 1/5/2017.
 */

public class GameScreen implements Screen {
    SpriteBatch spriteBatch;
    Sprite toaster;
    public GameScreen(SpriteBatch batch)
    {
        spriteBatch = batch;
        toaster=new Sprite();
        toaster.setPosition(40,40);
        toaster.setTexture(new Texture(Gdx.files.internal("toaster.jpg")));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        float height = toaster.getTexture().getHeight() / toaster.getTexture().getWidth() * Gdx.graphics.getWidth() * 1.2f;
        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.begin();
        spriteBatch.draw(toaster.getTexture(),
                0,
                60,
                toaster.getTexture().getWidth(),//Gdx.graphics.getWidth() * 1.2f,
                toaster.getTexture().getHeight());
        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
