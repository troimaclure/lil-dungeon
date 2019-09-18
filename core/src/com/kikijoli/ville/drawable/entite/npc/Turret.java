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
import com.kikijoli.ville.business.TurretBusiness;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.interfaces.IBusiness;
import com.kikijoli.ville.maps.Tmap;
import com.kikijoli.ville.util.Constantes;

/**
 *
 * @author troïmaclure
 */
public final class Turret extends Entite implements IBusiness {

    private static final String turret = "sprite/turret.png";

    public PointLight vision;

    public Turret(int srcX, int srcY) {
        super(turret, srcX, srcY, Constantes.TILESIZE, Constantes.TILESIZE);
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
        return new TurretBusiness(this);
    }
}
