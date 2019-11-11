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
import com.kikijoli.ville.util.Count;
import com.kikijoli.ville.util.MathUtils;
import com.kikijoli.ville.util.Time;

/**
 *
 * @author Arthur
 */
public class ArcherSamouraiBusiness extends AbstractBusiness {

    ArcherSamourai archer;

    public ArcherSamouraiBusiness(ArcherSamourai guard) {
        this.archer = guard;
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
            archer.alarmed();
            Ennemy.callFriend(archer);
        }

        @Override
        public void act() {
            if (archer.isAlarmed && !archer.see(EntiteManager.player)) {
                if (alarmed.stepAndComplete()) {
                    current = new LostPlayer();
                }
            }
            if (!archer.isAlarmed) return;
            if (archer.see(EntiteManager.player))
                archer.lookAt(EntiteManager.player);
            handleBow();
        }

        private void handleBow() {
            if (!archer.see(EntiteManager.player)) return;
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

        Count delay = new Count(0, Time.SECONDE * 3);
        int currentVision = 0;

        public WaitPlayer() {

            actions.clear();
        }

        @Override
        public void act() {

            if (delay.stepAndComplete()) {
                currentVision = archer.getLookSomewhereElse();
            }
            if (!com.badlogic.gdx.math.MathUtils.isEqual(currentVision, archer.getRotation(), 10)) {
                float angle = archer.getRotation() > currentVision ? archer.getRotation() - 5 : archer.getRotation() + 5;
                archer.setRotation(angle);
            }
            if (archer.see(EntiteManager.player) || archer.isAlarmed) {
                archer.alarmed();
                current = new AttackPlayer();
            }
        }

    }

    private class LostPlayer extends AbstractAction {

        Count lookingFor = new Count(0, 4 * Time.SECONDE);
        Count waitRotation = new Count(0, Time.SECONDE);

        private float targetRotation;

        public LostPlayer() {
            archer.shader = null;
            archer.calmDown();
            actions.clear();
        }

        @Override
        public void act() {

            if (archer.see(EntiteManager.player) || archer.isAlarmed) {
                current = new AttackPlayer();
                System.out.println("set to attack");
                return;
            }
            if (com.badlogic.gdx.math.MathUtils.isEqual(archer.getRotation(), targetRotation, 10)) {
                if (waitRotation.stepAndComplete()) {
                    targetRotation = com.badlogic.gdx.math.MathUtils.random(360);
                }
            } else {
                archer.setRotation(archer.getRotation() + (archer.getRotation() < targetRotation ? 5 : (-5)));
            }
            if (lookingFor.stepAndComplete()) {
                current = new WaitPlayer();
                archer.talk("Don't care...", Color.WHITE);
            }
        }

    }
}
