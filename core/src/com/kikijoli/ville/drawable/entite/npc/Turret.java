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
import com.kikijoli.ville.component.IComponent;
import com.kikijoli.ville.component.TurretComponent;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.interfaces.IBusiness;
import com.kikijoli.ville.manager.EntiteManager;
import com.kikijoli.ville.maps.Tmap;
import com.kikijoli.ville.util.Constantes;
import com.kikijoli.ville.util.MathUtils;
import java.util.Arrays;

/**
 *
 * @author troïmaclure
 */
public final class Turret extends Entite implements IBusiness {

    private static final String TURRET = "sprite/turret.png";

    public PointLight vision;

    public Turret(int srcX, int srcY) {
        super(TURRET, srcX, srcY, Constantes.TILESIZE, Constantes.TILESIZE);
        this.buisiness = this.getDefault();
        this.vision = new PointLight(Tmap.getRay(), 20, Color.BLACK, 500, this.getX(), this.getY());
        this.vision.setXray(true);
        this.point = 1500;
        this.components.addAll(Arrays.asList(new IComponent[]{new TurretComponent(this, (t) -> {
            return MathUtils.getCenter(EntiteManager.player.getBoundingRectangle());
        })}));
        this.currentComponent = this.components.get(0);
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
