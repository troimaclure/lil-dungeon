package com.kikijoli.ville.drawable.entite.projectile.Spell;

import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.drawable.entite.Entite;

/**
 *
 * @author ajosse
 */
public class PoisonSpell extends Spell {

	public PoisonSpell(Vector2 destination, Entite author, float x, float y) {
		super("particle/poison.p", destination, 1000, author, x, y);
	}

}
