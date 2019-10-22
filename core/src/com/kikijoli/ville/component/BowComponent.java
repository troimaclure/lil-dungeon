/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.component;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.drawable.entite.simple.Bow;
import com.kikijoli.ville.util.MathUtils;
import java.util.function.Function;

/**
 *
 * @author ajosseau
 */
public class BowComponent implements IComponent {

    public Bow bow = new Bow(0, 0);
    public Entite entite;
    private final Function<Void, Vector2> function;

    public BowComponent(Entite entite, Function<Void, Vector2> function) {
        this.entite = entite;
        this.function = function;
    }

    @Override
    public void draw(SpriteBatch batch) {
        bow.setX((float) (this.entite.getX() - (bow.getWidth() / 2 - this.entite.getWidth() / 2)));
        bow.setY(this.entite.getY() - this.entite.getHeight() / 2);
        Vector2 apply = function.apply(null);
        bow.setRotation(90 + MathUtils.getRotation(this.entite.getX(), this.entite.getY(), apply.x, apply.y));
        bow.draw(batch);
    }
}
