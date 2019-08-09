/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.drawable.entite.npc;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.util.Constantes;

/**
 *
 * @author troïmaclure
 */
public class Npc extends Entite {

    private static final String SPRITESIMPLEPNG = "sprite/simple.png";

    public int speed = 2;

    public Npc(int srcX, int srcY) {
        super(SPRITESIMPLEPNG, srcX, srcY, Constantes.TILESIZE / 4, Constantes.TILESIZE / 2);
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);

    }
}
