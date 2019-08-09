package com.kikijoli.ville.pathfind;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.kikijoli.ville.manager.ColorManager;
import static com.kikijoli.ville.maps.Tmap.shapeRenderer;
import com.kikijoli.ville.util.Constantes;

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
        shapeRenderer.begin(ShapeType.Filled);
        Color c = shapeRenderer.getColor();
        for (Tile[] object : GridManager.grid) {
            for (Tile tile : object) {
                if (tile.state.equals(Constantes.WALL)) {
                    shapeRenderer.setColor(ColorManager.getTextureColor());
                } else {
                    shapeRenderer.setColor(ColorManager.getBackgroundColor());
                }
                shapeRenderer.rect(tile.getX(), tile.getY(), tile.getWidth(), tile.getHeight());
            }
        }
        shapeRenderer.setColor(c);
        shapeRenderer.end();
    }

    public static boolean isClearZone(Rectangle entite) {
        for (Tile[] grille1 : grid) {
            for (Tile g : grille1) {
                if (!g.state.equals(Constantes.EMPTY) && !g.state.equals(Constantes.KEY) && Intersector.overlaps(g.getBoundingRectangle(), entite)) {
                    return false;
                }
            }
        }
        return true;
    }

}
