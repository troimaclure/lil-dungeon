package com.kikijoli.ville.theme;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.kikijoli.ville.drawable.hud.Tile;
import java.util.List;

/**
 *
 * @author ajosse
 */
public abstract class AbstractTheme {

    protected final List<Tile> tiles;

    public AbstractTheme(List<Tile> tiles) {
        this.tiles = tiles;
    }

    public List<Tile> getTiles() {
        return this.tiles;
    }

    public abstract void handleFromTmx(TiledMapTileMapObject entite);

    public abstract Color getFontColor();
}
