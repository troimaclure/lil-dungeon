package com.kikijoli.ville.drawable.hud;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.manager.MessageManager;
import com.kikijoli.ville.util.Constantes;
import com.kikijoli.ville.util.MathUtils;
import java.util.function.Supplier;

/**
 *
 * @author ajosse
 */
public abstract class TileWithAmmo extends Tile {

    Supplier<Integer> supplier;

    public TileWithAmmo(Entite entite, Supplier supplier) {
        super(entite);
        this.supplier = supplier;
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
        int count = supplier.get();
        MessageManager.SHOWG.setColor(count > 0 ? Color.WHITE : Color.RED);
        Vector2 centerString = MathUtils.centerString(count + "", MessageManager.SHOWG, this);
        MessageManager.SHOWG.draw(batch, count + "", centerString.x + Constantes.TILESIZE / 2, y - 5);
    }

    @Override
    public void draw(ShapeRenderer batch) {
        super.draw(batch);
        batch.setColor(Color.BLACK);
        batch.rect(x, y - Constantes.TILESIZE / 2, width, Constantes.TILESIZE / 2);
    }

    @Override
    public void drawLines(ShapeRenderer batch) {
        super.drawLines(batch);
        batch.setColor(Color.WHITE);
        batch.rect(x, y - Constantes.TILESIZE / 2, width, Constantes.TILESIZE / 2);
    }

}
