package com.kikijoli.ville.manager;

import box2dLight.PointLight;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.automation.prevent.Prevent;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.drawable.entite.object.Key;
import com.kikijoli.ville.drawable.entite.npc.Ennemy;
import com.kikijoli.ville.drawable.entite.npc.Player;
import com.kikijoli.ville.drawable.entite.projectile.Spell.Spell;
import com.kikijoli.ville.drawable.entite.simple.Blood;
import com.kikijoli.ville.drawable.entite.simple.Pebble;
import com.kikijoli.ville.interfaces.Ipv;
import com.kikijoli.ville.listeners.GeneralKeyListener;
import static com.kikijoli.ville.manager.LockManager.handleDoor;
import static com.kikijoli.ville.maps.Tmap.spriteBatch;
import static com.kikijoli.ville.maps.Tmap.worldCoordinates;
import com.kikijoli.ville.shader.WalkShader;
import com.kikijoli.ville.util.Count;
import com.kikijoli.ville.util.MathUtils;
import com.kikijoli.ville.util.Move;
import com.kikijoli.ville.util.Time;
import java.util.ArrayList;
import java.util.Optional;

/**
 *
 * @author troïmaclure
 */
public class EntiteManager {

    public static Player player = new Player(100, 100);
    public static ArrayList<Entite> entites = new ArrayList<>();
    public static ArrayList<Entite> removes = new ArrayList<>();
    public static ArrayList<Entite> deads = new ArrayList<>();
    public static ParticleEffect ball;
    public static ArrayList<Key> keys = new ArrayList<>();
    public static Vector2 currentBallPosition = new Vector2();
    public static boolean playerDead;
    public static int arrowCount = 0;
    public static int pebbleCount = 3;
    public static Vector2 currentMove = new Vector2(0, 0);
    private static int rotationExpected;

    public static void addEntite(Entite entite) {
        entites.add(entite);
    }

    public static void addKey(Key key) {
        keys.add(key);
    }

    public static void draw() {
        Color c = spriteBatch.getColor();
        handlePlayer();

        entites.stream().forEach((Entite entite) -> {
            renderEntity(entite);
            entite.effects.forEach((effect) -> {
                effect.draw(entite);
            });
        });
        spriteBatch.setColor(c);
        handleDeads();
    }

    public static void tour() {
        currentMove = new Vector2(0, 0);

        moveBall();

        for (Entite remove : removes) {
            remove.effects.forEach((effect) -> {
                ParticleManager.particleEffects.remove(effect.effect);
            });
            remove.effects.clear();
        }
        entites.forEach((entite) -> {
            if (entite.buisiness != null) {
                entite.buisiness.act();
            }
            entite.effects.stream().filter((effect) -> (effect.end)).forEachOrdered((effect) -> {
                ParticleManager.particleEffects.remove(effect.effect);
            });
            entite.effects.removeIf(e -> e.end);
        });
        entites.removeAll(removes);
    }

    private static void renderEntity(Entite entite) {
        spriteBatch.setShader(entite.shader);
        if (entite.visible) {
            entite.draw(spriteBatch);
        }
        spriteBatch.setShader(null);
    }

