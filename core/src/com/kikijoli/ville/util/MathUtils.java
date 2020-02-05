package com.kikijoli.ville.util;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.drawable.entite.Entite;

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

    public static Vector2 destination(Vector2 destination, Vector2 v) {
        return new Vector2().set(destination).sub(new Vector2(v.x, v.y)).nor();
    }

    public static Entite centered(Entite source, Entite target) {
        target.setX(source.getX() - target.getWidth() / 2 + source.getWidth() / 2);
        target.setY(source.getY() - target.getHeight() / 2 + source.getHeight() / 2);
        return target;
    }

    public static int transformIpsToSec(int by60) {
        return (int) (Math.ceil(by60 > 0 ? by60 / 60 : 0) + 1);
    }

    public static int nearest(int x, int y, int v) {
        int one = Math.abs(v) - Math.abs(x);
        int two = Math.abs(v) - Math.abs(y);
        return two > one ? y : x;
    }

    public static Vector2 centerString(String s, BitmapFont bitMapFont, Rectangle rect) {
        final GlyphLayout layout = new GlyphLayout(bitMapFont, s);
        Vector2 center = MathUtils.getCenter(rect);
        final float fontX = center.x - layout.width / 2 - rect.width / 2;
        final float fontY = rect.y + (rect.height + layout.height) / 2;
        return new Vector2(fontX, fontY);
    }

    public static GlyphLayout getFontSize(BitmapFont font, String s) {
        return new GlyphLayout(font, s);
    }

    public static double getDistance(Vector2 object1, Vector2 object2) {
        return Math.sqrt(Math.pow((object2.x - object1.x), 2) + Math.pow((object2.y - object1.y), 2));
    }

    public static float getMax(float a, float b) {
        return (a > b) ? a : b;
    }
}
