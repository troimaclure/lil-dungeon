/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.drawable.entite.decor;

import com.kikijoli.ville.util.Constantes;

/**
 *
 * @author troïmaclure
 */
public class Mountains extends Decor {

    public Mountains(int srcX, int srcY) {
        super("sprite/mountains.png", srcX, srcY, Constantes.TILESIZE * 4, Constantes.TILESIZE * 4);
    }

}
