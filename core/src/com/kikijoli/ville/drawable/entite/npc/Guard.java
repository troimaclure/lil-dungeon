/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.drawable.entite.npc;

import box2dLight.ConeLight;
import com.badlogic.gdx.graphics.Color;
import com.kikijoli.ville.business.GuardBuisiness;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kikijoli.ville.business.AbstractBusiness;
import com.kikijoli.ville.component.IComponent;
import com.kikijoli.ville.component.SwordComponent;
import com.kikijoli.ville.drawable.entite.simple.PlayerShield;
import com.kikijoli.ville.interfaces.IBusiness;
import com.kikijoli.ville.maps.Tmap;
import java.util.Arrays;

/**
 *
 * @author troïmaclure
 */
public class Guard extends Npc implements IBusiness {

    private static final String GUARD = "sprite/samourai.png";

    public ConeLight vision;

    public Guard(float srcX, float srcY) {
        super(GUARD, srcX, srcY);
        this.buisiness = this.getDefault();
//        this.vision = new PointLight(Tmap.getRay(), 40, Color.BLACK, 500, this.getX(), this.getY());
        this.vision = new ConeLight(Tmap.getRay(), 500, Color.BLACK, 500, srcX, srcY, 0, 45);
        this.vision.attachToBody(getBody(), 0, 0, -90f);
        this.vision.setIgnoreAttachedBody(true);
//        this.vision.setStaticLight(true);
//        this.vision.setSoft(true);
        this.shield = new PlayerShield(srcX, srcY);
        this.point = 1000;
        this.components.addAll(Arrays.asList(new IComponent[]{new SwordComponent(this)}));
        this.currentComponent = this.components.get(0);

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
