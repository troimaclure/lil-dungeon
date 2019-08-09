/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.drawable.entite.build;

import com.kikijoli.ville.util.Constantes;

/**
 *
 * @author troïmaclure
 */
public class Key extends Build {

    private static final String SPRITEKEYPNG = "sprite/key.png";

    public Key(int srcX, int srcY) {
        super(SPRITEKEYPNG, srcX, srcY, Constantes.TILESIZE / 2, Constantes.TILESIZE / 2);
    }

}
