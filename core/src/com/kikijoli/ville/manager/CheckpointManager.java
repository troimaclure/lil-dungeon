package com.kikijoli.ville.manager;

import com.kikijoli.ville.drawable.entite.build.Firecamp;
import com.kikijoli.ville.maps.Tmap;
import java.util.ArrayList;

/**
 *
 * @author ajosse
 */
public class CheckpointManager {

    public static Firecamp currentCheckpoint = null;
    public static ArrayList<Firecamp> firecamps = new ArrayList<>();

    public static void tour() {
        for (Firecamp firecamp : firecamps) {
            if (firecamp.touch) continue;
            if (EntiteManager.player.getBoundingRectangle().overlaps(firecamp.getBoundingRectangle())) {
                firecamp.touch();
            }
        }
    }

    public static void draw() {
        firecamps.forEach((firecamp) -> {
            firecamp.draw(Tmap.spriteBatch);
        });
    }
}
