/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.automation.ennemy;

import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.abstracts.AbstractAction;
import com.kikijoli.ville.component.TurretComponent;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.drawable.entite.projectile.Bullet.CannonBall;
import com.kikijoli.ville.drawable.entite.simple.TurretBow;
import com.kikijoli.ville.manager.ProjectileManager;
import com.kikijoli.ville.manager.SoundManager;
import com.kikijoli.ville.util.MathUtils;

/**
 *
 * @author Arthur
 */
public abstract class AttackTurret extends AbstractAction {

    TurretBow turret;
    Entite entite;
    Vector2 destination;

    public AttackTurret(Entite entite, Vector2 destination) {
        this.entite = entite;
        this.destination = destination;
        turret = ((TurretComponent) this.entite.getComponent(TurretComponent.class)).turret;
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
        Vector2 center = MathUtils.getCenter(turret.getBoundingRectangle());
        SoundManager.playSound(SoundManager.CANNON_BALL);
        CannonBall cannonBall = new CannonBall((int) center.x, (int) center.y,
                new Vector2(destination.x, destination.y), entite);
        cannonBall.sprite
                .setRotation(90 + MathUtils.getRotation(entite.getX(), entite.getY(), destination.x, destination.y));
        ProjectileManager.projectiles.add(cannonBall);
    }

}
