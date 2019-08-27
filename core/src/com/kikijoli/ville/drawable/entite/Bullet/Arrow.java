package com.kikijoli.ville.drawable.entite.Bullet;

import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.util.Constantes;

/**
 *
 * @author ajosse
 */
public class Arrow extends Bullet {

    public Arrow(int srcX, int srcY, Vector2 destination) {
        super("sprite/arrow.png", srcX, srcY, Constantes.TILESIZE / 4, Constantes.TILESIZE / 2, destination);
        this.speed = 5;
        this.scope = 600;
    }

}
