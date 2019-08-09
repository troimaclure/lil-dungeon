/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.kikijoli.ville.drawable.entite.decor.Water;
import com.kikijoli.ville.pathfind.GridManager;
import com.kikijoli.ville.util.Constantes;

/**
 *
 * @author Arthur
 */
public class StageManager {

    private static final String SEPARATOR = "\\r?\\n";
    private static final String EMPTY = "";

    public static void load(int level) {
        FileHandle internal = Gdx.files.internal("stage/" + level + ".txt");
        String[] contentSplit = internal.readString().split(SEPARATOR);
        GridManager.initialize(contentSplit.length, contentSplit[0].split(EMPTY).length, Constantes.TILESIZE);
        int i = 0;
        for (String row : contentSplit) {
            int r = 0;
            String[] cols = row.split(EMPTY);
            for (String col : cols) {
                GridManager.setState(col, i, r++);
                switch (col) {
                    case Constantes.WATER:
                        WaterManager.addWater(new Water((r - 1) * Constantes.TILESIZE, Math.abs(i - cols.length) * Constantes.TILESIZE));
                        break;
                    case Constantes.LOCK:
                        LockManager.addLock((r - 1) * Constantes.TILESIZE, Math.abs(i - cols.length) * Constantes.TILESIZE);
                        break;
                    case Constantes.KEY:
                        LockManager.addKey((r - 1) * Constantes.TILESIZE, Math.abs(i - cols.length) * Constantes.TILESIZE);
                        break;
                }
            }
            i++;
        }

    }

}
