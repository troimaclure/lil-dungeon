package com.kikijoli.ville.manager;

import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.interfaces.IShapeDrawable;
import com.kikijoli.ville.maps.Tmap;
import java.util.ArrayList;

/**
 *
 * @author ajosse
 */
public class DrawManager {

    private DrawManager() {
    }

    public static ArrayList<Entite> entites = new ArrayList<>();
    public static ArrayList<IShapeDrawable> spritesFilled = new ArrayList<>();
    public static ArrayList<IShapeDrawable> spritesDrawed = new ArrayList<>();

    public static void tour() {

        entites.forEach((sprite) -> {
            Tmap.spriteBatch.setShader(sprite.shader);
            sprite.draw(Tmap.spriteBatch);

        });

    }

    public static void drawShapeFilled() {
        spritesFilled.forEach((sprite) -> {
            sprite.draw(Tmap.shapeRenderer);
        });
    }

    public static void drawShapeDrawed() {
        spritesDrawed.forEach((sprite) -> {
            sprite.draw(Tmap.shapeRenderer);
        });
    }
}
