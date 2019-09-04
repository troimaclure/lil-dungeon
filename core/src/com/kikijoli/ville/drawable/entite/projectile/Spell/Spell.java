package com.kikijoli.ville.drawable.entite.projectile.Spell;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.drawable.entite.projectile.Projectile;
import com.kikijoli.ville.manager.ParticleManager;

/**
 *
 * @author ajosse
 */
public abstract class Spell extends Projectile {

	ParticleEffect effect;

	public Spell(String path, Vector2 destination, int scope, Entite author, float x, float y) {
		super(destination, scope, author, x, y, 0, 0);
		effect = ParticleManager.addParticle(path, x, y, 1);
		this.speed = 2;
	}

	@Override
	public void move() {
		super.move();
		effect.setPosition(this.getX(), this.getY());
	}
}
