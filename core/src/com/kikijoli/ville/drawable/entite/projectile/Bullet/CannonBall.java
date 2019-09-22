package com.kikijoli.ville.drawable.entite.projectile.Bullet;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.util.Constantes;

/**
 *
 * @author ajosse
 */
public class CannonBall extends Bullet {

    public CannonBall(int srcX, int srcY, Vector2 destination, Entite author) {
        super("sprite/cannonball.png", destination, 2000, author, srcX, srcY, Constantes.TILESIZE / 2, Constantes.TILESIZE / 2);
        this.speed = 10;
    }

    @Override
    public String getMouvementFilter() {
        return Constantes.CANNONBALL_MOVEMENT_OK;
    }

    @Override
    public boolean isTouching(Rectangle rectangle) {
        return this.overlaps(rectangle);
    }
}
