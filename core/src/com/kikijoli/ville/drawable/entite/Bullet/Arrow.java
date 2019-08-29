package com.kikijoli.ville.drawable.entite.Bullet;

import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.util.Constantes;

/**
 *
 * @author ajosse
 */
public class Arrow extends Bullet {

	public Arrow(int srcX, int srcY, Vector2 destination, Entite author) {
		super("sprite/arrow.png", srcX, srcY, Constantes.TILESIZE / 4, Constantes.TILESIZE / 2, destination, author);
		this.speed = 5;
		this.scope = 1000;
	}

}