    private static void handlePlayer() {

        for (int i = 0; i < player.speed; i++) {
            if (isPlayerDead()) {
                boolean move = handleY();
                move = handleX() || move;
                playerMove(move);
            }
            handleDoor();
        }
        rotatePlayer();
        checkPlayerVisible();
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

    public static void rotatePlayer() {
        int oldRotation = rotationExpected;
        int y = (int) ((currentMove.y) * 45);
        switch ((int) currentMove.x) {
            case 1:
                rotationExpected = (int) (90 + y);
                break;
            case -1:
                rotationExpected = (int) (270 - y);
                break;
            default:
                if (currentMove.y == 1) {
                    rotationExpected = 180;
                } else {
                    rotationExpected = 0;
                }
                break;
        }
        if (oldRotation != rotationExpected)
            player.setRotation(rotationExpected);
    }

    private static boolean handleX() {
        Rectangle moved = new Rectangle(player.getX(), player.getY(), player.getWidth(), player.getHeight());
        if (GeneralKeyListener.KeyLeft) {
            moved.x--;
            currentMove.x = -1;
        } else if (GeneralKeyListener.KeyRight) {
            moved.x++;
            currentMove.x = 1;
        }
        if (StageManager.isClearZone(moved, Move.NPC_MOVE_FILTER)) {
            if (moved.x != player.getX()) {
                player.setX(moved.x);
                return true;
            }
        }

        return false;
    }

    private static boolean handleY() {
        Rectangle moved = new Rectangle(player.getX(), player.getY(), player.getWidth(), player.getHeight());
        if (GeneralKeyListener.KeyDown) {
            moved.y--;
            currentMove.y = -1;
        } else if (GeneralKeyListener.KeyUp) {
            moved.y++;
            currentMove.y = 1;
        }
        if (StageManager.isClearZone(moved, Move.NPC_MOVE_FILTER)) {
            if (moved.y != player.getY()) {
                player.setY(moved.y);
                return true;
            }
        }
        return false;
    }

    public static void initialize() {
        addEntite(player);
        ball = ParticleManager.addParticle("particle/ball.p", 0, 0, 0.5f);
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
        if (!entite.isTouchable || entite.touched) return;

        if (entite == player && (player.invincible)) return;
        if (entite.shield != null) {
            entite.shield = null;

            SoundManager.playSound(SoundManager.SHIELD_CRASH);
            return;
        }
        if (entite == player) {
            player.hurted();
        }
        if (entite instanceof Ipv) {
            ((Ipv) entite).setPv(((Ipv) entite).getPv() - 1);
            entite.touched = true;
            entite.talkDouble("Aïe", Color.BLACK, Color.WHITE);
            if (((Ipv) entite).getPv() > 0) return;

        }
        handleDead(entite);
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
        deads.add(entite);
        DrawManager.spritesFilled.add(new Blood(new Rectangle(entite.getCenter().x, entite.getY(), entite.getWidth(), entite.getHeight()), (int) (entite.getWidth() * 2)));
    }

    public static void handleDead(Entite entite) {
        if (entite != player) {
            SoundManager.playSound(SoundManager.KILL);
        } else {
            SoundManager.playSound(SoundManager.DEATH);
        }
        if (entite instanceof Ennemy) {
            if (!((Ennemy) entite).isAlarmed) {
                entite.talkDouble("SILENT KILL !", Color.WHITE, Color.RED, 60 * 2);
                RankManager.currentStagePoint += entite.point * 2;
            } else {
                entite.talkDouble("SHAMEFUL KILL...", Color.GRAY, Color.WHITE, 60 * 2);
                RankManager.currentStagePoint += entite.point / 2;
            }
        }
        deads.add(entite);
        entite.dead();
    }

    public static void clearDead() {
        deads.clear();
    }

    public static ArrayList<Entite> getEntites() {
        return (ArrayList<Entite>) entites.clone();
    }

    private static void checkPlayerVisible() {

        Optional<Rectangle> findFirst = StageManager.hideouts.stream().filter(e -> e.overlaps(player.getBoundingRectangle())).findFirst();
        if (findFirst.isPresent()) {
            if (player.isHidden()) return;
            player.setHide(true);
            MessageManager.talk(findFirst.get(), "hidden..", Color.WHITE);
        } else {
            player.setHide(false);
        }

    }

    public static ArrayList<Entite> getSeeEntite(PointLight sonar) {
        ArrayList<Entite> target = new ArrayList<>();
        entites.forEach((entite) -> {
            Vector2 center = entite.getCenter();
            if (sonar.contains(center.x, center.y)) target.add(entite);
        });
        return target;
    }

    public static void pebble(Pebble pebble) {
        entites.stream().filter(e -> e instanceof Ennemy).map(e -> ((Ennemy) e)).forEach(e -> {
            Vector2 center = pebble.getCenter();
            if (e.sonar.contains(center.x, center.y)) {
                e.talk("uh ?", Color.ORANGE);
                e.prevent = new Prevent(new Count(0, 2 * Time.SECONDE), () -> {
                    e.lookAt(pebble);
                });
            }
        });
    }

    private EntiteManager() {
    }

}
