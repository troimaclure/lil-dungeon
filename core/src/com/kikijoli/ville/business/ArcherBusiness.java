/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.business;

import com.kikijoli.ville.abstracts.AbstractAction;
import com.kikijoli.ville.automation.player.AttackBow;
import com.kikijoli.ville.automation.ennemy.AttackDirectionPreparation;
import com.kikijoli.ville.drawable.entite.npc.Archer;
import com.kikijoli.ville.drawable.entite.simple.Bow;
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
        private static final String PREPARATION = "PREPARATION";

        @Override
        public void act() {

            handleBow();
        }

        private void handleBow() {
            if (!actions.containsKey(BOW) && !actions.containsKey(PREPARATION)) {
                SoundManager.playSound(SoundManager.PREPARE_SPELL);
                actions.put(PREPARATION, new AttackDirectionPreparation(archer, MathUtils.centered(archer, new Bow(0, 0))) {
                    @Override
                    public void onComplete() {
                        actions.remove(PREPARATION);
                        SoundManager.playSound(SoundManager.BOW);
                        actions.put(BOW, new AttackBow(archer, destination) {
                            @Override
                            public void onFinish() {
                                actions.remove(BOW);
                            }
                        });
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
