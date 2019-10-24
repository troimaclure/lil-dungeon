/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.drawable.entite.decor;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kikijoli.ville.maps.Tmap;
import com.kikijoli.ville.util.Constantes;

/**
 *
 * @author troïmaclure
 */
public class Stone extends Decor {

    public Stone(int srcX, int srcY) {
        super("sprite/stone.png", srcX, srcY, Constantes.TILESIZE, Constantes.TILESIZE);
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }

}
