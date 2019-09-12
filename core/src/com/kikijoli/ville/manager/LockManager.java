/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.manager;

import com.kikijoli.ville.drawable.entite.build.Door;
import com.kikijoli.ville.drawable.entite.build.Key;
import com.kikijoli.ville.drawable.entite.build.Lock;
import com.kikijoli.ville.maps.Tmap;
import java.util.ArrayList;

/**
 *
 * @author Arthur
 */
public class LockManager {

    public static ArrayList<Lock> locks = new ArrayList<>();
    public static ArrayList<Door> doors = new ArrayList<>();
    public static ArrayList<Key> keys = new ArrayList<>();

    public static void addLock(int x, int y) {
        locks.add(new Lock(x, y));
    }

    public static void addKey(int x, int y) {
        keys.add(new Key(x, y));
    }

    public static void tour() {
        Tmap.spriteBatch.setColor(ColorManager.getTextureColor());
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
}
