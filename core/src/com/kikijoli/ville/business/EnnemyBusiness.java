package com.kikijoli.ville.business;

import com.kikijoli.ville.drawable.entite.npc.Ennemy;
import com.kikijoli.ville.manager.EntiteManager;

/**
 *
 * @author ajosse
 */
public abstract class EnnemyBusiness extends AbstractBusiness {

    Ennemy ennemy;

    public EnnemyBusiness(Ennemy ennemy) {
        this.ennemy = ennemy;
    }

    @Override
    public void act() {
        if (ennemy.prevent != null) {
            if (ennemy.see(EntiteManager.player)) {
                ennemy.prevent = null;
                return;
            }
            if (!ennemy.prevent.count.stepAndComplete()) {
                ennemy.prevent.runnable.run();
                return;
            } else {
                ennemy.prevent = null;
            }
        }
        super.act(); //To change body of generated methods, choose Tools | Templates.
    }

}
