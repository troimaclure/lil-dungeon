/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.drawable.entite.object;

import com.badlogic.gdx.graphics.Color;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.manager.EntiteManager;
import com.kikijoli.ville.manager.SoundManager;
import com.kikijoli.ville.util.Constantes;

/**
 *
 * @author troïmaclure
 */
public class Key extends Entite implements IObject {

    private static final String SPRITEKEYPNG = "sprite/key.png";

    public Key(float srcX, float srcY) {
        super(SPRITEKEYPNG, srcX, srcY, Constantes.TILESIZE / 2, Constantes.TILESIZE / 2);
    }

    @Override
    public void get() {
        EntiteManager.keys.add(this);
        EntiteManager.player.talkDouble("Got key !", Color.BLACK, Color.ORANGE);
        SoundManager.playSound(SoundManager.TAKE_KEY);

    }

}
