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
import com.kikijoli.ville.drawable.entite.npc.Ennemy;
import com.kikijoli.ville.drawable.entite.npc.Samourai;
import com.kikijoli.ville.manager.EntiteManager;
import com.kikijoli.ville.manager.SoundManager;
import com.kikijoli.ville.pathfind.GridManager;
import com.kikijoli.ville.pathfind.Tile;
import com.kikijoli.ville.util.Count;
import com.kikijoli.ville.util.MathUtils;
import com.kikijoli.ville.util.Time;
import java.util.ArrayList;

/**
 *
 * @author Arthur
 */
public class SamouraiBusiness extends AbstractBusiness {

    private static final String GOTO = "GOTO";
    Samourai samourai;

    public SamouraiBusiness(Samourai guard) {
        this.samourai = guard;
    }

    @Override
    public AbstractAction getDefault() {
        return new WaitPlayer();
    }

    private class AttackPlayer extends AbstractAction {

        private static final String GOTO = "GOTO";
        private static final String DASH = "DASH";
        private static final String ATTACK = "ATTACK";

        Count dash = new Count(60, Time.SECONDE);
        Count alarmed = new Count(0, 4 * Time.SECONDE);

        public AttackPlayer() {
            actions.clear();
            samourai.alarmed();
            Ennemy.callFriend(samourai);
        }

        @Override
        public void act() {
            if (samourai.isAlarmed && !samourai.see(EntiteManager.player)) {
                if (alarmed.stepAndComplete()) {
                    current = new LostPlayer();
                }
            }
            if (!samourai.isAlarmed) return;
            handleWalk();
            handleDash();
            samourai.lookAt(EntiteManager.player);
        }

        private void handleWalk() {
            if (!actions.containsKey(GOTO) && !actions.containsKey(DASH)) {
                actions.put(GOTO, new GoTo(samourai, EntiteManager.player));
            }
        }

        private void handleDash() {

            if (!actions.containsKey(DASH) && dash.step()) {
                if (MathUtils.getDistance(samourai.getCenter(), EntiteManager.player.getCenter()) > 200)
                    return;
                if (!samourai.vision.contains(EntiteManager.player.getX(), EntiteManager.player.getY()))
                    return;
                dash.complete();
                actions.remove(GOTO);
                SoundManager.playSound(SoundManager.PREPARE_SPELL);

                SoundManager.playSound(SoundManager.DASH);
                actions.put(DASH, new DashEnnemy(samourai, new Vector2(EntiteManager.player.getX(), EntiteManager.player.getY())) {
                    @Override
                    public void onFinish() {
                        handleWalk();
                        actions.remove(DASH);
                    }
                });
                SoundManager.playSound(SoundManager.SWORD);
                actions.put(ATTACK, new AttackSword(samourai) {
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
        Count go = new Count(0, Time.SECONDE * 2);

        public WaitPlayer() {
            actions.clear();
        }

        @Override
        public void act() {

            if (!actions.containsKey(GOTO)) {
                ArrayList<Tile> tileFor = GridManager.getTileFor(samourai.sonar);
                Tile t = tileFor.get(com.badlogic.gdx.math.MathUtils.random(tileFor.size() - 1));
                goTo = new GoTo(samourai, t, () -> {
                    if (go.stepAndComplete()) {
                        actions.remove(GOTO);
                    }
                }, true);
                actions.put(GOTO, goTo);
            }

            if (samourai.see(EntiteManager.player) || samourai.isAlarmed) {
                current = new AttackPlayer();
            }
        }

    }

    private class LostPlayer extends AbstractAction {

        Count lookingFor = new Count(0, 4 * Time.SECONDE);
        Count waitRotation = new Count(0, Time.SECONDE);

        private float targetRotation;

        public LostPlayer() {
            samourai.shader = null;
            samourai.calmDown();
            actions.clear();
        }

        @Override
        public void act() {
            if (samourai.see(EntiteManager.player) || samourai.isAlarmed) {
                current = new AttackPlayer();
                return;
            }
            if (com.badlogic.gdx.math.MathUtils.isEqual(samourai.getRotation(), targetRotation, 10)) {
                if (waitRotation.stepAndComplete()) {
                    targetRotation = com.badlogic.gdx.math.MathUtils.random(360);
                }
            } else
                samourai.setRotation(samourai.getRotation() + (samourai.getRotation() < targetRotation ? 5 : (-5)));

            if (lookingFor.stepAndComplete()) {
                samourai.talk("Must be the wind...", Color.WHITE);
                actions.put(GOTO, new GoTo(samourai, GridManager.getCaseFor(samourai.initial), () -> {
                    current = new WaitPlayer();

                }));
            }

        }

    }

}
