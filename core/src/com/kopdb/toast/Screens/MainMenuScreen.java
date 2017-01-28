package com.kopdb.toast.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kopdb.toast.ToastGame;
import com.sun.xml.internal.bind.annotation.OverrideAnnotationOf;

/**
 * Created by O607 on 1/27/2017.
 */

public class MainMenuScreen implements Screen {

    private final ToastGame game;
    private Stage stage;
    TextButton playButton;
    Skin uiSkin;

    public MainMenuScreen(ToastGame game) {
        this.game = game;
        uiSkin = new Skin(Gdx.files.internal("uiskin.json"), new TextureAtlas("uiskin.atlas"));
        stage = new Stage(game.getViewport());

        // Create UI elements
        playButton = new TextButton("Get Toasty", uiSkin);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                switchToGameScreen();
            }
        });

        // Add UI elements to stage for drawing + input processing
        stage.addActor(playButton);

        // Use stage as input processor
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        ToastGame.getCamera().update();
        ToastGame.getBatch().setProjectionMatrix(ToastGame.getCamera().combined);
        ToastGame.getBatch().begin();
        stage.draw();
        ToastGame.getBatch().end();

        stage.act(delta);
    }

    private void switchToGameScreen() {
        game.getScreen().dispose();
        game.setScreen(new GameScreen(game));
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
        uiSkin.dispose();
    }
}
