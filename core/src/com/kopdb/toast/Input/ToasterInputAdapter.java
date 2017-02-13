package com.kopdb.toast.Input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
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

    public ToasterInputAdapter(MainMenuScreen menu, Sprite toasterLever, float top, float bottom) {
        mainMenu = menu;
        lever = toasterLever;
        leverTop=top;
        leverBotom=bottom;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 in = new Vector3(screenX, screenY, 0);
        ToastGame.getCamera().unproject(in);
        screenX=(int)in.x;
        screenY=(int)in.y;
        touchOffset = screenY - lever.getY()+3;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (done){return true;}
        Vector3 in = new Vector3(screenX, screenY, 0);
        ToastGame.getCamera().unproject(in);
        screenX=(int)in.x;
        screenY=(int)in.y;
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
