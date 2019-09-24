package com.kikijoli.ville.drawable.entite.simple;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.kikijoli.ville.interfaces.IShapeDrawable;

/**
 *
 * @author ajosse
 */
public class CircleEffect implements IShapeDrawable {

    Circle circle;
    Color color;

    public CircleEffect(Circle c, Color color) {
        this.circle = c;
        this.color = color;
    }

    @Override
    public void draw(ShapeRenderer batch) {
        batch.setColor(color);
        batch.circle(circle.x, circle.y, circle.radius);
    }

}
