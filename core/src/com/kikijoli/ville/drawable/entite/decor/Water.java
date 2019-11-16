/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.drawable.entite.decor;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kikijoli.ville.manager.ParticleManager;
import com.kikijoli.ville.manager.ShaderManager;
import com.kikijoli.ville.util.Constantes;

/**
 *
 * @author troïmaclure
 */
public class Water extends Decor {

    public Water(float srcX, float srcY) {
        super("sprite/waterflat.png", srcX, srcY, Constantes.TILESIZE, Constantes.TILESIZE);
        this.shader = ShaderManager.waveShader;
        ParticleManager.addParticle("particle/wave.p", getX() + Constantes.TILESIZE / 2, getY() + Constantes.TILESIZE / 2, 1.0f);

    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }
}
