/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.business;

import com.kikijoli.ville.abstracts.AbstractAction;
import com.kikijoli.ville.drawable.entite.npc.RollingTrap;
import com.kikijoli.ville.manager.EntiteManager;
import com.kikijoli.ville.pathfind.GridManager;
import com.kikijoli.ville.util.Constantes;
import com.kikijoli.ville.util.MathUtils;

/**
 *
 * @author Arthur
 */
public class RollingTrapBusiness extends AbstractBusiness {

    RollingTrap rolling;

    public RollingTrapBusiness(RollingTrap rolling) {
        this.rolling = rolling;
    }

    @Override
    public AbstractAction getDefault() {
        return new Rolling();
    }

    private class Rolling extends AbstractAction {

        @Override
        public void act() {
            rolling.setX(rolling.getX() + (rolling.speedX));
            rolling.setY(rolling.getY() + (rolling.speedY));
            if (!GridManager.isClearZone(rolling.getBoundingRectangle(), Constantes.NPC_MOVEMENT_OK)) {
                rolling.speedX *= -1;
                rolling.speedY *= -1;
            }
            if (rolling.getBoundingRectangle().contains(MathUtils.getCenter(EntiteManager.player.getBoundingRectangle()))) {
                EntiteManager.touch(EntiteManager.player);
            }
        }
    }

}
