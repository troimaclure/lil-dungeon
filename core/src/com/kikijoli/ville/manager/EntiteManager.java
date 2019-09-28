package com.kikijoli.ville.manager;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.drawable.entite.build.Door;
import com.kikijoli.ville.drawable.entite.build.Key;
import com.kikijoli.ville.drawable.entite.build.Lock;
import com.kikijoli.ville.drawable.entite.npc.Player;
import com.kikijoli.ville.drawable.entite.projectile.Spell.Spell;
import com.kikijoli.ville.drawable.entite.simple.Blood;
import com.kikijoli.ville.listeners.GeneralKeyListener;
import static com.kikijoli.ville.manager.StageManager.stopwatch;
import com.kikijoli.ville.maps.Tmap;
import static com.kikijoli.ville.maps.Tmap.spriteBatch;
import static com.kikijoli.ville.maps.Tmap.worldCoordinates;
import com.kikijoli.ville.pathfind.GridManager;
import com.kikijoli.ville.shader.WalkShader;
import com.kikijoli.ville.util.Constantes;
import com.kikijoli.ville.util.MathUtils;
import com.kikijoli.ville.util.SetLevel;
import java.util.ArrayList;

/**
 *
 * @author troïmaclure
 */
public class EntiteManager {

    public static Player player = new Player(100, 100);
    public static ArrayList<Entite> entites = new ArrayList<>();
    public static ArrayList<Entite> removes = new ArrayList<>();
    private static final ArrayList<Entite> deads = new ArrayList<>();
    public static ParticleEffect ball;
    public static ArrayList<Rectangle> walls = new ArrayList<Rectangle>();
    public static final ArrayList<Key> keys = new ArrayList<>();
    public static Vector2 currentBallPosition = new Vector2();
    public static boolean playerDead;
    public static int arrowCount = 0;

    public static void addEntite(Entite entite) {

        entites.add(entite);
    }

    public static void tour() {
        Color c = spriteBatch.getColor();
        handlePlayer();
        handleBall();
        entites.stream().forEach((Entite entite) -> {
            renderEntity(entite);
            entite.effects.forEach((effect) -> {
                effect.tour(entite);
            });
        });
        spriteBatch.setColor(c);
        moveBall();
        handleDeads();
        for (Entite remove : removes) {
            remove.effects.forEach((effect) -> {
                ParticleManager.particleEffects.remove(effect.effect);
            });
            remove.effects.clear();
        }
        entites.forEach((entite) -> {
            entite.effects.stream().filter((effect) -> (effect.end)).forEachOrdered((effect) -> {
                ParticleManager.particleEffects.remove(effect.effect);
            });
            entite.effects.removeIf(e -> e.end);
        });
        entites.removeAll(removes);
    }

    private static void renderEntity(Entite entite) {
        spriteBatch.setColor(ColorManager.getTextureColor());
        spriteBatch.setShader(entite.shader);
        if (entite.buisiness != null) {
            entite.buisiness.act();
        }
        if (entite.visible) {
            entite.draw(spriteBatch);
        }
        spriteBatch.setShader(ShaderManager.defaultShader);
    }

    private static void handlePlayer() {
        for (int i = 0; i < player.speed; i++) {
            if (isPlayerDead()) {
                boolean move = handleY();
                move = handleX() || move;
                playerMove(move);
            }
            handleGet();
            handleDoor();
        }
    }

    private static boolean isPlayerDead() {
        return player.buisiness != null;
    }

    private static void playerMove(boolean move) {
        if (player.shader == null && move) {
            player.shader = new WalkShader(player);
        } else if (player.shader instanceof WalkShader && !move) {
            player.shader = null;
        }
    }

    private static boolean handleX() {
        Rectangle moved = player.getBoundingRectangle();
        if (GeneralKeyListener.KeyLeft) {
            moved.x--;
        } else if (GeneralKeyListener.KeyRight) {
            moved.x++;
        }
        if (GridManager.isClearZone(moved)) {
            if (moved.x != player.getX()) {
                player.setPosition(moved.x, moved.y);
                return true;
            }
        }
        return false;
    }

