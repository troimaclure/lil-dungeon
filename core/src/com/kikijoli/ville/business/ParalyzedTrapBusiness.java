/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.business;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.abstracts.AbstractAction;
import com.kikijoli.ville.drawable.entite.npc.ParalyzedTrap;
import com.kikijoli.ville.manager.EntiteManager;
import com.kikijoli.ville.manager.ParticleManager;
import com.kikijoli.ville.manager.SoundManager;
import com.kikijoli.ville.manager.StateManager;
import com.kikijoli.ville.state.ParalyzedState;
import com.kikijoli.ville.util.Count;
import com.kikijoli.ville.util.Time;

/**
 *
 * @author Arthur
 */
public class ParalyzedTrapBusiness extends AbstractBusiness {

    ParalyzedTrap trap;
    int delay = 0;
    boolean explode;

    public ParalyzedTrapBusiness(ParalyzedTrap turret) {
        this.trap = turret;
    }

    @Override
    public AbstractAction getDefault() {
        return new AttackPlayer();
    }

    private class AttackPlayer extends AbstractAction {

        @Override
        public void act() {
            if (isContacted()) {
                handleBoom();
            }
        }

        public void handleBoom() {
            if (explode)
                return;
            explode = true;
            trap.hided = false;
            trap.talk("Traped", Color.RED);
            ParticleManager.addParticle("particle/blood.p", trap.getX() + trap.getWidth() / 2,
                    trap.getY() + trap.getHeight() / 2, 1.0f);
            SoundManager.playSound(SoundManager.TRAP_EXPLODE);
            StateManager.states.add(new ParalyzedState(new Count(2 * Time.SECONDE), EntiteManager.player));
            Vector2 center = trap.getCenter();
            EntiteManager.player.setX(center.x - trap.getWidth() / 2);
            EntiteManager.player.setY(trap.getY());
        }

        private boolean isContacted() {
            return trap.getBoundingRectangle().contains(EntiteManager.player.getCenter());
        }
    }
}
