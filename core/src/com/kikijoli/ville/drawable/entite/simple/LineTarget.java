package com.kikijoli.ville.drawable.entite.simple;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.interfaces.IShapeDrawable;
import com.kikijoli.ville.maps.Tmap;

/**
 *
 * @author ajosse
 */
public class LineTarget implements IShapeDrawable {

    Vector2 start;
    Vector2 end;

    public LineTarget(Vector2 start, Vector2 end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public void draw(ShapeRenderer batch) {
        batch.setColor(Color.RED);
        batch.rectLine(new Vector2(start.x, start.y), new Vector2(end.x, end.y), 5);
    }

}
