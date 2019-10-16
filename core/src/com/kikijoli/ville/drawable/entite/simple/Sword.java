package com.kikijoli.ville.drawable.entite.simple;

import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.util.Constantes;

/**
 *
 * @author ajosse
 */
public class Sword extends Entite {

    public Sword(float srcX, float srcY) {
        super("sprite/sword.png", srcX, srcY, Constantes.TILESIZE * 2, Constantes.TILESIZE * 2);
    }

    public Sword(float srcX, float srcY, float width, float height) {
        super("sprite/sword.png", srcX, srcY, width, width);
    }

}
