/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.drawable.entite.npc;

import box2dLight.PointLight;
import com.badlogic.gdx.graphics.Color;
import com.kikijoli.ville.business.GuardBuisiness;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kikijoli.ville.business.AbstractBusiness;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.interfaces.IBusiness;
import com.kikijoli.ville.maps.Tmap;
import com.kikijoli.ville.util.Constantes;

/**
 *
 * @author troïmaclure
 */
public final class Guard extends Entite implements IBusiness {

    private static final String GUARD = "sprite/guard.png";

    public PointLight vision;

    public Guard(int srcX, int srcY) {
        super(GUARD, srcX, srcY, Constantes.TILESIZE / 4, Constantes.TILESIZE / 2);
        this.buisiness = this.getDefault();
        this.vision = new PointLight(Tmap.getRay(), 20, Color.BLACK, 500, this.getX(), this.getY());
    }

    @Override
    public void draw(SpriteBatch batch) {
        this.vision.setPosition(this.getX(), this.getY());
        super.draw(batch);
    }

    @Override
    public AbstractBusiness getDefault() {
        return new GuardBuisiness(this);
    }
}
