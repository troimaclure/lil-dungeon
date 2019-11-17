package com.kikijoli.ville.drawable.entite.simple;

import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.util.Constantes;

/**
 *
 * @author ajosse
 */
public class Vanish extends Entite {

    public Vanish(float srcX, float srcY) {
        this(srcX, srcY, Constantes.TILESIZE, Constantes.TILESIZE);
    }

    public Vanish(float srcX, float srcY, float width, float height) {
        super("sprite/vanish.png", srcX, srcY, width, height);
        this.speed = 10;
    }

}
