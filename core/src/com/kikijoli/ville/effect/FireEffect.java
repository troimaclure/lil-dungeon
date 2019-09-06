package com.kikijoli.ville.effect;

import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.business.AbstractBusiness;
import com.kikijoli.ville.business.InFireBuisiness;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.drawable.entite.projectile.Spell.Spell;
import com.kikijoli.ville.manager.ParticleManager;
import com.kikijoli.ville.util.MathUtils;

/**
 *
 * @author ajosse
 */
public class FireEffect extends AbstractEffect {

	private int count = 60 * 3;
	AbstractBusiness old;

	public FireEffect(float x, float y, Spell spell) {
		super(ParticleManager.addParticle("particle/fireeffect.p", x, y, 0.5f), spell);

	}

	@Override
	public void tour(Entite entite) {
//		if (entite.buisiness instanceof InFireBuisiness) {
//			old = entite.buisiness;
//			entite.buisiness = new InFireBuisiness(spell, entite);
//		}
		Vector2 center = MathUtils.getCenter(entite.getBoundingRectangle());
		this.effect.setPosition(center.x, center.y);
		this.count--;
		if (this.count <= 0) {
//			entite.buisiness = old;
			end = true;
		}
	}

}
