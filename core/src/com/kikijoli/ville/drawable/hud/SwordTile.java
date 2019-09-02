package com.kikijoli.ville.drawable.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.kikijoli.ville.drawable.entite.simple.Sword;
import com.kikijoli.ville.interfaces.IShapeDrawable;
import com.kikijoli.ville.interfaces.ISpriteDrawable;
import com.kikijoli.ville.manager.EntiteManager;
import com.kikijoli.ville.util.Mode;

/**
 *
 * @author ajosse
 */
public class SwordTile extends Tile implements ISpriteDrawable, IShapeDrawable {

	public SwordTile(int srcX, int srcY) {
		super(srcX, srcY, new Sword(0, 0));
	}

	@Override
	public void action() {
		super.action();
		EntiteManager.player.mode = Mode.SWORD;
	}

}
