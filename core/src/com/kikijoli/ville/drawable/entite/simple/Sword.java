package com.kikijoli.ville.drawable.entite.simple;

import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.util.Constantes;

/**
 *
 * @author ajosse
 */
public class Sword extends Entite {

	public Sword(int srcX, int srcY) {
		super("sprite/sword.png", srcX, srcY, Constantes.TILESIZE, Constantes.TILESIZE);
	}

}
