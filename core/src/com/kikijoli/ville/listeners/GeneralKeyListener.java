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
import com.kikijoli.ville.business.PlayerBusiness;
import com.kikijoli.ville.manager.EntiteManager;
import com.kikijoli.ville.manager.HudManager;
import com.kikijoli.ville.manager.StageManager;
import com.kikijoli.ville.manager.ThemeManager;
import com.kikijoli.ville.maps.Tmap;

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
            case Keys.I:
                EntiteManager.player.invincible = true;
                break;
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
            case Keys.D:
            case Keys.E:
                KeyRight = true;
                break;
            case Keys.NUM_1:
                tilesAction(0);
                break;
            case Keys.NUM_2:
                tilesAction(1);
                break;
            case Keys.NUM_3:
                tilesAction(2);
                break;
            case Keys.NUM_4:
                tilesAction(3);
                break;
            case Keys.NUM_5:
                tilesAction(4);
                break;
            case Keys.NUM_6:
                tilesAction(5);
                break;
            case Keys.NUM_7:
                tilesAction(6);
                break;
            case Keys.NUM_8:
                tilesAction(7);
                break;
            case Keys.NUM_9:
                tilesAction(8);
                break;
            case Keys.ENTER:
//                if (EntiteManager.playerDead) {
                StageManager.reload();
//                }
                break;
            case Keys.ESCAPE:
                if (EntiteManager.playerDead) {
                    Gdx.app.exit();
                }
                break;

        }

        return super.keyDown(keycode);
    }

    private void tilesAction(int index) {
        if(EntiteManager.player.isDead())return ; 
        if (index > ThemeManager.currentTheme.getTiles().size() - 1) return;
        ThemeManager.currentTheme.getTiles().get(index).action();
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
            case Keys.D:
            case Keys.E:
                KeyRight = false;
                break;
        }

        return super.keyUp(keycode);
    }

    @Override
    public boolean scrolled(int amount) {
        HudManager.setSelected(amount);
        return super.scrolled(amount);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        rightButton = button == Input.Buttons.RIGHT;
        if (((PlayerBusiness) EntiteManager.player.business) != null)
            if (rightButton) {
                ((PlayerBusiness) EntiteManager.player.business).dash();
            } else {
                ((PlayerBusiness) EntiteManager.player.business).attack();
            }
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
