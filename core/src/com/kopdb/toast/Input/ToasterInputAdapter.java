package com.kopdb.toast.Input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.kopdb.toast.Screens.MainMenuScreen;
import com.kopdb.toast.ToastGame;

/**
 * Created by O607 on 2/11/2017.
 */

public class ToasterInputAdapter extends InputAdapter {

    MainMenuScreen mainMenu;
    Sprite lever;
    float leverTop;
    float leverBotom;
    float touchOffset;
    boolean done=false;

    private int convertToYUp(int yDown) {
        return Gdx.graphics.getHeight() - 1 - yDown;
    }

    public ToasterInputAdapter(MainMenuScreen menu, Sprite toasterLever, float top, float bottom) {
        mainMenu = menu;
        lever = toasterLever;
        leverTop=top;
        leverBotom=bottom;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenY=convertToYUp(screenY);
        touchOffset = screenY - lever.getY()+3;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (done){return true;}
        screenY=convertToYUp(screenY);
        if (lever.getBoundingRectangle().contains(screenX, screenY)) {
            if (screenY-touchOffset<leverBotom) {
                done = true;
                mainMenu.toasterSwitchSet();
                return true;
            }
            if (screenY-touchOffset>leverTop) {
                return true;
            }
            lever.setPosition(lever.getX(),screenY-touchOffset);
        }

        return true;
    }
}
