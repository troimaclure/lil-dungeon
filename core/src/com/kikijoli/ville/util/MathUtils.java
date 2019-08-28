package com.kikijoli.ville.util;

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
}