    private static boolean handleY() {
        Rectangle moved = player.getBoundingRectangle();
        if (GeneralKeyListener.KeyDown) {
            moved.y--;
        } else if (GeneralKeyListener.KeyUp) {
            moved.y++;
        }
        if (GridManager.isClearZone(moved)) {
            if (moved.y != player.getY()) {
                player.setPosition(moved.x, moved.y);
                return true;
            }
        }
        return false;
    }

    public static void initialize() {
        addEntite(player);
        ball = ParticleManager.addParticle("particle/ball.p", 0, 0, 0.5f);
    }

    private static void handleGet() {
        for (Key key : LockManager.keys) {
            if (player.getBoundingRectangle().overlaps(key.getBoundingRectangle())) {
                playerAddKey(key);
                break;
            }
        }
    }

    private static void handleDoor() {
        for (Lock lock : LockManager.locks) {
            if (!keys.isEmpty() && Intersector.overlaps(player.anchor, lock.getBoundingRectangle())) {
                lockOpen(lock);
                break;
            }
        }
        for (Door door : LockManager.doors) {
            if (Intersector.overlaps(player.anchor, door.getBoundingRectangle())) {
                doorOpen(door);
                break;
            }
        }
    }

    private static void playerAddKey(Key key) {
        SoundManager.playSound(SoundManager.TAKE_KEY);
        keys.add(key);
        LockManager.keys.remove(key);
        GridManager.setState(Constantes.EMPTY, key.getBoundingRectangle());

    }

    private static void lockOpen(Lock lock) {
        SoundManager.playSound(SoundManager.OPEN_DOOR);
        keys.remove(0);
        LockManager.locks.remove(lock);
        GridManager.setState(Constantes.EMPTY, lock.getBoundingRectangle());
        Tmap.removeBoxs(lock.getBoundingRectangle());
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

    public static void handleBall() {
        if (GeneralKeyListener.KeyDown) currentBallPosition.y -= 4;
        else if (GeneralKeyListener.KeyUp) currentBallPosition.y += 4;
        if (GeneralKeyListener.KeyRight) currentBallPosition.x += 4;
        else if (GeneralKeyListener.KeyLeft) currentBallPosition.x -= 4;
        ball.setPosition(currentBallPosition.x, currentBallPosition.y);
    }

    public static void moveBall() {
        ball.setPosition(worldCoordinates.x, worldCoordinates.y);
        currentBallPosition.x = worldCoordinates.x;
        currentBallPosition.y = worldCoordinates.y;
    }

    public static void attack(Entite entite) {
        entites.stream().filter((target) -> (target != entite && target.good != entite.good && target.getBoundingRectangle().overlaps(entite.getBoundingRectangle()))).forEachOrdered((target) -> {
            touch(target);
        });
    }

    public static void touch(Entite entite) {
        if (entite == player && player.invincible) return;
        if (entite.shield != null) {
            entite.shield = null;
            SoundManager.playSound(SoundManager.SHIELD_CRASH);
            return;
        }

        addDead(entite);
        ParticleManager.addParticle("particle/blood.p", entite.getX(), entite.getY() + entite.getWidth(), 0.5f);
        entite.buisiness = null;
        Vector2 center = MathUtils.getCenter(entite.getBoundingRectangle());
        DrawManager.spritesFilled.add(new Blood(new Rectangle(center.x, entite.getY(), entite.getWidth(), entite.getHeight())));
        removes.add(entite);
    }

    private static void handleDeads() {
        deads.stream().forEachOrdered((dead) -> {
            if (dead.getRotation() < 90) {
                dead.setRotation(dead.getRotation() + 5);
            }
            dead.draw(spriteBatch);
            if (dead == player) {
                playerDead = true;
            }
        });
    }

    public static void spellEffect(Entite entite, Spell spell) {
        if (entite.effects.stream().anyMatch(e -> e.getClass() == spell.getEffectType()))
            return;
        entite.effects.add(spell.getEffect(entite.getX(), entite.getY()));
    }

    public static void addDead(Entite entite) {
        if (entite != player) {
            RankManager.currentStagePoint += entite.point;
            MessageManager.addIndicator(entite.getX(), entite.getY(), "+" + Integer.toString(entite.point) + "pts", entite);
            SoundManager.playSound(SoundManager.KILL);
        } else {
            SoundManager.playSound(SoundManager.DEATH);
        }
        deads.add(entite);
        entite.dead();
    }

    public static void clearDead() {
        deads.clear();
    }

    private EntiteManager() {
    }

}
