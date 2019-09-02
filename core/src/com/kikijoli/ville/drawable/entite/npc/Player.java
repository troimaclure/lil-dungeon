/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.drawable.entite.npc;

import com.kikijoli.ville.business.PlayerBuisiness;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.util.Constantes;
import com.kikijoli.ville.util.Mode;

/**
 *
 * @author troïmaclure
 */
public class Player extends Entite {

	public int mode = Mode.SWORD;
	private static final String SPRITESIMPLEPNG = "sprite/simple.png";

	public Player(int srcX, int srcY) {
		super(SPRITESIMPLEPNG, srcX, srcY, Constantes.TILESIZE / 4, Constantes.TILESIZE / 2);
		this.buisiness = new PlayerBuisiness();
		good = true;
	}

	@Override
	public void draw(SpriteBatch batch) {
		super.draw(batch);
	}

	@Override
	public float getAnchorSize() {
		return Constantes.TILESIZE / 2 + getWidth();
	}

}
