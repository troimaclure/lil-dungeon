/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.automation.player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.abstracts.AbstractAction;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.drawable.entite.simple.Pebble;
import com.kikijoli.ville.manager.DrawManager;
import com.kikijoli.ville.manager.EntiteManager;
import com.kikijoli.ville.manager.ParticleManager;
import com.kikijoli.ville.util.Constantes;
import com.kikijoli.ville.util.MathUtils;

/**
 *
 * @author Arthur
 */
public abstract class ThrowPebble extends AbstractAction {

    Pebble pebble;
    Entite entite;
    Vector2 destination;
    boolean shooted = false;
    Vector2 vel;

    public ThrowPebble(Entite entite, Vector2 destination) {
        this.entite = entite;
        this.destination = destination;
        Vector2 center = entite.getCenter();
        this.pebble = new Pebble(center.x, center.y, Constantes.TILESIZE, Constantes.TILESIZE);
        DrawManager.entites.add(pebble);
    }

    @Override
    public void act() {
        shoot();
        run();
        end();
    }

    private void run() {
        if (vel == null) {
            vel = MathUtils.destination(this.destination, new Vector2(pebble.getX(), pebble.getY()));
        }
        pebble.setX(pebble.getX() + vel.x * pebble.speed);
        pebble.setY(pebble.getY() + vel.y * pebble.speed);
    }

    private void shoot() {
        if (shooted) return;
        shooted = true;
        Vector2 center = MathUtils.getCenter(pebble.getBoundingRectangle());
        pebble.setRotation(90 + MathUtils.getRotation(center.x, center.y, destination.x, destination.y));
    }

    public abstract void onFinish();

    private void end() {
        if (pebble.getBoundingRectangle().contains(destination)) {
            DrawManager.entites.remove(pebble);
            ParticleManager.addParticle("particle/pebble.p", pebble.getX(), pebble.getY(), 1.0f);
            onFinish();
            pebble.talk("Poc", Color.GRAY);
            EntiteManager.pebble(pebble);
        }
    }

}
