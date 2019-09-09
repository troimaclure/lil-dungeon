package com.kikijoli.ville.manager;

import com.kikijoli.ville.drawable.entite.projectile.Bullet.Bullet;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.drawable.entite.projectile.Projectile;
import com.kikijoli.ville.maps.Tmap;
import com.kikijoli.ville.pathfind.GridManager;
import com.kikijoli.ville.util.Constantes;
import com.kikijoli.ville.util.MathUtils;
import java.util.ArrayList;

/**
 *
 * @author ajosse
 */
public class ProjectileManager {

    public static ArrayList<Bullet> projectiles = new ArrayList<>();
    public static ArrayList<Bullet> removes = new ArrayList<>();

    public static void tour() {
        for (Bullet proj : projectiles) {
            proj.move();
            proj.draw(Tmap.spriteBatch);
            testCollision(proj);
            if (proj.distance >= proj.scope) {
                removes.add(proj);
            }
        }
        projectiles.removeAll(removes);
    }

    private static void testCollision(Bullet bullet) {
        if (!GridManager.isClearZone(MathUtils.getCenter(bullet), Constantes.BULLET_MOVEMENT_OK)) removes.add(bullet);
        for (Entite entite : EntiteManager.entites) {
            if (!entite.equals(bullet.author) && entite.good != bullet.author.good) {
                if (bullet instanceof Bullet) {
                    if (entite.getBoundingRectangle().contains(MathUtils.getCenter(bullet))) {
                        removes.add(bullet);
                        EntiteManager.touch(entite);
                        break;
                    }
                }
            }
        }
    }

}
