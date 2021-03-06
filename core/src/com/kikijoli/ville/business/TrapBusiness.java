/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.business;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Intersector;
import com.kikijoli.ville.abstracts.AbstractAction;
import com.kikijoli.ville.drawable.entite.npc.ExplodeTrap;
import com.kikijoli.ville.drawable.entite.simple.CircleEffect;
import com.kikijoli.ville.manager.EntiteManager;
import com.kikijoli.ville.manager.MessageManager;
import com.kikijoli.ville.manager.ParticleManager;
import com.kikijoli.ville.manager.SoundManager;
import com.kikijoli.ville.util.MathUtils;

/**
 *
 * @author Arthur
 */
public class TrapBusiness extends AbstractBusiness {

    CircleEffect circleEffect;
    ExplodeTrap trap;
    boolean exec;
    boolean explode;
    int delay = 60;

    public TrapBusiness(ExplodeTrap turret) {
        this.trap = turret;
        this.circleEffect = new CircleEffect(trap.getBoundsEffect(), Color.RED);
    }

    @Override
    public AbstractAction getDefault() {
        return new AttackPlayer();
    }

    private class AttackPlayer extends AbstractAction {

        int bowDelay = 150;
        int countBow = 150;

        @Override
        public void act() {
            if (isContacted()) {
                waitForPlayer();
            }
            handleBoom();
        }

        public void handleBoom() {
            if (exec) {
                delay--;
            }
            if (delay < 0 && !explode) {
//                DrawManager.spritesDrawed.remove(circleEffect);
                MessageManager.addMessage(trap.getX() - trap.getWidth() / 2, trap.getY() + trap.getHeight() / 2, "BOOM", Color.WHITE, 30);
                ParticleManager.addParticle("particle/explosion.p", trap.getX() + trap.getWidth() / 2, trap.getY() + trap.getHeight() / 2, 1.0f);
                explode = true;
                SoundManager.playSound(SoundManager.TRAP_EXPLODE);
                if (trap.getBoundsEffect().contains(MathUtils.getCenter(EntiteManager.player.getBoundingRectangle()))) {
                    EntiteManager.touch(EntiteManager.player);
                }
            }
        }

        public void waitForPlayer() {
            if (!exec) {
//                DrawManager.spritesDrawed.add(circleEffect);
                MessageManager.addIndicator(trap.getX() - trap.getWidth() / 2, trap.getY() + trap.getHeight() / 2, "CLIC", Color.WHITE, 30);
                exec = true;
                SoundManager.playSound(SoundManager.TRAP_CLIC);
            }
        }

        private boolean isContacted() {
            return Intersector.overlaps(trap.anchor, EntiteManager.player.getBoundingRectangle());
        }
    }
}
