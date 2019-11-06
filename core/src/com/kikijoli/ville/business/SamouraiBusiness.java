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
import com.kikijoli.ville.drawable.entite.npc.Samourai;
import com.kikijoli.ville.manager.EntiteManager;
import com.kikijoli.ville.manager.MessageManager;
import com.kikijoli.ville.manager.SoundManager;
import com.kikijoli.ville.pathfind.GridManager;
import com.kikijoli.ville.pathfind.Tile;
import com.kikijoli.ville.shader.WalkShader;
import com.kikijoli.ville.util.MathUtils;
import java.util.ArrayList;

/**
 *
 * @author Arthur
 */
public class SamouraiBusiness extends AbstractBusiness {

    Samourai guard;
    private static final String GOTO = "GOTO";

    public SamouraiBusiness(Samourai guard) {
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
            handleWalk();
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
                if (MathUtils.getDistance(guard.getCenter(), EntiteManager.player.getCenter()) > 200)
                    return;
                if (!guard.vision.contains(EntiteManager.player.getX(), EntiteManager.player.getY()))
                    return;
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
        private GoTo goTo;
        int count = 0;
        int delay = 60 * 2;

        @Override
        public void act() {
            if (!actions.containsKey(GOTO)) {
                ArrayList<Tile> tileFor = GridManager.getTileFor(guard.sonar);
                Tile t = tileFor.get(com.badlogic.gdx.math.MathUtils.random(tileFor.size() - 1));
                goTo = new GoTo(guard, t);
                actions.put(GOTO, goTo);

            } else {
                if (goTo.isFinish()) {
                    if (count++ > delay) {
                        actions.remove(GOTO);
                        count = 0;
                    }
                }
            }
            lookForPlayer();
        }

        private void lookForPlayer() {
            if (EntiteManager.player.hide) return;
            if (guard.vision.contains(EntiteManager.player.getX(), EntiteManager.player.getY())) {
                actions.clear();
                MessageManager.addIndicator(guard.getX() - 8, guard.getY() + guard.getHeight(), "?!", guard, Color.ORANGE, 50);
                guard.vision.setColor(Color.RED);
                current = new AttackPlayer();
            }
        }

    }

}
