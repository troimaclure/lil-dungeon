package com.kikijoli.ville.theme;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.kikijoli.ville.drawable.hud.Tile;
import java.util.List;

/**
 *
 * @author ajosse
 */
public abstract class AbstractTheme {

    protected final List<Tile> tiles;
    protected final Texture wallTexture;
    protected final Color backgroundColor;

    public AbstractTheme(List<Tile> tiles, Texture wallTexture, Color backgroundColor) {

        this.tiles = tiles;
        this.wallTexture = wallTexture;
        this.backgroundColor = backgroundColor;
    }

    public Texture getWallTexture() {
        return wallTexture;
    }

    public List<Tile> getTiles() {
        return this.tiles;
    }

    public Color getBackGroundColor() {
        return this.backgroundColor;
    }

}
