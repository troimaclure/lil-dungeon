package com.kikijoli.ville.manager;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.kikijoli.ville.maps.Tmap;
import java.util.ArrayList;

/**
 *
 * @author ajosse
 */
public class DrawManager {

    private DrawManager() {
    }

    public static ArrayList<Sprite> sprites = new ArrayList<>();

    public static void tour() {
        sprites.forEach((sprite) -> {
            Tmap.spriteBatch.draw(sprite.getTexture(), sprite.getX(), sprite.getY(), sprite.getOriginX(), sprite.getOriginY(), sprite.getWidth(), sprite.getHeight(), sprite.getScaleX(), sprite.getScaleY(), sprite.getRotation(), (int) sprite.getX(), (int) sprite.getY(), (int) sprite.getWidth(), (int) sprite.getHeight(), false, false);
        });
    }
}
