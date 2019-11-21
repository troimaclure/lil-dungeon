/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.business;

import com.badlogic.gdx.graphics.Color;
import com.kikijoli.ville.abstracts.AbstractAction;
import com.kikijoli.ville.automation.ennemy.AttackBowEnnemy;
import com.kikijoli.ville.drawable.entite.npc.ArcherSamourai;
import com.kikijoli.ville.drawable.entite.npc.Ennemy;
import com.kikijoli.ville.manager.EntiteManager;
import com.kikijoli.ville.manager.SoundManager;
import com.kikijoli.ville.util.Constantes;
import com.kikijoli.ville.util.Count;
import com.kikijoli.ville.util.MathUtils;
import com.kikijoli.ville.util.Time;

/**
 *
 * @author Arthur
 */
public class ArcherSamouraiBusiness extends EnnemyBusiness {

    public ArcherSamouraiBusiness(ArcherSamourai ennemy) {
        super(ennemy);
    }

    @Override
    public AbstractAction getDefault() {
        return new WaitPlayer();
    }

    private class AttackPlayer extends AbstractAction {

        private static final String BOW = "BOW";
        Count alarmed = new Count(0, 4 * Time.SECONDE);

        public AttackPlayer() {
            actions.clear();
            ennemy.alarmed();
            Ennemy.callFriend(ennemy);
        }

        @Override
        public void act() {
            if (EntiteManager.player.vanish) current = new LostPlayer();
            if (ennemy.isAlarmed && !ennemy.see(EntiteManager.player)) {
                if (alarmed.stepAndComplete()) {
                    current = new LostPlayer();
                }
            }
            if (!ennemy.isAlarmed) return;
            if (ennemy.see(EntiteManager.player))
                handleBow();
        }

        private void handleBow() {
            ennemy.lookAt(EntiteManager.player);
            if (!actions.containsKey(BOW)) {
                SoundManager.playSound(SoundManager.PREPARE_SPELL);
                SoundManager.playSound(SoundManager.BOW);
                actions.put(BOW, new AttackBowEnnemy(ennemy, MathUtils.getCenter(EntiteManager.player.getBoundingRectangle())) {
                    @Override
                    public void onFinish() {
                        actions.remove(BOW);
                    }
                });
            }
        }
    }

    public class WaitPlayer extends AbstractAction {

        private final Count see = new Count(0, Constantes.SEELANTENCY);
        Count delay = new Count(0, Time.SECONDE * 3);
        int currentVision = 0;

        public WaitPlayer() {
            actions.clear();
        }

        @Override
        public void act() {

            if (delay.stepAndComplete()) {
                currentVision = ennemy.getLookSomewhereElse();
            }
            if (!com.badlogic.gdx.math.MathUtils.isEqual(currentVision, ennemy.getRotation(), 10)) {
                float angle = ennemy.getRotation() > currentVision ? ennemy.getRotation() - 5 : ennemy.getRotation() + 5;
                ennemy.setRotation(angle);
            }
            if (ennemy.isAlarmed) {
                current = new AttackPlayer();

            } else if (ennemy.see(EntiteManager.player)) {
                if (see.getCount() == 0) ennemy.talk(" Hu ? ", Color.ORANGE);
                ennemy.lookAt(EntiteManager.player);
                if (see.stepAndComplete()) {
                    current = new AttackPlayer();
                }

            } else {
                see.reset();
            }
        }

    }

    private class LostPlayer extends AbstractAction {

        Count lookingFor = new Count(0, 4 * Time.SECONDE);
        Count waitRotation = new Count(0, Time.SECONDE);

        private float targetRotation;

        public LostPlayer() {
            ennemy.shader = null;
            ennemy.calmDown();
            actions.clear();
        }

        @Override
        public void act() {

            if (ennemy.see(EntiteManager.player) || ennemy.isAlarmed) {
                current = new AttackPlayer();
                return;
            }
            if (com.badlogic.gdx.math.MathUtils.isEqual(ennemy.getRotation(), targetRotation, 10)) {
                if (waitRotation.stepAndComplete()) {
                    targetRotation = com.badlogic.gdx.math.MathUtils.random(360);
                }
            } else {
                ennemy.setRotation(ennemy.getRotation() + (ennemy.getRotation() < targetRotation ? 5 : (-5)));
            }
            if (lookingFor.stepAndComplete()) {
                current = new WaitPlayer();
                ennemy.talk("Don't care...", Color.WHITE);
            }
        }

    }
}
