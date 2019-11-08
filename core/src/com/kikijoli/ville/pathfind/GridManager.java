package com.kikijoli.ville.pathfind;

import box2dLight.Light;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import java.util.ArrayList;

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
    public static Tile[][] grid = new Tile[0][0];

    public static void initialize(int rowCount, int columnCount, int size) {
        COLUMNCOUNT = columnCount;
        ROWCOUNT = rowCount;
        grid = new Tile[columnCount][rowCount];
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
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

    public static Tile getCaseFor(Vector2 vector) {
        for (Tile[] grille1 : grid) {
            for (Tile g : grille1) {
                if (g.getBoundingRectangle().contains(vector)) {
                    return g;
                }
            }
        }

        return null;
    }

    public static Tile getCaseFor(Rectangle entite, String filters) {
        if (entite != null) {
            for (Tile[] grille1 : grid) {
                for (Tile g : grille1) {
                    if (Intersector.overlaps(entite, g.getBoundingRectangle()) && filters.contains(g.state)) {
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

    public static ArrayList<Tile> getTileFor(Light light) {
        ArrayList<Tile> tilesReturn = new ArrayList<>();
        for (Tile[] tiles : grid) {
            for (Tile tile : tiles) {
                if (light.contains(tile.getCenter().x, tile.getCenter().y)) {
                    tilesReturn.add(tile);
                }
            }
        }
        return tilesReturn;
    }

}
