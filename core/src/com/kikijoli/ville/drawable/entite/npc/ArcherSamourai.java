/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.drawable.entite.npc;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.business.AbstractBusiness;
import com.kikijoli.ville.business.ArcherSamouraiBusiness;
import com.kikijoli.ville.component.BowComponent;
import com.kikijoli.ville.component.IComponent;
import com.kikijoli.ville.interfaces.IBusiness;
import com.kikijoli.ville.manager.EntiteManager;
import com.kikijoli.ville.util.MathUtils;
import java.util.Arrays;

/**
 *
 * @author troïmaclure
 */
public final class ArcherSamourai extends Ennemy implements IBusiness {

    private static final String ARCHER = "sprite/archersamourai.png";

    public ArcherSamourai(float srcX, float srcY) {
        super(ARCHER, srcX, srcY);
        this.buisiness = this.getDefault();
        this.point = 500;
        this.components.addAll(Arrays.asList(new IComponent[]{new BowComponent(this, (t) -> {
            if (isAlarmed && see(EntiteManager.player)) {
                return MathUtils.getCenter(EntiteManager.player.getBoundingRectangle());
            }
            return Vector2.Zero;
        })}));
        this.currentComponent = this.components.get(0);
        this.vision.setDistance(1000);
    }

    @Override
    public void draw(SpriteBatch batch) {
        this.vision.setPosition(this.getX(), this.getY());
        super.draw(batch);
    }

    @Override
    public AbstractBusiness getDefault() {
        return new ArcherSamouraiBusiness(this);
    }

    @Override
    public int getMinSpeed() {
        return 0;
    }

    @Override
    public int getMaxSpeed() {
        return 0;
    }

}
