package com.kikijoli.ville.drawable.entite.projectile.Bullet;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.util.Constantes;

/**
 *
 * @author ajosse
 */
public class Arrow extends Bullet {

    public Arrow(int srcX, int srcY, Vector2 destination, Entite author) {
        super("sprite/arrow.png", destination, 1000, author, srcX, srcY, Constantes.TILESIZE / 2, Constantes.TILESIZE);
        this.speed = 7;
    }

    @Override
    public String getMouvementFilter() {
        return Constantes.BULLET_MOVEMENT_OK;
    }

    @Override
    public boolean isTouching(Rectangle rectangle) {
        return rectangle.overlaps(this);
    }

}
