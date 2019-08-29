package com.kikijoli.ville.util;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 *
 * @author ajosse
 */
public class MathUtils {

	public static float getRotation(float x, float y, float x2, float y2) {
		return (float) (Math.atan2(
				y - y2,
				x - x2
		) * 180.0d / Math.PI);
	}

	public static float getDifference(float a, float b) {
		return Math.abs(Math.abs(a) - Math.abs(b));
	}

	public static Vector2 getCenter(Rectangle r) {
		Vector2 tmp = new Vector2();
		tmp = r.getCenter(tmp);
		return tmp;
	}
}
