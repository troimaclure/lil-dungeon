package com.kikijoli.ville.drawable.entite.simple;

import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.util.Constantes;

/**
 *
 * @author ajosse
 */
public class Pebble extends Entite {

    public Pebble(float srcX, float srcY) {
        this(srcX, srcY, Constantes.TILESIZE * 2, Constantes.TILESIZE * 2);
    }

    public Pebble(float srcX, float srcY, float width, float height) {
        super("sprite/pebble.png", srcX, srcY, width, height);
        this.speed = 10;
    }

}
