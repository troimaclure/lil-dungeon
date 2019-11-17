package com.kikijoli.ville.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.kikijoli.ville.drawable.hud.Tile;
import com.kikijoli.ville.maps.Tmap;
import com.kikijoli.ville.util.Constantes;
import com.kikijoli.ville.util.TextureUtil;

/**
 *
 * @author ajosse
 */
public class HudManager {

    public static Texture life = TextureUtil.getTexture("sprite/life.png");
    public static Texture bloodScreen = TextureUtil.getTexture("sprite/bloodscreen.png");
    public static float bloodIndex = 1.0f;
    public static boolean resetBlood = false;

    public static void drawSprite() {
        drawLife();
        handleTouchedAndBlood();
        drawTiles();
    }

    private static void drawLife() {
        Tmap.hudBatch.setColor(Color.WHITE);
        for (int i = 0; i < EntiteManager.player.pv; i++) {
            Tmap.hudBatch.draw(life, Constantes.TILESIZE + i * Constantes.TILESIZE / 2, Gdx.graphics.getHeight() - Constantes.TILESIZE * 2.5f, Constantes.TILESIZE / 2, Constantes.TILESIZE / 2);
        }
    }

    private static void drawTiles() {
        int index = 0;
        for (Tile tile : ThemeManager.currentTheme.getTiles()) {
            tile.setX(++index * Constantes.TILESIZE);
            tile.setY(Constantes.TILESIZE);
            tile.draw(Tmap.hudBatch);
        }
    }

    public static void handleTouchedAndBlood() {
        if (EntiteManager.player.touched) {
            bloodIndex -= 0.01f;
            Tmap.hudBatch.setColor(1, 1, 1, bloodIndex);
            Tmap.hudBatch.draw(bloodScreen, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            resetBlood = true;
        }
        if (resetBlood && !EntiteManager.player.touched) {
            resetBlood = false;
            bloodIndex = 1.0f;
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
        if (indexOf < 0)
            indexOf = ThemeManager.currentTheme.getTiles().size() - 1;
        if (indexOf > ThemeManager.currentTheme.getTiles().size() - 1)
            indexOf = 0;
        ThemeManager.currentTheme.getTiles().get(indexOf).action();
    }
}
