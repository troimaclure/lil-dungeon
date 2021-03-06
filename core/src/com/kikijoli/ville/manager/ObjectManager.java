package com.kikijoli.ville.manager;

import com.badlogic.gdx.math.Intersector;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.drawable.entite.object.IObject;
import com.kikijoli.ville.maps.Tmap;
import java.util.ArrayList;

/**
 *
 * @author ajosse
 */
public class ObjectManager {

    public static ArrayList<Entite> objects = new ArrayList<>();

    public static void draw() {
        objects.forEach((object) -> {
            object.draw(Tmap.spriteBatch);
        });
    }

    public static void tour() {
        ArrayList<Entite> remove = new ArrayList<>();
        objects.stream().filter((object) -> (Intersector.overlaps(EntiteManager.player.getBoundingRectangle(), object.getBoundingRectangle()))).filter((object) -> (((IObject) object).get())).forEachOrdered((object) -> {
            remove.add(object);
        });

        objects.removeAll(remove);
    }

    public static void addObject(Entite entite) {
        objects.add(entite);
    }
}
