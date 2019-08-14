package com.kikijoli.ville.manager;

import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.maps.Tmap;
import java.util.ArrayList;

/**
 *
 * @author ajosse
 */
public class DrawManager {

    private DrawManager() {
    }

    public static ArrayList<Entite> sprites = new ArrayList<>();

    public static void tour() {
        sprites.forEach((sprite) -> {
            sprite.draw(Tmap.spriteBatch);
        });
    }
}
