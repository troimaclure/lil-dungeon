/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.kikijoli.ville.drawable.entite.decor.Water;
import com.kikijoli.ville.drawable.entite.npc.Archer;
import com.kikijoli.ville.drawable.entite.npc.Guard;
import com.kikijoli.ville.drawable.entite.npc.KeyGuard;
import com.kikijoli.ville.drawable.entite.npc.Player;
import com.kikijoli.ville.drawable.entite.npc.RollingTrap;
import com.kikijoli.ville.drawable.entite.npc.Trap;
import com.kikijoli.ville.drawable.entite.npc.Turret;
import static com.kikijoli.ville.manager.EntiteManager.player;
import com.kikijoli.ville.maps.Tmap;
import com.kikijoli.ville.pathfind.GridManager;
import com.kikijoli.ville.util.Constantes;
import java.util.ArrayList;

/**
 *
 * @author Arthur
 */
public class StageManager {

    private static final String SEPARATOR = "\\r?\\n";
    private static final String EMPTY = "";
    private static String currentLevel;

    private static final String TXT = ".txt";
    private static final String STAGE_PATH = "stage/";
    public static int stopwatch;
    public static TiledMapRenderer tiledMapRenderer;
    public static TiledMap tiledMap;
    public static ArrayList<Rectangle> walls = new ArrayList<>();
    public static ArrayList<Rectangle> hideouts = new ArrayList<>();
    public static final String PHYSIQUE = "physique";
    public static final String LIGHT_PHYSIQUE = "light_physique";
    public static final String ENTITE = "entite";

    public static void loadFromXml(String level) {
        tiledMap = new TmxMapLoader().load("stage/" + level + ".tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        stopwatch = 60 * 60;
        RankManager.currentStagePoint = 0;
        createWall();
        createEntite();
        createHideOut();
        //<editor-fold defaultstate="collapsed" desc="grid initialisation">
//        GridManager.initialize(load.length, load[0].length, Constantes.TILESIZE);
//        int i = 0;
//        for (Tile[] tileDTOs : load) {
//            int r = 0;
//            for (Tile tileDTO : tileDTOs) {
//                GridManager.setState(tileDTO.code, i, r++);
//                int x = (r - 1) * Constantes.TILESIZE;
//                int y = Math.abs(i - tileDTOs.length + 1) * Constantes.TILESIZE;
//                handleElement(tileDTO.code, x, y, tileDTO.data);
//            }
//            i++;
//        }
//        currentLevel = level;
//        EntiteManager.arrowCount = (int) EntiteManager.entites.stream().filter(e -> e != EntiteManager.player).count();
//</editor-fold>
    }

    private static void createWall() {
        MapLayer collisionObjectLayer = (MapLayer) tiledMap.getLayers().get(PHYSIQUE);
        MapObjects objects = collisionObjectLayer.getObjects();
        for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {
            Rectangle rectangle = rectangleObject.getRectangle();
            Tmap.addBox((int) rectangle.getX(), (int) rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
            walls.add(rectangle);
        }
    }

    private static void createHideOut() {
        MapLayer collisionObjectLayer = (MapLayer) tiledMap.getLayers().get(LIGHT_PHYSIQUE);
        MapObjects objects = collisionObjectLayer.getObjects();
        for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {
            hideouts.add(rectangleObject.getRectangle());
        }
    }

    private static void createEntite() {
        MapLayer collisionObjectLayer = (MapLayer) tiledMap.getLayers().get(ENTITE);
        MapObjects objects = collisionObjectLayer.getObjects();
        for (TiledMapTileMapObject entite : objects.getByType(TiledMapTileMapObject.class)) {
            if ("player".equals(entite.getName())) {
                EntiteManager.player.setX(entite.getX());
                EntiteManager.player.setY(entite.getY());
                continue;
            }
            ThemeManager.currentTheme.handleFromTmx(entite);
        }

    }

    public static void tour() {
        stopwatch -= stopwatch > 0 ? 1 : 0;
        if (stopwatch <= 0) {
            EntiteManager.touch(player);
        }
    }

    public static void setLevel(String level) {

        EntiteManager.entites.removeIf(e -> e != EntiteManager.player);
        ParticleManager.particleEffects.removeIf(e -> e != EntiteManager.ball);
        EntiteManager.clearDead();
        LockManager.doors.clear();
        LockManager.locks.clear();
        LockManager.keys.clear();
        EntiteManager.keys.clear();
        DrawManager.entites.clear();
        WaterManager.waters.clear();
        DrawManager.spritesFilled.clear();
        ThemeManager.currentTheme.getTiles().stream().forEach(e -> {
            e.disabled = false;
            e.count = 0;
        });
        ProjectileManager.projectiles.clear();
        SpellManager.spells.clear();
        Tmap.getRay().removeAll();
        loadFromXml(level);
    }

    static String getCurrentLevel() {
        return currentLevel;
    }

    public static void reload() {
        EntiteManager.playerDead = false;
        EntiteManager.clearDead();
        EntiteManager.player = new Player(0, 0);
        HudManager.setSelected(0);
        EntiteManager.addEntite(player);
        setLevel(getCurrentLevel());
    }

    public static boolean isClearZone(Rectangle moved) {
        return !walls.stream().filter(e -> e.overlaps(moved)).findFirst().isPresent();
    }

    private StageManager() {

    }

}
