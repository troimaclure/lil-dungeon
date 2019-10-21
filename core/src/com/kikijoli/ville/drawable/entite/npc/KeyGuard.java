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
import com.kikijoli.ville.business.KeyGuardBuisiness;
import com.kikijoli.ville.component.SwordComponent;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.drawable.entite.simple.PlayerShield;
import com.kikijoli.ville.interfaces.IBusiness;
import com.kikijoli.ville.manager.LockManager;
import com.kikijoli.ville.maps.Tmap;
import com.kikijoli.ville.util.Constantes;

/**
 *
 * @author troïmaclure
 */
public final class KeyGuard extends Entite implements IBusiness {

    private static final String KeyGuard = "sprite/KeyGuard.png";
    public PointLight vision;

    public KeyGuard(int srcX, int srcY) {
        super(KeyGuard, srcX, srcY, Constantes.TILESIZE, Constantes.TILESIZE);

        this.buisiness = this.getDefault();
        this.vision = new PointLight(Tmap.getRay(), 40, Color.BLACK, 500, this.getX(), this.getY());
        this.vision.setSoft(false);
        this.shield = new PlayerShield(srcX, srcY, getWidth(), getHeight());
        this.point = 1500;
        this.currentComponent = new SwordComponent(this);

    }

    @Override
    public void draw(SpriteBatch batch) {
        this.vision.setPosition(this.getX(), this.getY());
        super.draw(batch);
    }

    @Override
    public AbstractBusiness getDefault() {
        return new KeyGuardBuisiness(this);
    }

    @Override
    public void dead() {
        LockManager.addKey(this.getX(), this.getY());
    }

}
