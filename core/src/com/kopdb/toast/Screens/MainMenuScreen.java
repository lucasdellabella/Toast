package com.kopdb.toast.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kopdb.toast.ToastGame;

/**
 * Created by O607 on 1/27/2017.
 */

public class MainMenuScreen implements Screen {

    private final ToastGame game;
    private Stage stage;
    Button playButton;

    private Vector2 camTarget=new Vector2(ToastGame.getCamera().viewportWidth / 2, ToastGame.getCamera().viewportHeight / 2);
    private Boolean startGame = false;

    public MainMenuScreen(ToastGame game) {
        this.game = game;
        stage = new Stage(game.getViewport());

        // Create UI elements
        playButton = new ImageButton(new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("buttertoast.png")))));
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                camTarget.x += 100;
                startGame = true;
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
        //tweens camera to target and launches game if the camera reached the target and the game is meant to launch
        Vector2 camDiff = camTarget.cpy().sub(ToastGame.getCamera().position.x, ToastGame.getCamera().position.y);
        if (startGame && camDiff.len()<2)
        {
            switchToGameScreen();
            startGame = false;
        }
        camDiff.scl(0.1f);
        ToastGame.getCamera().translate(camDiff);

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
        ToastGame.getCamera().position.set(ToastGame.getCamera().viewportWidth / 2, ToastGame.getCamera().viewportHeight / 2, 0);
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
        //uiSkin.dispose();
    }
}
