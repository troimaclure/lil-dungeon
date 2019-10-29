/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.manager;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.kikijoli.ville.drawable.entite.build.Door;
import com.kikijoli.ville.drawable.entite.build.Key;
import com.kikijoli.ville.drawable.entite.build.Lock;
import static com.kikijoli.ville.manager.EntiteManager.player;
import static com.kikijoli.ville.manager.StageManager.stopwatch;
import com.kikijoli.ville.maps.Tmap;
import com.kikijoli.ville.util.MathUtils;
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
    public static ArrayList<Key> keys = new ArrayList<>();

    public static void addLock(float x, float y) {
        locks.add(new Lock(x, y));
        Tmap.addBox(x, y);
    }

    public static void addKey(float x, float y) {
        keys.add(new Key(x, y));
    }

    public static void tour() {
        locks.forEach((lock) -> {
            lock.draw(Tmap.spriteBatch);
        });

        keys.forEach((key) -> {
            key.draw(Tmap.spriteBatch);
        });
        doors.forEach((t) -> {
            t.draw(Tmap.spriteBatch);
        });
    }

    public static void addDoor(int x, int y, String data) {
        doors.add(new Door(x, y, data));
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

    public static void playerAddKey(Key key) {
        SoundManager.playSound(SoundManager.TAKE_KEY);
        EntiteManager.keys.add(key);
        LockManager.keys.remove(key);
    }

    public static void lockOpen(Lock lock) {
        SoundManager.playSound(SoundManager.OPEN_DOOR);
        EntiteManager.keys.remove(0);
        LockManager.locks.remove(lock);
        Tmap.removeBoxs(lock.getBoundingRectangle());
        refeshLocksRectangle();
    }

    public static void refeshLocksRectangle() {
        lockRectangles = (ArrayList) locks.stream().map(e -> e.getBoundingRectangle()).collect(Collectors.toList());
    }
}
