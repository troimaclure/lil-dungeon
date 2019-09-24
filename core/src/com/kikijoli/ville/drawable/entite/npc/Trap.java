/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.drawable.entite.npc;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.business.AbstractBusiness;
import com.kikijoli.ville.business.TrapBusiness;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.interfaces.IBusiness;
import com.kikijoli.ville.util.Constantes;
import com.kikijoli.ville.util.MathUtils;

/**
 *
 * @author troïmaclure
 */
public final class Trap extends Entite implements IBusiness {

    private static final String TRAP = "sprite/floor.png";

    public Trap(int srcX, int srcY) {
        super(TRAP, srcX, srcY, Constantes.TILESIZE, Constantes.TILESIZE);
        this.buisiness = this.getDefault();
    }

    @Override
    public void draw(SpriteBatch batch) {
//        super.draw(batch);
    }

    @Override
    public AbstractBusiness getDefault() {
        return new TrapBusiness(this);
    }

    public Circle getBoundsEffect() {
        Vector2 center = MathUtils.getCenter(this.getBoundingRectangle());
        return new Circle(center.x, center.y, Constantes.TILESIZE * 2);
    }
}
