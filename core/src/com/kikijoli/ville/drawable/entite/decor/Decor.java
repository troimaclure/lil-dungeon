/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.drawable.entite.decor;

import com.kikijoli.ville.drawable.entite.Entite;

/**
 *
 * @author troïmaclure
 */
public abstract class Decor extends Entite {

    public Decor(String path, float srcX, float srcY, int srcWidth, int srcHeight) {
        super(path, srcX, srcY, srcWidth, srcHeight);
    }

}
