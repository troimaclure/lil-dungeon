package com.kikijoli.ville.drawable.entite.simple;

import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.util.Constantes;

/**
 *
 * @author ajosse
 */
public class Bow extends Entite {

    public Bow(float srcX, float srcY) {
        super("sprite/bow.png", srcX, srcY, Constantes.TILESIZE * 2, Constantes.TILESIZE * 2);
    }

    public Bow(float srcX, float srcY, float width, float height) {
        super("sprite/bow.png", srcX, srcY, width, height);
    }

}
