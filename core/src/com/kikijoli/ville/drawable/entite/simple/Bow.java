package com.kikijoli.ville.drawable.entite.simple;

import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.util.Constantes;

/**
 *
 * @author ajosse
 */
public class Bow extends Entite {

    public Bow(int srcX, int srcY) {
        super("sprite/bow.png", srcX, srcY, Constantes.TILESIZE, Constantes.TILESIZE);
    }

}
