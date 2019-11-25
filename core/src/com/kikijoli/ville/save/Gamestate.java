package com.kikijoli.ville.save;

import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.drawable.entite.npc.Player;
import com.kikijoli.ville.manager.CheckpointManager;
import com.kikijoli.ville.manager.DrawManager;
import com.kikijoli.ville.manager.EntiteManager;
import com.kikijoli.ville.manager.HudManager;
import com.kikijoli.ville.manager.LockManager;
import com.kikijoli.ville.manager.ObjectManager;
import com.kikijoli.ville.manager.ProjectileManager;
import com.kikijoli.ville.manager.RankManager;
import com.kikijoli.ville.maps.Tmap;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ajosse
 */
public final class Gamestate implements Serializable {

    int arrowCount, pebbleCount, pv, currentPoint, vanishCount;
    float x, y;
    byte[] byteArray;
    ArrayList<EntiteWrapper> entiteWrappers = new ArrayList<>();

    public Gamestate() {

    }

    public void write() {
        ObjectOutputStream os = null;
        try (ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream()) {

            EntiteManager.entites.forEach((entite) -> {
                if (entite instanceof Player) return;
                entiteWrappers.add(entite.write(EntiteManager.class.getCanonicalName(), "addEntite"));
            });
            EntiteManager.deads.forEach((entite) -> {
                entiteWrappers.add(entite.write(EntiteManager.class.getCanonicalName(), "addDead"));
            });
            LockManager.doors.forEach((entite) -> {
                entiteWrappers.add(entite.write(LockManager.class.getCanonicalName(), "addDoor"));
            });
            LockManager.locks.forEach((entite) -> {
                entiteWrappers.add(entite.write(LockManager.class.getCanonicalName(), "addLock"));
            });
            EntiteManager.keys.forEach((entite) -> {
                entiteWrappers.add(entite.write(EntiteManager.class.getCanonicalName(), "addKey"));
            });
            ObjectManager.objects.forEach((entite) -> {
                entiteWrappers.add(entite.write(ObjectManager.class.getCanonicalName(), "addObject"));
            });
            DrawManager.entites.forEach((entite) -> {
                entiteWrappers.add(entite.write(DrawManager.class.getCanonicalName(), "addEntite"));
            });

            arrowCount = EntiteManager.arrowCount;
            pebbleCount = EntiteManager.pebbleCount;
            pv = EntiteManager.player.pv;
            currentPoint = RankManager.currentStagePoint;
            vanishCount = EntiteManager.vanishCount;
            x = EntiteManager.player.getX();
            y = EntiteManager.player.getY();
            os = new ObjectOutputStream(arrayOutputStream);
            os.writeObject(this);
            byteArray = arrayOutputStream.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger(Gamestate.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void load() {
        Gamestate g = getGameState();
        clearAll();
        buildEntite(g);
        buildGameProperties(g);
        buildPlayer(g);
        manageEntite();
    }

    public Gamestate getGameState() {
        ObjectInputStream is;
        ByteArrayInputStream in = new ByteArrayInputStream(byteArray);
        try {
            is = new ObjectInputStream(in);
            return (Gamestate) is.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Gamestate.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void buildPlayer(Gamestate g) {
        EntiteManager.player = new Player(g.x, g.y);
        EntiteManager.player.pv = g.pv;
        EntiteManager.addEntite(EntiteManager.player);
        HudManager.setSelected(0);
    }

    public void buildGameProperties(Gamestate g) {
        EntiteManager.arrowCount = g.arrowCount;
        EntiteManager.pebbleCount = g.pebbleCount;
        RankManager.currentStagePoint = g.currentPoint;
        EntiteManager.vanishCount = g.vanishCount;
    }

    public void buildEntite(Gamestate g) {
        g.entiteWrappers.forEach((entiteWrapper) -> {
            try {
                Class<?> forName = Class.forName(entiteWrapper.className);
                Constructor<?> constructor = forName.getConstructor(float.class, float.class);
                Entite newInstance = (Entite) constructor.newInstance(0, 0);
                newInstance.load(entiteWrapper);
            } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                Logger.getLogger(Gamestate.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    private void manageEntite() {
        CheckpointManager.firecamps.stream().filter((firecamp) -> (firecamp.touch)).forEachOrdered((firecamp) -> {
            firecamp.resetLight();
        });
    }

    public void clearAll() {
        Tmap.getRay().removeAll();
        ProjectileManager.projectiles.clear();
        DrawManager.spritesFilled.clear();
        DrawManager.spritesDrawed.clear();
        EntiteManager.entites.clear();
        EntiteManager.deads.clear();
        LockManager.doors.clear();
        LockManager.locks.clear();
        EntiteManager.keys.clear();
        ObjectManager.objects.clear();
        DrawManager.entites.clear();
    }

    public int getArrowCount() {
        return arrowCount;
    }

    public void setArrowCount(int arrowCount) {
        this.arrowCount = arrowCount;
    }

    public int getPebbleCount() {
        return pebbleCount;
    }

    public void setPebbleCount(int pebbleCount) {
        this.pebbleCount = pebbleCount;
    }

    public int getPv() {
        return pv;
    }

    public void setPv(int pv) {
        this.pv = pv;
    }

    public int getCurrentPoint() {
        return currentPoint;
    }

    public void setCurrentPoint(int currentPoint) {
        this.currentPoint = currentPoint;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public byte[] getByteArray() {
        return byteArray;
    }

    public void setByteArray(byte[] byteArray) {
        this.byteArray = byteArray;
    }

    public ArrayList<EntiteWrapper> getEntiteWrappers() {
        return entiteWrappers;
    }

    public void setEntiteWrappers(ArrayList<EntiteWrapper> entiteWrappers) {
        this.entiteWrappers = entiteWrappers;
    }

}
