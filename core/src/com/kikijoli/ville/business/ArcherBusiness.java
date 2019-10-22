/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.business;

import com.kikijoli.ville.abstracts.AbstractAction;
import com.kikijoli.ville.automation.ennemy.AttackBowEnnemy;
import com.kikijoli.ville.drawable.entite.npc.Archer;
import com.kikijoli.ville.manager.EntiteManager;
import com.kikijoli.ville.manager.SoundManager;
import com.kikijoli.ville.util.MathUtils;

/**
 *
 * @author Arthur
 */
public class ArcherBusiness extends AbstractBusiness {

    Archer archer;

    public ArcherBusiness(Archer guard) {
        this.archer = guard;
    }

    @Override
    public AbstractAction getDefault() {
        return new WaitPlayer();
    }

    private class AttackPlayer extends AbstractAction {

        private static final String BOW = "BOW";

        @Override
        public void act() {

            handleBow();
        }

        private void handleBow() {
            if (!actions.containsKey(BOW)) {
                SoundManager.playSound(SoundManager.PREPARE_SPELL);
                SoundManager.playSound(SoundManager.BOW);
                actions.put(BOW, new AttackBowEnnemy(archer, MathUtils.getCenter(EntiteManager.player.getBoundingRectangle())) {
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
            if (archer.vision.contains(EntiteManager.player.getX(), EntiteManager.player.getY())) {
                current = new AttackPlayer();
            }
        }

    }

}
