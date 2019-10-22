package com.kikijoli.ville.drawable.entite.simple;

import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.util.Constantes;

/**
 *
 * @author ajosse
 */
public class TurretBow extends Entite {

    public TurretBow(float srcX, float srcY) {
        super("sprite/turretbow.png", srcX, srcY, Constantes.TILESIZE * 2, Constantes.TILESIZE * 2);
    }

}
