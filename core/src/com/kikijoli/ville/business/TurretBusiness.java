/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.business;

import com.badlogic.gdx.math.Intersector;
import com.kikijoli.ville.abstracts.AbstractAction;
import com.kikijoli.ville.automation.ennemy.AttackTurret;
import com.kikijoli.ville.drawable.entite.npc.Turret;
import com.kikijoli.ville.manager.EntiteManager;
import com.kikijoli.ville.manager.SoundManager;
import com.kikijoli.ville.util.MathUtils;

/**
 *
 * @author Arthur
 */
public class TurretBusiness extends AbstractBusiness {

    Turret turret;

    public TurretBusiness(Turret turret) {
        this.turret = turret;
    }

    @Override
    public AbstractAction getDefault() {
        return new WaitPlayer();
    }

    private class AttackPlayer extends AbstractAction {

        private static final String BOW = "BOW";

        int bowDelay = 150;
        int countBow = 150;

        @Override
        public void act() {
            handleBow();
        }

        private boolean isContacted() {
            return Intersector.overlaps(turret.anchor, EntiteManager.player.getBoundingRectangle());
        }

        private void handleBow() {
            if (!actions.containsKey(BOW) && countBow++ > bowDelay && !isContacted()) {
                countBow = 0;
                SoundManager.playSound(SoundManager.CANNON);
                actions.put(BOW, new AttackTurret(turret, MathUtils.getCenter(EntiteManager.player.getBoundingRectangle())) {
                    @Override
                    public void onFinish() {
                        actions.remove(BOW);
                    }
                });

            }
        }

    }

    public class WaitPlayer extends AbstractAction {

        @Override
        public void act() {
            if (turret.vision.contains(EntiteManager.player.getX(), EntiteManager.player.getY())) {
                current = new AttackPlayer();
            }
        }

    }

}
