package com.kikijoli.ville.business;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.abstracts.AbstractAction;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.drawable.entite.projectile.Spell.Spell;
import com.kikijoli.ville.effect.FireEffect;
import com.kikijoli.ville.pathfind.GridManager;
import com.kikijoli.ville.util.Constantes;
import com.kikijoli.ville.util.MathUtils;
import com.sun.corba.se.impl.orbutil.closure.Constant;

/**
 *
 * @author ajosse
 */
public class InFireBuisiness extends AbstractBusiness {

	public Rectangle fireSource;
	Entite entite;

	public InFireBuisiness(Rectangle fireSource, Entite entite) {
		this.fireSource = fireSource;
		this.entite = entite;
	}

	@Override
	public AbstractAction getDefault() {
		return new FireFear();
	}

	private class FireFear extends AbstractAction {

		Vector2 vel;

		@Override
		public void act() {
			if (vel == null) {
				vel = MathUtils.destination(MathUtils.getCenter(fireSource), new Vector2(entite.getX(), entite.getY()));
			}
			Vector2 goal = new Vector2(entite.getX() - vel.x * entite.speed + 1, entite.getY() - vel.y * entite.speed + 1);
			if (!GridManager.isClearZone(goal, Constantes.NPC_MOVEMENT_OK)) return;
			entite.setX(entite.getX() - vel.x * entite.speed + 1);
			entite.setX(entite.getY() - vel.y * entite.speed + 1);
		}
	}

}
