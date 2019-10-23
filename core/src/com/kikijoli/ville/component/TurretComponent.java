/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.component;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.drawable.entite.simple.TurretBow;
import com.kikijoli.ville.util.MathUtils;
import java.util.function.Function;

/**
 *
 * @author ajosseau
 */
public class TurretComponent implements IComponent {

    public TurretBow turret = new TurretBow(0, 0);
    public Entite entite;
    private final Function<Void, Vector2> function;

    public TurretComponent(Entite entite, Function<Void, Vector2> function) {
        this.entite = entite;
        this.function = function;
    }

    @Override
    public void draw(SpriteBatch batch) {
        turret.setX((float) (this.entite.getX() - (turret.getWidth() / 2 - this.entite.getWidth() / 2)));
        turret.setY(this.entite.getY() - this.entite.getHeight() / 2);
        Vector2 apply = function.apply(null);
        turret.setRotation(90 + MathUtils.getRotation(this.entite.getX(), this.entite.getY(), apply.x, apply.y));
        turret.draw(batch);
    }
}
