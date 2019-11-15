/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.drawable.entite.object;

import com.badlogic.gdx.graphics.Color;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.manager.EntiteManager;

/**
 *
 * @author Arthur
 */
public class ArrowObject extends Entite implements IObject {

    public ArrowObject(float x, float y) {
        super("sprite/arrow_to_get.png", x, y);
    }

    @Override
    public void get() {
        EntiteManager.player.talkDouble("Arrow +1", Color.BLACK, Color.CYAN);
        EntiteManager.arrowCount += 1;
    }
}
