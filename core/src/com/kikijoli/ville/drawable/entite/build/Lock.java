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
public class Lock extends Build {

    private static final String SPRITELOCKPNG = "sprite/lock.png";

    public Lock(int srcX, int srcY) {
        super(SPRITELOCKPNG, srcX, srcY, Constantes.TILESIZE, Constantes.TILESIZE);
    }

}
