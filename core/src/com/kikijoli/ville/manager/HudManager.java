package com.kikijoli.ville.manager;

import com.badlogic.gdx.graphics.Color;
import com.kikijoli.ville.drawable.hud.BowTile;
import com.kikijoli.ville.drawable.hud.SwordTile;
import com.kikijoli.ville.drawable.hud.Tile;
import com.kikijoli.ville.drawable.hud.WandFireTile;
import com.kikijoli.ville.drawable.hud.WandTile;
import com.kikijoli.ville.maps.Tmap;
import com.kikijoli.ville.util.Constantes;
import java.util.ArrayList;

/**
 *
 * @author ajosse
 */
public class HudManager {

    public final static ArrayList<Tile> tiles = new ArrayList<>();

    static {
        tiles.add(new BowTile(100, 100));
        tiles.add(new SwordTile(100 + Constantes.TILESIZE, 100));
        tiles.add(new WandTile(100 + Constantes.TILESIZE * 2, 100));
        tiles.add(new WandFireTile(100 + Constantes.TILESIZE * 3, 100));
        tiles.get(0).action();
    }

    public static void drawSprite() {
        for (Tile tile : tiles) {
            tile.draw(Tmap.hudBatch);
        }
    }

    public static void drawShape() {

        for (Tile tile : tiles) {
            tile.draw(Tmap.hudShapeRenderer);
        }
    }

    public static void drawLines() {

        for (Tile tile : tiles) {
            tile.drawLines(Tmap.hudShapeRenderer);
        }
    }

    public static void setSelected(int amount) {
        int indexOf = (amount) + tiles.indexOf(tiles.stream().filter(e -> e.selected).findFirst().orElse(tiles.get(0)));
        if (indexOf < 0) indexOf = tiles.size() - 1;
        if (indexOf > tiles.size() - 1) indexOf = 0;
        tiles.get(indexOf).action();
    }
}
