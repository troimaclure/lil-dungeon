/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.manager;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.kikijoli.ville.drawable.entite.build.Door;
import com.kikijoli.ville.drawable.entite.build.Lock;
import static com.kikijoli.ville.manager.EntiteManager.player;
import static com.kikijoli.ville.manager.StageManager.stopwatch;
import com.kikijoli.ville.maps.Tmap;
import com.kikijoli.ville.pathfind.GridManager;
import com.kikijoli.ville.util.MathUtils;
import com.kikijoli.ville.util.Move;
import com.kikijoli.ville.util.SetLevel;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 *
 * @author Arthur
 */
public class LockManager {

    public static ArrayList<Lock> locks = new ArrayList<>();
    public static ArrayList<Rectangle> lockRectangles = new ArrayList<>();
    public static ArrayList<Door> doors = new ArrayList<>();

    public static void addLock(float x, float y) {
        Lock lock = new Lock(x, y);
        locks.add(lock);
        Tmap.addBox(x, y);
        GridManager.setState("1", lock.getBoundingRectangle());
    }

    public static void addLock(Lock lock) {
        locks.add(lock);
        Tmap.addBox(lock.getX(), lock.getY());
        GridManager.setState("1", lock.getBoundingRectangle());
    }

    public static void draw() {
        locks.forEach((lock) -> {
            lock.draw(Tmap.spriteBatch);
        });

        doors.forEach((t) -> {
            t.draw(Tmap.spriteBatch);
        });
    }

    public static void addDoor(int x, int y, String data) {
        doors.add(new Door(x, y, data));
    }

    public static void addDoor(Door door) {
        doors.add(door);
    }

    public static ArrayList<Rectangle> getLocksRectangle() {
        if (lockRectangles.isEmpty()) {
            refeshLocksRectangle();
        }
        return lockRectangles;
    }

    public static void handleDoor() {
        for (Lock lock : LockManager.locks) {
            if (!EntiteManager.keys.isEmpty() && Intersector.overlaps(player.anchor, lock.getBoundingRectangle())) {
                lockOpen(lock);
                break;
            }
        }

        for (Door door : LockManager.doors) {
            if (player.anchor.contains(MathUtils.getCenter(EntiteManager.player.getBoundingRectangle())) && Intersector.overlaps(player.anchor, door.getBoundingRectangle())) {
                doorOpen(door);
                break;
            }
        }
    }

    private static void doorOpen(Door door) {
        if (Tmap.setLevel == null) {
            SoundManager.playSound(SoundManager.END_OF_LEVEL);
            SoundManager.playSound(SoundManager.LEVEL_END_ANIM_SCREEN);
            Tmap.setLevel = new SetLevel(door.data);
            RankManager.point += MathUtils.transformIpsToSec(stopwatch) * RankManager.TIME_POINT;
            RankManager.point += RankManager.currentStagePoint;
        }
    }

    public static void lockOpen(Lock lock) {
        SoundManager.playSound(SoundManager.OPEN_DOOR);
        EntiteManager.keys.remove(0);
        LockManager.locks.remove(lock);
        lock.talkDouble("Open !", Color.BLACK, Color.CYAN);
        Tmap.removeBoxs(lock.getBoundingRectangle());
        GridManager.setState("0", lock.getBoundingRectangle());
        refeshLocksRectangle();
        Move.initialize();
    }

    public static void refeshLocksRectangle() {
        lockRectangles = (ArrayList) locks.stream().map(e -> e.getBoundingRectangle()).collect(Collectors.toList());
    }
}
