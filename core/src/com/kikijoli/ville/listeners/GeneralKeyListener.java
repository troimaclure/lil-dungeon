/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.listeners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.kikijoli.ville.abstracts.AbstractAction;
import com.kikijoli.ville.manager.CameraManager;
import com.kikijoli.ville.manager.ColorManager;
import com.kikijoli.ville.manager.EntiteManager;

/**
 *
 * @author troïmaclure
 */
public class GeneralKeyListener extends InputAdapter {

    public static boolean KeyLeft, KeyUp, KeyDown, KeyRight;
    public static AbstractAction onTouchAction;
    public static boolean dragged;
    public static boolean rightButton;

    @Override
    public boolean keyDown(int keycode) {

        switch (keycode) {
            case Keys.LEFT:
            case Keys.Q:
            case Keys.A:
                KeyLeft = true;
                break;
            case Keys.UP:
            case Keys.Z:
                KeyUp = true;
                break;
            case Keys.DOWN:
            case Keys.S:
                KeyDown = true;
                break;
            case Keys.RIGHT:
            case Keys.E:
            case Keys.D:
                KeyRight = true;
                break;
            case Keys.T:
                ColorManager.toggle();
                break;
        }

        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Keys.LEFT:
            case Keys.Q:
            case Keys.A:
                KeyLeft = false;
                break;
            case Keys.UP:
            case Keys.Z:
                KeyUp = false;
                break;
            case Keys.DOWN:
            case Keys.S:
                KeyDown = false;
                break;
            case Keys.RIGHT:
            case Keys.E:
            case Keys.D:
                KeyRight = false;
                break;
        }

        return super.keyUp(keycode);
    }

    @Override
    public boolean scrolled(int amount) {
        if ((CameraManager.camera.zoom + ((float) amount / 5) > 0.5f && CameraManager.camera.zoom + ((float) amount / 5) < 6.5f)) {
            CameraManager.camera.zoom += ((float) amount / 5);
            CameraManager.camera.update();
        }
        return super.scrolled(amount);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        rightButton = button == Input.Buttons.RIGHT;
        return super.touchDown(screenX, screenY, pointer, button); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        rightButton = false;
        dragged = false;
        return super.touchDown(screenX, screenY, pointer, button); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        dragged = true;
        return super.touchDragged(screenX, screenY, pointer);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {

        return super.mouseMoved(screenX, screenY);
    }

}
