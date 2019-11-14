package com.kikijoli.ville.util;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.drawable.entite.build.Door;
import com.kikijoli.ville.drawable.entite.build.Lock;
import com.kikijoli.ville.drawable.entite.object.Key;
import com.kikijoli.ville.interfaces.IShapeDrawable;
import com.kikijoli.ville.manager.DrawManager;
import com.kikijoli.ville.manager.EntiteManager;
import com.kikijoli.ville.manager.LockManager;
import com.kikijoli.ville.manager.ObjectManager;
import com.kikijoli.ville.manager.ParticleManager;
import com.kikijoli.ville.manager.ProjectileManager;
import com.kikijoli.ville.manager.RankManager;
import com.kikijoli.ville.manager.SpellManager;
import com.kikijoli.ville.manager.ThemeManager;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ajosse
 */
public final class Gamestate implements Serializable{

    ArrayList<Entite> entites = new ArrayList<>();
    ArrayList<Entite> deads = new ArrayList<>();
    ArrayList<Door> doors = new ArrayList<>();
    ArrayList<Lock> locks = new ArrayList<>();
    ArrayList<Key> keysAdded = new ArrayList<>();
    ArrayList<Entite> objects = new ArrayList<>();
    ArrayList<ParticleEffect> particleEffects = new ArrayList<>();
    ArrayList<Entite> entitesDraw = new ArrayList<>();
    ArrayList<IShapeDrawable> shapeDraw = new ArrayList<>();
    ArrayList<IShapeDrawable> shapeFilled = new ArrayList<>();
    int arrowCount, pebbleCount, pv, currentPoint;
    float x, y;
    byte[] byteArray;

    public Gamestate() {

    }

    public void write() {
        ObjectOutputStream os = null;
        try (ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream()) {
            entites = EntiteManager.entites;
            particleEffects = ParticleManager.particleEffects;
            deads = EntiteManager.deads;
            doors = LockManager.doors;
            locks = LockManager.locks;
            keysAdded = EntiteManager.keys;
            objects = ObjectManager.objects;
            entitesDraw = DrawManager.entites;
            shapeDraw = DrawManager.spritesDrawed;
            shapeFilled = DrawManager.spritesFilled;
            arrowCount = EntiteManager.arrowCount;
            pebbleCount = EntiteManager.pebbleCount;
            pv = EntiteManager.player.pv;
            currentPoint = RankManager.currentStagePoint;
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
        Gamestate g = null;
        ByteArrayInputStream in = new ByteArrayInputStream(byteArray);
        ObjectInputStream is;
        try {
            is = new ObjectInputStream(in);
            g = (Gamestate) is.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Gamestate.class.getName()).log(Level.SEVERE, null, ex);
        }

        EntiteManager.entites = g.entites;
        ParticleManager.particleEffects = g.particleEffects;
        EntiteManager.deads = g.deads;
        LockManager.doors = g.doors;
        LockManager.locks = g.locks;
        EntiteManager.keys = g.keysAdded;
        EntiteManager.arrowCount = g.arrowCount;
        EntiteManager.pebbleCount = g.pebbleCount;
        EntiteManager.player.pv = g.pv;
        RankManager.currentStagePoint = g.currentPoint;

        DrawManager.entites = g.entitesDraw;
        DrawManager.spritesFilled = g.shapeFilled;
        DrawManager.spritesDrawed = g.shapeDraw;
        ThemeManager.currentTheme.getTiles().stream().forEach(e -> {
            e.disabled = false;
            e.count = 0;
        });
        ProjectileManager.projectiles.clear();
        SpellManager.spells.clear();
        EntiteManager.player.setX(g.x);
        EntiteManager.player.setY(g.y);
        if (!EntiteManager.entites.contains(EntiteManager.player)) {
            EntiteManager.entites.add(EntiteManager.player);
        }
    }

    public ArrayList<Entite> getEntites() {
        return entites;
    }

    public void setEntites(ArrayList<Entite> entites) {
        this.entites = entites;
    }

    public ArrayList<Entite> getDeads() {
        return deads;
    }

    public void setDeads(ArrayList<Entite> deads) {
        this.deads = deads;
    }

    public ArrayList<Door> getDoors() {
        return doors;
    }

    public void setDoors(ArrayList<Door> doors) {
        this.doors = doors;
    }

    public ArrayList<Lock> getLocks() {
        return locks;
    }

    public void setLocks(ArrayList<Lock> locks) {
        this.locks = locks;
    }

    public ArrayList<Key> getKeysAdded() {
        return keysAdded;
    }

    public void setKeysAdded(ArrayList<Key> keysAdded) {
        this.keysAdded = keysAdded;
    }

    public ArrayList<Entite> getObjects() {
        return objects;
    }

    public void setObjects(ArrayList<Entite> objects) {
        this.objects = objects;
    }

    public ArrayList<ParticleEffect> getParticleEffects() {
        return particleEffects;
    }

    public void setParticleEffects(ArrayList<ParticleEffect> particleEffects) {
        this.particleEffects = particleEffects;
    }

    public ArrayList<Entite> getEntitesDraw() {
        return entitesDraw;
    }

    public void setEntitesDraw(ArrayList<Entite> entitesDraw) {
        this.entitesDraw = entitesDraw;
    }

    public ArrayList<IShapeDrawable> getShapeDraw() {
        return shapeDraw;
    }

    public void setShapeDraw(ArrayList<IShapeDrawable> shapeDraw) {
        this.shapeDraw = shapeDraw;
    }

    public ArrayList<IShapeDrawable> getShapeFilled() {
        return shapeFilled;
    }

    public void setShapeFilled(ArrayList<IShapeDrawable> shapeFilled) {
        this.shapeFilled = shapeFilled;
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

}
