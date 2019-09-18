/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.automation.player;

import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.abstracts.AbstractAction;
import com.kikijoli.ville.drawable.entite.projectile.Bullet.Arrow;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.drawable.entite.simple.Bow;
import com.kikijoli.ville.drawable.entite.simple.TurretBow;
import com.kikijoli.ville.manager.ProjectileManager;
import com.kikijoli.ville.manager.DrawManager;
import com.kikijoli.ville.manager.EntiteManager;
import com.kikijoli.ville.util.MathUtils;

/**
 *
 * @author Arthur
 */
public abstract class AttackTurret extends AbstractAction {

    public int count = 0;
    public int countArrow = 50;
    public int delayArrow = 50;
    public int delay = 30;
    TurretBow bow;
    Entite entite;
    Vector2 destination;

    public AttackTurret(Entite entite, Vector2 destination) {
        this.entite = entite;
        this.destination = destination;
    }

    @Override
    public void act() {

        addBowIfNotExist();
        bow.setX((float) (entite.getX() - (bow.getWidth() / 2 - entite.getWidth() / 2)));
        bow.setY(entite.getY());
        bow.setRotation(90 + MathUtils.getRotation(entite.getX(), entite.getY(), destination.x, destination.y));
        if (countArrow++ >= delayArrow) shoot();
        if (count++ > delay) end();
    }

    private void addBowIfNotExist() {
        if (bow != null) return;
        bow = new TurretBow((int) (entite.getX()), (int) (entite.getY()));
        DrawManager.entites.add(bow);
        EntiteManager.attack(entite);
    }

    public abstract void onFinish();

    private void end() {
        if (bow != null)
            DrawManager.entites.remove(bow);
        onFinish();
    }

    private void shoot() {
        countArrow = 0;
        Vector2 center = MathUtils.getCenter(bow.getBoundingRectangle());
        Arrow arrow = new Arrow((int) center.x, (int) center.y, new Vector2(destination.x, destination.y), entite);
        arrow.sprite.setRotation(90 + MathUtils.getRotation(entite.getX(), entite.getY(), destination.x, destination.y));
        ProjectileManager.projectiles.add(arrow);
    }

}
