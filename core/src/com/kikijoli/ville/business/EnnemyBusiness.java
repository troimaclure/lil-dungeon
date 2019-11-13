package com.kikijoli.ville.business;

import com.kikijoli.ville.drawable.entite.npc.Ennemy;

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
            if (!ennemy.prevent.count.stepAndComplete()) {
                ennemy.prevent.runnable.run();
                return;
            } else {
                System.out.println("stop");
                ennemy.prevent = null;
            }
        }
        super.act(); //To change body of generated methods, choose Tools | Templates.
    }

}
