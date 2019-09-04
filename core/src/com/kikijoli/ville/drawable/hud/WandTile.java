package com.kikijoli.ville.drawable.hud;

import com.kikijoli.ville.drawable.entite.simple.Wand;
import com.kikijoli.ville.manager.EntiteManager;
import com.kikijoli.ville.util.Mode;

/**
 *
 * @author ajosse
 */
public class WandTile extends Tile {

	public WandTile(int srcX, int srcY) {
		super(srcX, srcY, new Wand(0, 0));
	}

	@Override
	public void action() {
		super.action();
		EntiteManager.player.mode = Mode.WAND;
	}

}
