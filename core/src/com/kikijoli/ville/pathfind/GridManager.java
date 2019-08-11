package com.kikijoli.ville.pathfind;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.kikijoli.ville.manager.ColorManager;
import com.kikijoli.ville.maps.Tmap;
import com.kikijoli.ville.util.Constantes;
import com.kikijoli.ville.util.TextureUtil;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author troïmaclure
 */
public class GridManager {

    public static int COLUMNCOUNT;
    public static int ROWCOUNT;
    public static Tile[][] grid;
    private static final String FLOOR = "sprite/floor.png";
    private static final String WALL = "sprite/wall.png";

    public static void initialize(int rowCount, int columnCount, int size) {
        COLUMNCOUNT = columnCount;
        ROWCOUNT = rowCount;
        grid = new Tile[rowCount][columnCount];
        for (int i = 0; i < columnCount; i++) {
            for (int j = 0; j < rowCount; j++) {
                grid[j][i] = new Tile(i, j, i * size, j * size, size, size);
            }
        }
    }

    public static Tile getCaseFor(Rectangle entite) {
        if (entite != null) {
            for (Tile[] grille1 : grid) {
                for (Tile g : grille1) {
                    if (Intersector.overlaps(entite, g.getBoundingRectangle())) {
                        return g;
                    }
                }
            }
        }

        return null;
    }

    public static Tile getCaseFor(Rectangle entite, String filters) {
        if (entite != null) {
            for (Tile[] grille1 : grid) {
                for (Tile g : grille1) {
                    if (Intersector.overlaps(entite, g.getBoundingRectangle()) && !filters.contains(g.state)) {
                        return g;
                    }
                }
            }
        }

        return null;
    }

    public static Tile getCaseFor(Circle entite, String filters) {
        if (entite != null) {
            for (Tile[] grille1 : grid) {
                for (Tile g : grille1) {
                    if (Intersector.overlaps(entite, g.getBoundingRectangle()) && !filters.contains(g.state)) {
                        return g;
                    }
                }
            }
        }

        return null;
    }

    public static void setState(String b, Rectangle entite) {
        if (entite != null) {
            for (Tile[] grille1 : grid) {
                for (Tile g : grille1) {
                    if (Intersector.overlaps(entite, g.getBoundingRectangle())) {
                        g.state = b;
                    }
                }
            }
        }
    }

    public static void setState(String state, int row, int col) {
        grid[Math.abs(row - ROWCOUNT) - 1][col].state = state;
    }

    public static void tour() {
        Tmap.spriteBatch.setColor(Color.WHITE);
        for (Tile[] object : GridManager.grid) {
            for (Tile tile : object) {
                if (tile.state.equals(Constantes.WALL)) {
                    drawWall(tile);
                } else {
                    drawFloor(tile);
                }
            }
        }

    }

    private static void drawFloor(Tile tile) {
        if (ColorManager.mode)
            Tmap.spriteBatch.draw(TextureUtil.getTexture(FLOOR), tile.getX(), tile.getY(), tile.getWidth(), tile.getHeight());
        else
            Tmap.spriteBatch.draw(TextureUtil.getTexture(WALL), tile.getX(), tile.getY(), tile.getWidth(), tile.getHeight());
    }

    private static void drawWall(Tile tile) {
        if (ColorManager.mode)
            Tmap.spriteBatch.draw(TextureUtil.getTexture(WALL), tile.getX(), tile.getY(), tile.getWidth(), tile.getHeight());
        else
            Tmap.spriteBatch.draw(TextureUtil.getTexture(FLOOR), tile.getX(), tile.getY(), tile.getWidth(), tile.getHeight());
    }

    public static boolean isClearZone(Rectangle entite) {
        for (Tile[] grille1 : grid) {
            for (Tile g : grille1) {
                if (!Constantes.NPC_MOVEMENT_OK.contains(g.state) && Intersector.overlaps(g.getBoundingRectangle(), entite)) {
                    return false;
                }
            }
        }
        return true;
    }

}
