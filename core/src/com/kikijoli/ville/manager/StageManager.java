/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.kikijoli.ville.drawable.entite.decor.Water;
import com.kikijoli.ville.drawable.entite.npc.Guard;
import com.kikijoli.ville.maps.Tmap;
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
                int x = (r - 1) * Constantes.TILESIZE;
                int y = Math.abs(i - cols.length + 1) * Constantes.TILESIZE;
                switch (col) {
                    case Constantes.WATER:
                        WaterManager.addWater(new Water(x, y));
                        break;
                    case Constantes.LOCK:
                        LockManager.addLock(x, y);
                        Tmap.addBox(x, y);
                        break;
                    case Constantes.KEY:
                        LockManager.addKey(x, y);
                        break;
                    case Constantes.GUARD:
                        EntiteManager.addEntite(new Guard(x, y));
                        break;
                    case Constantes.PLAYER:
                        EntiteManager.player.setPosition(x, y);
                        break;
                    case Constantes.WALL:
                        Tmap.addBox(x, y);
                        break;
                }
            }
            i++;
        }

    }

    public static void setLevel(int level) {
        EntiteManager.entites.removeIf(e -> e != EntiteManager.player);
        ParticleManager.particleEffects.removeIf(e -> e != EntiteManager.ball);
        EntiteManager.deads.clear();
        DrawManager.entites.clear();
        DrawManager.sprites.clear();
        ProjectileManager.projectiles.clear();
        SpellManager.spells.clear();
        Tmap.getRay().removeAll();
        load(level);
    }

}
