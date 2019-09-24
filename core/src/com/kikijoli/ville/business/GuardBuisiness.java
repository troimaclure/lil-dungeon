/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.business;

import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.abstracts.AbstractAction;
import com.kikijoli.ville.automation.player.AttackBow;
import com.kikijoli.ville.automation.ennemy.DashEnnemy;
import com.kikijoli.ville.automation.common.GoTo;
import com.kikijoli.ville.automation.ennemy.AttackDirectionPreparation;
import com.kikijoli.ville.automation.player.AttackSword;
import com.kikijoli.ville.drawable.entite.npc.Guard;
import com.kikijoli.ville.drawable.entite.simple.Bow;
import com.kikijoli.ville.drawable.entite.simple.Sword;
import com.kikijoli.ville.manager.EntiteManager;
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
        private static final String BOW = "BOW";
        private static final String ATTACK = "ATTACK";
        private static final String PREPARATION = "PREPARATIOn";

        int count = 50;
        int delay = 50;
        int dashDelay = 50;
        int countDash = 0;
        int bowDelay = 150;
        int countBow = 150;

        @Override
        public void act() {

            handleWalk();
            handleDash();
            handleBow();
        }

        private void handleWalk() {
            if (!actions.containsKey(GOTO) && !actions.containsKey(DASH) && !actions.containsKey(PREPARATION))
                actions.put(GOTO, new GoTo(guard, EntiteManager.player));
            if (!(guard.shader instanceof WalkShader))
                guard.shader = new WalkShader(guard);
        }

        private void handleDash() {

            if (!actions.containsKey(DASH) && !actions.containsKey(PREPARATION) && countDash++ > dashDelay) {
                actions.remove(GOTO);
                countDash = 0;
                actions.put(PREPARATION, new AttackDirectionPreparation(guard, MathUtils.centered(guard, new Sword(0, 0))) {
                    @Override
                    public void onComplete() {
                        actions.remove(PREPARATION);
                        actions.put(DASH, new DashEnnemy(guard, new Vector2(EntiteManager.player.getX(), EntiteManager.player.getY())) {
                            @Override
                            public void onFinish() {
                                handleWalk();
                                actions.remove(DASH);
                            }
                        });
                        actions.put(ATTACK, new AttackSword(guard) {
                            @Override
                            public void onFinish() {
                                actions.remove(ATTACK);
                            }
                        });
                    }
                });
            }
        }

        private void handleBow() {
            if (!actions.containsKey(BOW) && !actions.containsKey(PREPARATION) && countBow++ > bowDelay) {
                countBow = 0;
                actions.remove(GOTO);
                actions.put(PREPARATION, new AttackDirectionPreparation(guard, MathUtils.centered(guard, new Bow(0, 0))) {
                    @Override
                    public void onComplete() {
                        actions.remove(PREPARATION);
                        actions.put(BOW, new AttackBow(guard, destination) {
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
            if (guard.vision.contains(EntiteManager.player.getX(), EntiteManager.player.getY())) {
                current = new AttackPlayer();
            }
        }

    }

}
