package com.kopdb.toast.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.kopdb.toast.ToastGame;

/**
 * Created by O607 on 1/27/2017.
 */

public class MainMenuScreen implements Screen {

    TextButton playButton;
    Skin uiSkin;

    public MainMenuScreen() {
        uiSkin = new Skin(Gdx.files.internal("uiskin.json"), new TextureAtlas("uiskin.atlas"));
        playButton=new TextButton("Get Toasty", uiSkin);
        playButton.setPosition(0,0);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        ToastGame.camera.update();
        ToastGame.batch.setProjectionMatrix(ToastGame.camera.combined);

        ToastGame.batch.begin();

        playButton.draw(ToastGame.batch,1);

        ToastGame.batch.end();
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
