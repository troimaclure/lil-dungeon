package com.kikijoli.ville.pathfind;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.manager.ThemeManager;
import com.kikijoli.ville.maps.Tmap;
import com.kikijoli.ville.util.Constantes;
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

    public static ArrayList<Tile> getCasesFor(Rectangle entite) {
        ArrayList<Tile> al = new ArrayList<>();
        if (entite != null) {
            for (Tile[] grille1 : grid) {
                for (Tile g : grille1) {
                    if (Intersector.overlaps(entite, g.getBoundingRectangle())) {
                        al.add(g);
                    }
                }
            }
        }
        return al;
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

    public static boolean isClearZone(Vector2 v, String filter) {
        for (Tile[] grille1 : grid) {
            for (Tile g : grille1) {
                if (!filter.contains(g.state) && g.getBoundingRectangle().contains(v)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isClearZone(Rectangle v, String filter) {
        for (Tile[] grille1 : grid) {
            for (Tile g : grille1) {
                if (!filter.contains(g.state) && g.getBoundingRectangle().overlaps(v)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void removeBuild(Entite entite) {
        Tile t = getCaseFor(entite.getBoundingRectangle());
        if (t == null) return;
        t.state = Constantes.EMPTY;
    }
}
