package com.kikijoli.ville.drawable.entite.simple;

import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.util.Constantes;

/**
 *
 * @author ajosse
 */
public class Wand extends Entite {

    public Wand(int srcX, int srcY) {
        super("sprite/wand.png", srcX, srcY, Constantes.TILESIZE * 2, Constantes.TILESIZE * 2);
    }

    public Wand(int srcX, int srcY, float width, float height) {
        super("sprite/wand.png", srcX, srcY, width, height);
    }

}
