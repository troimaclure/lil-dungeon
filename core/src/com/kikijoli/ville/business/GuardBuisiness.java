/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.business;

import com.badlogic.gdx.math.Intersector;
import com.kikijoli.ville.abstracts.AbstractAction;
import com.kikijoli.ville.automation.GoTo;
import com.kikijoli.ville.drawable.entite.npc.Guard;
import com.kikijoli.ville.manager.EntiteManager;
import com.kikijoli.ville.shader.ClickShader;
import com.kikijoli.ville.shader.WalkShader;

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

        int count = 50;
        int delay = 50;

        @Override
        public void act() {
            if (Intersector.overlaps(guard.anchor, EntiteManager.player.getBoundingRectangle())) {
                actions.clear();
                if (!(guard.shader instanceof ClickShader)) {
                    guard.shader = null;
                }
                if (count++ >= delay) {
                    guard.shader = new ClickShader(guard, null);
                    count = 0;
                }
            } else {
                if (!actions.containsKey("GoTo"))
                    actions.put("GoTo", new GoTo(guard, EntiteManager.player));
                if (!(guard.shader instanceof WalkShader))
                    guard.shader = new WalkShader(guard);
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
