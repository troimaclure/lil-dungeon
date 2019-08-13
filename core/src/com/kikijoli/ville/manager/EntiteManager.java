package com.kikijoli.ville.manager;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.drawable.entite.build.Key;
import com.kikijoli.ville.drawable.entite.build.Lock;
import com.kikijoli.ville.drawable.entite.npc.Player;
import com.kikijoli.ville.listeners.GeneralKeyListener;
import com.kikijoli.ville.maps.Tmap;
import static com.kikijoli.ville.maps.Tmap.spriteBatch;
import static com.kikijoli.ville.maps.Tmap.worldCoordinates;
import com.kikijoli.ville.pathfind.GridManager;
import com.kikijoli.ville.shader.WalkShader;
import com.kikijoli.ville.util.Constantes;
import java.util.ArrayList;

/**
 *
 * @author troïmaclure
 */
public class EntiteManager {

    public static Player player = new Player(100, 100);
    public static ArrayList<Entite> entites = new ArrayList<>();
    public static ParticleEffect ball;
    public static ArrayList<Rectangle> walls = new ArrayList<Rectangle>();
    private static final ArrayList<Key> keys = new ArrayList<>();
    public static boolean playedBall = false;
    public static Vector2 currentBallPosition = new Vector2();

    public static ArrayList<Entite> getEntites() {
        return (ArrayList<Entite>) entites.clone();
    }

    public static void addEntite(Entite entite) {
        entites.add(entite);

    }

    public static void tour() {
        Color c = spriteBatch.getColor();
        handlePlayer();
        handleBall();
        entites.stream().forEach((Entite entite) -> {
            renderEntity(entite);
        });
        spriteBatch.setColor(c);
        moveBall();
        removeDead();
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
        if (playedBall) return;
        for (int i = 0; i < player.speed; i++) {
            boolean move = handleY();
            move = handleX() || move;
            playerMove(move);
            handleGet();
            handleDoor();
        }
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
        if (keys.isEmpty()) return;
        for (Lock lock : LockManager.locks) {
            if (Intersector.overlaps(player.anchor, lock.getBoundingRectangle())) {
                doorOpen(lock);
                break;
            }
        }
    }

    private static void playerAddKey(Key key) {
        keys.add(key);
        LockManager.keys.remove(key);
        GridManager.setState(Constantes.EMPTY, key.getBoundingRectangle());

    }

    private static void doorOpen(Lock lock) {
        LockManager.locks.remove(lock);
        GridManager.setState(Constantes.EMPTY, lock.getBoundingRectangle());
        Tmap.removeBoxs(lock.getBoundingRectangle());
    }

    public static void handleBall() {
        if (!playedBall) return;
        if (GeneralKeyListener.KeyDown) currentBallPosition.y -= 4;
        else if (GeneralKeyListener.KeyUp) currentBallPosition.y += 4;
        if (GeneralKeyListener.KeyRight) currentBallPosition.x += 4;
        else if (GeneralKeyListener.KeyLeft) currentBallPosition.x -= 4;
        ball.setPosition(currentBallPosition.x, currentBallPosition.y);
    }

    public static void moveBall() {
        if (playedBall) return;
//        if (GeneralKeyListener.dragged && !GeneralKeyListener.rightButton) {
        ball.setPosition(worldCoordinates.x, worldCoordinates.y);
        currentBallPosition.x = worldCoordinates.x;
        currentBallPosition.y = worldCoordinates.y;
//        } else {
//            calculateBallMovement();
//            ball.setPosition(currentBallPosition.x, currentBallPosition.y);
//        }
    }

    private static void calculateBallMovement() {
        for (int i = 0; i <= player.speed + 1; i++) {
            if (currentBallPosition.x > player.getX() - 15 && currentBallPosition.x > player.getX() + 35)
                currentBallPosition.x -= 1;
            else if (currentBallPosition.x < player.getX() - 15 && currentBallPosition.x < player.getX() + 35)
                currentBallPosition.x += 1;
            if (currentBallPosition.y > player.getY() + 48 && currentBallPosition.y > player.getY() + 32)
                currentBallPosition.y -= 1;
            else if (currentBallPosition.y < player.getY() + 48 && currentBallPosition.y < player.getY() + 32)
                currentBallPosition.y += 1;
        }
    }

    public static void togglePlayerBall() {
        EntiteManager.playedBall = !EntiteManager.playedBall;
        player.shader = null;
    }

    public static void attack(Entite entite) {
        Circle c = entite.anchor;
        entites.stream().filter((target) -> (target != entite && Intersector.overlaps(c, target.getBoundingRectangle()))).forEachOrdered((target) -> {
            HitManager.hit(entite, target);
        });

    }

    private static void removeDead() {
        ArrayList<Entite> deads = new ArrayList<>();
        entites.stream().filter((entite) -> (entite.pv <= 0)).forEachOrdered((entite) -> {
            deads.add(entite);
        });
        entites.removeAll(deads);
    }
//
//    public static void select() {
//        for (Entite entite : entites) {
//            if(entite != player){
//                entiteSelected = entite; 
//            }
//        }
//    }

    private EntiteManager() {
    }

}
