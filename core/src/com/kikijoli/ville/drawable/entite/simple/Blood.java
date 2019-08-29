package com.kikijoli.ville.drawable.entite.simple;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.kikijoli.ville.interfaces.IShapeDrawable;

/**
 *
 * @author ajosse
 */
public class Blood implements IShapeDrawable {

	Rectangle bounds;
	int radius;

	public Blood(Rectangle bounds) {
		this.bounds = bounds;
	}

	@Override
	public void draw(ShapeRenderer batch) {
		batch.setColor(Color.RED);
		batch.ellipse(bounds.x + bounds.width - radius, bounds.y + 5, radius > bounds.width * 2 ? radius : radius++, 10);
	}

}
