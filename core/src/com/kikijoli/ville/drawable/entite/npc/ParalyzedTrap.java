/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.drawable.entite.npc;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kikijoli.ville.business.AbstractBusiness;
import com.kikijoli.ville.business.ParalyzedTrapBusiness;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.interfaces.IBusiness;
import com.kikijoli.ville.util.Constantes;

/**
 *
 * @author troïmaclure
 */
public final class ParalyzedTrap extends Entite implements IBusiness {

    private static final String TRAP = "sprite/trap-paralyzed.png";
    public boolean hided = true;

    public ParalyzedTrap(float srcX, float srcY) {
        super(TRAP, srcX, srcY, Constantes.TILESIZE, Constantes.TILESIZE);
        this.buisiness = this.getDefault();
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (this.hided) batch.setColor(new Color(Color.WHITE.r, Color.WHITE.g, Color.WHITE.b, 0.1f));
        super.draw(batch);
        batch.setColor(Color.WHITE);
    }

    @Override
    public AbstractBusiness getDefault() {
        return new ParalyzedTrapBusiness(this);
    }

}
