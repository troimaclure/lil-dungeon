package com.kikijoli.ville.drawable.entite.projectile.Spell;

import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.effect.AbstractEffect;
import com.kikijoli.ville.effect.FireEffect;

/**
 *
 * @author ajosse
 */
public class FireSpell extends Spell {

	public FireSpell(Vector2 destination, Entite author) {
		super("particle/fire.p", destination, 150, author, destination.x, destination.y);
		this.speed = 0;
	}

	@Override
	public AbstractEffect getEffect(float x, float y) {
		return new FireEffect(x, y, this);
	}

	@Override
	public Class getEffectType() {
		return FireEffect.class;
	}

}
