package com.kikijoli.ville.drawable.entite.simple;

import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.util.Constantes;

/**
 *
 * @author ajosse
 */
public class Wand extends Entite {

    public Wand(int srcX, int srcY) {
        super("sprite/wand.png", srcX, srcY, Constantes.TILESIZE, Constantes.TILESIZE);
    }

}
