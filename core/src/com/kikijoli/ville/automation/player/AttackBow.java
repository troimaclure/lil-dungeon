/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.automation.player;

import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.abstracts.AbstractAction;
import com.kikijoli.ville.component.BowComponent;
import com.kikijoli.ville.drawable.entite.projectile.Bullet.Arrow;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.drawable.entite.simple.Bow;
import com.kikijoli.ville.manager.ProjectileManager;
import com.kikijoli.ville.util.MathUtils;

/**
 *
 * @author Arthur
 */
public abstract class AttackBow extends AbstractAction {

    Bow bow;
    Entite entite;
    Vector2 destination;
    boolean shooted = false;

    public AttackBow(Entite entite, Vector2 destination) {
        this.entite = entite;
        this.destination = destination;
        this.bow = ((BowComponent) this.entite.getComponent(BowComponent.class)).bow;
    }

    @Override
    public void act() {
        shoot();
        end();
    }

    public abstract void onFinish();

    private void end() {
        onFinish();
    }

    private void shoot() {
        if (shooted) return;
        shooted = true;
        Vector2 center = MathUtils.getCenter(bow.getBoundingRectangle());
        Arrow arrow = new Arrow((int) center.x, (int) center.y, new Vector2(destination.x, destination.y), entite);
        arrow.sprite.setRotation(90 + MathUtils.getRotation(entite.getX(), entite.getY(), destination.x, destination.y));
        ProjectileManager.projectiles.add(arrow);
    }

}
