package com.kikijoli.ville.manager;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.graphics.ParticleEmitterBox2D;
import com.kikijoli.ville.drawable.entite.projectile.Bullet.Bullet;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.drawable.entite.projectile.Projectile;
import com.kikijoli.ville.drawable.entite.projectile.Spell.Spell;
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

	public static ArrayList<Projectile> projectiles = new ArrayList<>();
	public static ArrayList<Projectile> removes = new ArrayList<>();

	public static void tour() {

		for (Projectile proj : projectiles) {
			proj.move();
			handleBullet(proj);
			testCollision(proj);
			if (proj.distance >= proj.scope) {
				removes.add(proj);
			}
		}
		for (Projectile remove : removes) {
			if (remove instanceof Spell) {
				ParticleManager.particleEffects.remove(((Spell) remove).effect);
			}
		}
		projectiles.removeAll(removes);

	}

	private static void handleBullet(Projectile proj) {
		if (proj instanceof Bullet) {
			Bullet bullet = (Bullet) proj;
			bullet.draw(Tmap.spriteBatch);
		}
	}

	private static void testCollision(Projectile bullet) {
		if (!GridManager.isClearZone(MathUtils.getCenter(bullet), Constantes.BULLET_MOVEMENT_OK)) removes.add(bullet);
		for (Entite entite : EntiteManager.entites) {
			if (!entite.equals(bullet.author) && entite.good != bullet.author.good) {
				if (bullet instanceof Bullet) {
					if (entite.getBoundingRectangle().contains(MathUtils.getCenter(bullet))) {
						removes.add(bullet);
						EntiteManager.touch(entite);
						break;
					}
				} else if (bullet instanceof Spell && entite.getBoundingRectangle().overlaps(((Spell) bullet).anchors)) {
					EntiteManager.spellEffect(entite, ((Spell) bullet));
				}

			}

		}
	}

}
