package com.kikijoli.ville.drawable.entite.projectile.Bullet;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.util.Constantes;
import com.kikijoli.ville.util.Move;
import java.util.List;

/**
 *
 * @author ajosse
 */
public class Arrow extends Bullet {

    public Arrow(int srcX, int srcY, Vector2 destination, Entite author) {
        super("sprite/arrow.png", destination, 1500, author, srcX, srcY, Constantes.TILESIZE / 2, Constantes.TILESIZE);
        this.speed = 14;
    }

    @Override
    public boolean isTouching(Rectangle rectangle) {
        return rectangle.overlaps(this);
    }

    @Override
    public List<Rectangle> getMouvementFilter() {
        return Move.BULLET_MOVE_FILTER;
    }
}
