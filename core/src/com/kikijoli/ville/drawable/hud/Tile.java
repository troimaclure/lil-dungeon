package com.kikijoli.ville.drawable.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.interfaces.IShapeDrawable;
import com.kikijoli.ville.interfaces.ISpriteDrawable;
import com.kikijoli.ville.manager.ColorManager;
import com.kikijoli.ville.manager.HudManager;
import com.kikijoli.ville.manager.MessageManager;
import com.kikijoli.ville.util.Constantes;
import com.kikijoli.ville.util.MathUtils;

/**
 *
 * @author ajosse
 */
public abstract class Tile extends Rectangle implements ISpriteDrawable, IShapeDrawable {

    public Entite draw;
    public boolean selected;
    public boolean disabled;
    public int count = 0;

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
        batch.setColor(Color.WHITE);
        if (!disabled) this.draw.draw(batch);
        else {
            MessageManager.SHOWG.setColor(Color.WHITE);
            Vector2 center = MathUtils.getCenter(this);
            MessageManager.SHOWG.draw(batch, Integer.toString(MathUtils.transformIpsToSec(count)), center.x - 5, center.y + Constantes.TILESIZE / 4);
        }
    }

    @Override
    public void draw(ShapeRenderer batch) {
        batch.setColor(disabled ? Color.GRAY : selected ? Color.BLUE : ColorManager.getBackgroundColor());
        batch.rect(x, y, width, height);
    }

    public void drawLines(ShapeRenderer batch) {
        batch.setColor(Color.WHITE);
        batch.rect(x, y, width, height);
    }

}
