/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.drawable.entite.npc;

import box2dLight.PointLight;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kikijoli.ville.business.AbstractBusiness;
import com.kikijoli.ville.business.ArcherBusiness;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.interfaces.IBusiness;
import com.kikijoli.ville.maps.Tmap;
import com.kikijoli.ville.util.Constantes;

/**
 *
 * @author troïmaclure
 */
public final class Archer extends Entite implements IBusiness {

    private static final String ARCHER = "sprite/archer.png";

    public PointLight vision;

    public Archer(int srcX, int srcY) {
        super(ARCHER, srcX, srcY, Constantes.TILESIZE / 4, Constantes.TILESIZE / 2);
        this.buisiness = this.getDefault();
        this.vision = new PointLight(Tmap.getRay(), 40, Color.BLACK, 500, this.getX(), this.getY());
        this.vision.setSoft(false);
    }

    @Override
    public void draw(SpriteBatch batch) {
        this.vision.setPosition(this.getX(), this.getY());
        super.draw(batch);
    }

    @Override
    public AbstractBusiness getDefault() {
        return new ArcherBusiness(this);
    }
}
