package com.kikijoli.ville.manager;

import com.kikijoli.ville.drawable.entite.projectile.Bullet.Bullet;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.interfaces.INotTouchable;
import com.kikijoli.ville.maps.Tmap;
import com.kikijoli.ville.util.MathUtils;
import java.util.ArrayList;

/**
 *
 * @author ajosse
 */
public class ProjectileManager {

    public static ArrayList<Bullet> projectiles = new ArrayList<>();
    public static ArrayList<Bullet> removes = new ArrayList<>();

    public static void draw() {
        projectiles.forEach((proj) -> {
            proj.draw(Tmap.spriteBatch);
        });
    }

    public static void tour() {
        for (Bullet proj : projectiles) {
            proj.move();
            testCollision(proj);
            if (proj.distance >= proj.scope) {
                removes.add(proj);
            }
        }
        projectiles.removeAll(removes);
    }

    private static void testCollision(Bullet bullet) {
        if (!StageManager.isClearZone(MathUtils.getCenter(bullet), bullet.getMouvementFilter()))
            removes.add(bullet);
        for (Entite entite : EntiteManager.entites) {
            if (!(entite instanceof INotTouchable) && !entite.equals(bullet.author) && entite.good != bullet.author.good && entite.isTouchable) {
                if (bullet instanceof Bullet) {
                    if (bullet.isTouching(entite.getBoundingRectangle())) {
                        removes.add(bullet);
                        EntiteManager.touch(entite);
                        break;
                    }
                }
            }
        }
    }

}
