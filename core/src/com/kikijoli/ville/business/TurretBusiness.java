/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.business;

import com.badlogic.gdx.math.Intersector;
import com.kikijoli.ville.abstracts.AbstractAction;
import com.kikijoli.ville.automation.ennemy.AttackDirectionPreparation;
import com.kikijoli.ville.automation.player.AttackTurret;
import com.kikijoli.ville.drawable.entite.npc.Turret;
import com.kikijoli.ville.drawable.entite.simple.TurretBow;
import com.kikijoli.ville.manager.EntiteManager;
import com.kikijoli.ville.manager.SoundManager;
import com.kikijoli.ville.shader.ClickShader;
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
        private static final String PREPARATION = "PREPARATION";

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
            if (!actions.containsKey(BOW) && !actions.containsKey(PREPARATION) && countBow++ > bowDelay && !isContacted()) {
                countBow = 0;
                SoundManager.playSound(SoundManager.PREPARE_SPELL);
                actions.put(PREPARATION, new AttackDirectionPreparation(turret, MathUtils.centered(turret, new TurretBow(0, 0))) {
                    @Override
                    public void onComplete() {
                        actions.remove(PREPARATION);
                        SoundManager.playSound(SoundManager.CANNON);
                        actions.put(BOW, new AttackTurret(turret, destination) {
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
            if (turret.vision.contains(EntiteManager.player.getX(), EntiteManager.player.getY())) {
                current = new AttackPlayer();
            }
        }

    }

}
