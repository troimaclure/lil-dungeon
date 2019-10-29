package com.kikijoli.ville.manager;

import com.kikijoli.ville.drawable.hud.Tile;
import com.kikijoli.ville.maps.Tmap;
import com.kikijoli.ville.util.Constantes;

/**
 *
 * @author ajosse
 */
public class HudManager {

    public static void drawSprite() {
        int index = 0;
        for (Tile tile : ThemeManager.currentTheme.getTiles()) {
            tile.setX(++index * Constantes.TILESIZE);
            tile.setY(Constantes.TILESIZE);
            tile.draw(Tmap.hudBatch);
        }
    }

    public static void drawShape() {

        int index = 0;
        for (Tile tile : ThemeManager.currentTheme.getTiles()) {
            tile.setX(++index * Constantes.TILESIZE);
            tile.setY(Constantes.TILESIZE);
            tile.draw(Tmap.hudShapeRenderer);
        }
    }

    public static void drawLines() {
        int index = 0;
        for (Tile tile : ThemeManager.currentTheme.getTiles()) {
            tile.setX(++index * Constantes.TILESIZE);
            tile.setY(Constantes.TILESIZE);
            tile.drawLines(Tmap.hudShapeRenderer);
        }
    }

    public static void setSelected(int amount) {
        int indexOf = (amount) + ThemeManager.currentTheme.getTiles().indexOf(ThemeManager.currentTheme.getTiles().stream().filter(e -> e.selected).findFirst().orElse(ThemeManager.currentTheme.getTiles().get(0)));
        if (indexOf < 0) indexOf = ThemeManager.currentTheme.getTiles().size() - 1;
        if (indexOf > ThemeManager.currentTheme.getTiles().size() - 1) indexOf = 0;
        ThemeManager.currentTheme.getTiles().get(indexOf).action();
    }
}
