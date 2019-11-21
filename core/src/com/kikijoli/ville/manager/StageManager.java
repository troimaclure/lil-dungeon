/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.manager;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.buffer.ShadowFBO;
import com.kikijoli.ville.drawable.entite.decor.Water;
import com.kikijoli.ville.drawable.entite.npc.Player;
import static com.kikijoli.ville.manager.EntiteManager.player;
import com.kikijoli.ville.maps.Tmap;
import com.kikijoli.ville.pathfind.GridManager;
import com.kikijoli.ville.util.Constantes;
import com.kikijoli.ville.util.Move;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Arthur
 */
public class StageManager {

    private static String currentLevel;
    public static int stopwatch;
    public static TiledMapRenderer tiledMapRenderer;
    public static TiledMap tiledMap;
    public static ArrayList<Rectangle> walls = new ArrayList<>();
    public static ArrayList<Rectangle> hideouts = new ArrayList<>();
    public static ArrayList<Rectangle> cannotmove = new ArrayList<>();

    public static final String PHYSIQUE = "physique";
    public static final String MOVE_PHYSIQUE = "move_physique";
    public static final String LIGHT_PHYSIQUE = "light_physique";
    public static final String ENTITE = "entite";
    public static final String PLAYER = "player";
    public static Integer widthd = 0;
    public static Integer heightd = 0;

    private static final String HEIGHT = "height";
    private static final String WIDTH = "width";

    public static final String WEATHER = "weather";

    public static void loadFromXml(String level) {
        tiledMap = new TmxMapLoader().load("stage/" + level + ".tmx");
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        stopwatch = 60 * 60;
        RankManager.currentStagePoint = 0;

        widthd = (Integer) tiledMap.getProperties().get(WIDTH);
        heightd = (Integer) tiledMap.getProperties().get(HEIGHT);
        Tmap.removeAllBoxs();
        GridManager.initialize(widthd, heightd, Constantes.TILESIZE);
        ShadowFBO.lightSize = widthd * Constantes.TILESIZE;
        EntiteManager.arrowCount = 0;
        EntiteManager.pebbleCount = 3;
        EntiteManager.vanishCount = 2;
        createWall();
        createWater();
        createCannotMove();
        createEntite();
        createHideOut();
        Move.initialize();
        WeatherManager.setCurrentWeather((String)tiledMap.getProperties().get(WEATHER));

        currentLevel = level;

    }

    private static void createWall() {
        MapLayer collisionObjectLayer = (MapLayer) tiledMap.getLayers().get(PHYSIQUE);
        MapObjects objects = collisionObjectLayer.getObjects();
        for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {
            Rectangle rectangle = rectangleObject.getRectangle();
            Tmap.addBox((int) rectangle.getX(), (int) rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
            GridManager.setState("1", rectangle);
            walls.add(rectangle);
        }
    }

    private static void createCannotMove() {
        MapLayer collisionObjectLayer = (MapLayer) tiledMap.getLayers().get(MOVE_PHYSIQUE);
        MapObjects objects = collisionObjectLayer.getObjects();
        for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {
            Rectangle rectangle = rectangleObject.getRectangle();
            GridManager.setState("1", rectangle);
            cannotmove.add(rectangle);
        }
    }

    private static void createWater() {
        MapLayer collisionObjectLayer = (MapLayer) tiledMap.getLayers().get("water");
        MapObjects objects = collisionObjectLayer.getObjects();
        for (TiledMapTileMapObject water : objects.getByType(TiledMapTileMapObject.class)) {
            GridManager.setState("1", new Rectangle(water.getX(), water.getY(), Constantes.TILESIZE, Constantes.TILESIZE));
            WaterManager.addWater(new Water(water.getX(), water.getY()));
        }
    }

    private static void createHideOut() {
        MapLayer collisionObjectLayer = (MapLayer) tiledMap.getLayers().get(LIGHT_PHYSIQUE);
        MapObjects objects = collisionObjectLayer.getObjects();
        for (RectangleMapObject rectangleObject : objects.getByType(RectangleMapObject.class)) {
            Rectangle rectangle = rectangleObject.getRectangle();
            Tmap.addBox((int) rectangle.getX(), (int) rectangle.getY(), rectangle.getWidth(), rectangle.getHeight());
            hideouts.add(rectangle);
        }
    }

    private static void createEntite() {
        MapLayer collisionObjectLayer = (MapLayer) tiledMap.getLayers().get(ENTITE);
        MapObjects objects = collisionObjectLayer.getObjects();
        for (TiledMapTileMapObject entite : objects.getByType(TiledMapTileMapObject.class)) {
            if (PLAYER.equals(entite.getName())) {
                EntiteManager.player.setX(entite.getX());
                EntiteManager.player.setY(entite.getY());
                continue;
            }
            ThemeManager.currentTheme.handleFromTmx(entite);
        }

    }

    public static void tour() {
        stopwatch -= stopwatch > 0 ? 0 : 0;
        if (stopwatch <= 0) {
            EntiteManager.touch(player);
        }
    }

    public static void setLevel(String level) {

        CheckpointManager.currentCheckpoint = null;
        EntiteManager.entites.removeIf(e -> e != EntiteManager.player);
        ParticleManager.particleEffects.removeIf(e -> e != EntiteManager.ball);
        EntiteManager.clearDead();
        LockManager.doors.clear();
        LockManager.locks.clear();
        ObjectManager.objects.clear();
        EntiteManager.keys.clear();
        DrawManager.entites.clear();
        WaterManager.waters.clear();
        DrawManager.spritesFilled.clear();
        DrawManager.spritesDrawed.clear();
        CheckpointManager.firecamps.clear();
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

        if (handleChekpoint()) {
            return;
        }
        EntiteManager.clearDead();
        EntiteManager.player = new Player(0, 0);
        HudManager.setSelected(0);
        EntiteManager.addEntite(player);

        setLevel(getCurrentLevel());
    }

    public static boolean isClearZone(Rectangle moved, List<Rectangle> rectangle) {
        return !rectangle.stream().filter(e -> e.overlaps(moved)).findFirst().isPresent();
    }

    public static boolean isClearZone(Vector2 moved, List<Rectangle> rectangle) {
        return !rectangle.stream().filter(e -> e.contains(moved)).findFirst().isPresent();
    }

    private static boolean handleChekpoint() {
        if (CheckpointManager.currentCheckpoint == null) return false;
        CheckpointManager.currentCheckpoint.gameState.load();
        return true;
    }

    private StageManager() {

    }

}
