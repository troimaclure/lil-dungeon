package com.kikijoli.ville.manager;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.drawable.entite.Bullet.Bullet;
import com.kikijoli.ville.maps.Tmap;
import java.util.ArrayList;

/**
 *
 * @author ajosse
 */
public class BulletManager {

    public static ArrayList<Bullet> bullets = new ArrayList<>();

    public static void tour() {
        ArrayList<Bullet> removes = new ArrayList<>();
        for (Bullet bullet : bullets) {
            Vector2 vel = new Vector2().set(bullet.destination).sub(new Vector2(bullet.getX(), bullet.getY())).nor();
            bullet.setX(bullet.getX() + vel.x * bullet.speed);
            bullet.setY(bullet.getY() + vel.y * bullet.speed);
            bullet.distance += bullet.speed;
            bullet.draw(Tmap.spriteBatch);
            if (bullet.distance > bullet.scope) {
                removes.add(bullet);
            }
        }
        bullets.removeAll(removes);

    }

}
