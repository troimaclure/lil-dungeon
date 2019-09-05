package com.kikijoli.ville.effect;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.kikijoli.ville.drawable.entite.Entite;

/**
 *
 * @author ajosse
 */
public abstract class AbstractEffect {

	public ParticleEffect effect;

	public AbstractEffect(ParticleEffect effect) {
		this.effect = effect;
	}

	public abstract void tour(Entite entite);
}
