/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.business;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.abstracts.AbstractAction;
import com.kikijoli.ville.automation.ennemy.DashEnnemy;
import com.kikijoli.ville.automation.common.GoTo;
import com.kikijoli.ville.automation.player.AttackSword;
import com.kikijoli.ville.drawable.entite.npc.Guard;
import com.kikijoli.ville.manager.EntiteManager;
import com.kikijoli.ville.manager.MessageManager;
import com.kikijoli.ville.manager.SoundManager;
import com.kikijoli.ville.shader.WalkShader;
import com.kikijoli.ville.util.MathUtils;

/**
 *
 * @author Arthur
 */
public class GuardBuisiness extends AbstractBusiness {

    Guard guard;

    public GuardBuisiness(Guard guard) {
        this.guard = guard;
    }

    @Override
    public AbstractAction getDefault() {
        return new WaitPlayer();
    }

    private class AttackPlayer extends AbstractAction {

        private static final String GOTO = "GOTO";
        private static final String DASH = "DASH";
        private static final String ATTACK = "ATTACK";

        int count = 30;
        int delay = 60;
        int dashDelay = 60;
        int countDash = 30;

        @Override
        public void act() {
            Vector2 center = MathUtils.getCenter(guard.getBoundingRectangle());
            guard.setRotation(90 + MathUtils.getRotation(EntiteManager.player.getX(), EntiteManager.player.getY(), center.x, center.y));
            handleDash();
        }

        private void handleWalk() {
            if (!actions.containsKey(GOTO) && !actions.containsKey(DASH)) {
                actions.put(GOTO, new GoTo(guard, EntiteManager.player));
            }
            if (!(guard.shader instanceof WalkShader)) {
                guard.shader = new WalkShader(guard);
            }
        }

        private void handleDash() {

            if (!actions.containsKey(DASH) && countDash++ > dashDelay) {
                actions.remove(GOTO);
                countDash = 0;
                SoundManager.playSound(SoundManager.PREPARE_SPELL);

                SoundManager.playSound(SoundManager.DASH);
                actions.put(DASH, new DashEnnemy(guard, new Vector2(EntiteManager.player.getX(), EntiteManager.player.getY())) {
                    @Override
                    public void onFinish() {
                        handleWalk();
                        actions.remove(DASH);
                    }
                });
                SoundManager.playSound(SoundManager.SWORD);
                actions.put(ATTACK, new AttackSword(guard) {
                    @Override
                    public void onFinish() {
                        actions.remove(ATTACK);
                    }
                });

            }
        }
    }

    public class WaitPlayer extends AbstractAction {

        float degree = 0;

        @Override
        public void act() {
            guard.setRotation(degree++);
            if (EntiteManager.player.hide) return;
            if (guard.vision.contains(EntiteManager.player.getX(), EntiteManager.player.getY())) {
                MessageManager.addIndicator(guard.getX() - 8, guard.getY() + guard.getHeight(), "?!", guard, Color.ORANGE, 50);
                current = new AttackPlayer();
            }
        }

    }

}
