/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.drawable.entite.npc;

import com.kikijoli.ville.business.AbstractBusiness;
import com.kikijoli.ville.business.RollingTrapBusiness;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.interfaces.IBusiness;
import com.kikijoli.ville.util.Constantes;

/**
 *
 * @author troïmaclure
 */
public final class RollingTrap extends Entite implements IBusiness {

    private static final String RollingTrap = "sprite/rolling_trap.png";

    public float speedX, speedY;

    public RollingTrap(int srcX, int srcY, float speedX, float speedY) {
        super(RollingTrap, srcX, srcY, Constantes.TILESIZE, Constantes.TILESIZE);
        this.speedX = speedX;
        this.speedY = speedY;
        this.buisiness = this.getDefault();
        this.point = 1500;
        isTouchable = false;
    }

    @Override
    public AbstractBusiness getDefault() {
        return new RollingTrapBusiness(this);
    }

    @Override
    public void dead() {
    }

}
