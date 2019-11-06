package com.kikijoli.ville.pathfind;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

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

    public static void setState(String state, int row, int col) {
        grid[Math.abs(row - ROWCOUNT) - 1][col].state = state;
    }

}
