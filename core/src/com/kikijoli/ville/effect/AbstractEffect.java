package com.kikijoli.ville.effect;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.drawable.entite.projectile.Spell.Spell;

/**
 *
 * @author ajosse
 */
public abstract class AbstractEffect {

	public ParticleEffect effect;
	Spell spell;
	public boolean end = false;

	public AbstractEffect(ParticleEffect effect, Spell spell) {
		this.effect = effect;
	}

	public abstract void tour(Entite entite);
}
