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
import leveleditor.Tile;

/**
 *
 * @author Arthur
 */
public class StageManager {

    private static final String SEPARATOR = "\\r?\\n";
    private static final String EMPTY = "";

    public static void loadFromXml(String level) {
        Tile[][] load = XmlManager.load(level);
        GridManager.initialize(load.length, load[0].length, Constantes.TILESIZE);
        int i = 0;
        for (Tile[] tileDTOs : load) {
            int r = 0;
            for (Tile tileDTO : tileDTOs) {
                GridManager.setState(tileDTO.code, i, r++);
                int x = (r - 1) * Constantes.TILESIZE;
                int y = Math.abs(i - tileDTOs.length + 1) * Constantes.TILESIZE;
                handleElement(tileDTO.code, x, y, tileDTO.data);
            }
            i++;
        }
    }

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
                handleElement(col, x, y, "");
            }
            i++;
        }

    }

    private static void handleElement(String col, int x, int y, String data) {
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
            case Constantes.DOOR:
                LockManager.addDoor(x, y, data);
                break;
        }
    }

    public static void setLevel(String level) {
        EntiteManager.entites.removeIf(e -> e != EntiteManager.player);
        ParticleManager.particleEffects.removeIf(e -> e != EntiteManager.ball);
        EntiteManager.deads.clear();
        LockManager.doors.clear();
        LockManager.locks.clear();
        LockManager.keys.clear();
        DrawManager.entites.clear();
        DrawManager.sprites.clear();
        ProjectileManager.projectiles.clear();
        SpellManager.spells.clear();
        Tmap.getRay().removeAll();
        loadFromXml(level);
    }

}
