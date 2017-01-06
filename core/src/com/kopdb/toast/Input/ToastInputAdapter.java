package com.kopdb.toast.Input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.utils.Array;
import com.kopdb.toast.Toast;

/**
 * Created by lucasdellabella on 1/5/17.
 */
public class ToastInputAdapter extends InputAdapter {

    Array<Toast> toastArray;
    Toast draggingToast;

    public ToastInputAdapter(Array<Toast> toastArray) {
        this.toastArray = toastArray;
    }

    private int convertToYUp(int yDown) {
        return Gdx.graphics.getHeight() - 1 - yDown;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // start tweening
        for (Toast toast: toastArray) {
            toast.checkTouchDown(screenX, convertToYUp(screenY));
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        // release toast
        for (Toast toast: toastArray) {
            toast.checkTouchUp(screenX, convertToYUp(screenY));
        }
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        for (Toast toast: toastArray) {
            toast.checkTouchDragged(screenX, convertToYUp(screenY));
        }
        return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}

