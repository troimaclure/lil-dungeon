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
import com.kikijoli.ville.manager.EntiteManager;
import com.kikijoli.ville.manager.SoundManager;
import com.kikijoli.ville.pathfind.GridManager;
import com.kikijoli.ville.pathfind.Tile;
import com.kikijoli.ville.util.Constantes;
import com.kikijoli.ville.util.Count;
import com.kikijoli.ville.util.MathUtils;
import com.kikijoli.ville.util.Time;
import java.util.ArrayList;

/**
 *
 * @author Arthur
 */
public class SamouraiBusiness extends EnnemyBusiness {

    private static final String GOTO = "GOTO";

    public SamouraiBusiness(Ennemy ennemy) {
        super(ennemy);
    }

    @Override
    public AbstractAction getDefault() {
        return new WaitPlayer();
    }

    private class AttackPlayer extends AbstractAction {

        private static final String GOTO = "GOTO";
        private static final String DASH = "DASH";
        private static final String ATTACK = "ATTACK";

        Count dash = new Count(30, Time.SECONDE);
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
            handleWalk();
            handleDash();
            ennemy.lookAt(EntiteManager.player);
        }

        private void handleWalk() {
            if (!actions.containsKey(GOTO) && !actions.containsKey(DASH)) {
                actions.put(GOTO, new GoTo(ennemy, EntiteManager.player));
            }
        }

        private void handleDash() {

            if (!(!actions.containsKey(DASH) && dash.step())) return;
            if (MathUtils.getDistance(ennemy.getCenter(), EntiteManager.player.getCenter()) > 200)
                return;
            if (!ennemy.see(EntiteManager.player))
                return;
            dash.reset();
            actions.remove(GOTO);
            SoundManager.playSound(SoundManager.PREPARE_SPELL);

            SoundManager.playSound(SoundManager.DASH);
            actions.put(DASH, new DashEnnemy(ennemy, new Vector2(EntiteManager.player.getX(), EntiteManager.player.getY())) {
                @Override
                public void onFinish() {
                    handleWalk();
                    actions.remove(DASH);
                }
            });
            SoundManager.playSound(SoundManager.SWORD);
            actions.put(ATTACK, new AttackSword(ennemy) {
                @Override
                public void onFinish() {
                    actions.remove(ATTACK);
                }
            });

        }
    }

    public class WaitPlayer extends AbstractAction {

        private final Count see = new Count(0, Constantes.SEELANTENCY);
        float degree = 0;
        private GoTo goTo;
        Count go = new Count(0, Time.SECONDE * 2);

        public WaitPlayer() {
            actions.clear();
        }

        @Override
        public void act() {

            if (!actions.containsKey(GOTO)) {
                ArrayList<Tile> tileFor = GridManager.getTileFor(ennemy.sonar);
                if (tileFor.isEmpty()) return;
                Tile t = tileFor.get(com.badlogic.gdx.math.MathUtils.random(tileFor.size() - 1));
                goTo = new GoTo(ennemy, t, () -> {
                    if (go.stepAndComplete()) {
                        actions.remove(GOTO);
                    }
                }, true);
                actions.put(GOTO, goTo);
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
            } else
                ennemy.setRotation(ennemy.getRotation() + (ennemy.getRotation() < targetRotation ? 5 : (-5)));

            if (lookingFor.stepAndComplete()) {
                ennemy.talk("Must be the wind...", Color.WHITE);
                actions.put(GOTO, new GoTo(ennemy, GridManager.getCaseFor(ennemy.initial), () -> {
                    current = new WaitPlayer();
                }));
            }

        }

    }

}
