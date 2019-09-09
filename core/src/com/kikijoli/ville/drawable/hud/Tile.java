package com.kikijoli.ville.drawable.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.interfaces.IShapeDrawable;
import com.kikijoli.ville.interfaces.ISpriteDrawable;
import com.kikijoli.ville.manager.HudManager;
import com.kikijoli.ville.util.Constantes;

/**
 *
 * @author ajosse
 */
public abstract class Tile extends Rectangle implements ISpriteDrawable, IShapeDrawable {

	public Entite draw;
	public boolean selected;

	public Tile(int srcX, int srcY, Entite entite) {
		super(srcX, srcY, Constantes.TILESIZE, Constantes.TILESIZE);
		this.draw = entite;
	}

	public void action() {
		HudManager.tiles.forEach((tile) -> tile.selected = false);
		this.selected = true;
	}

	@Override
	public void draw(SpriteBatch batch) {
		this.draw.setX(x);
		this.draw.setY(y - Constantes.TILESIZE / 4);
		this.draw.draw(batch);
	}

	@Override
	public void draw(ShapeRenderer batch) {
		batch.rect(x, y, width, height);
	}
}
