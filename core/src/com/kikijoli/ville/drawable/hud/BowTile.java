package com.kikijoli.ville.drawable.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.kikijoli.ville.drawable.entite.simple.Bow;
import com.kikijoli.ville.manager.EntiteManager;
import com.kikijoli.ville.util.Mode;

/**
 *
 * @author ajosse
 */
public class BowTile extends Tile {

	public BowTile(int srcX, int srcY) {
		super(srcX, srcY, new Bow(0, 0));
	}

	@Override
	public void action() {
		super.action();
		EntiteManager.player.mode = Mode.BOW;
	}

}
