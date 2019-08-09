/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.drawable.entite.build;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.util.Constantes;

/**
 *
 * @author troïmaclure
 */
public abstract class Build extends Entite {

    public Circle anchor;
    public Rectangle anchorInfluence;

    public Build(String path, int srcX, int srcY, int srcWidth, int srcHeight) {
        super(path, srcX, srcY, srcWidth, srcHeight);
        Rectangle r = this.getBoundingRectangle();
        Vector2 center = new Vector2();
        r.getCenter(center);
        this.anchorInfluence = new Rectangle(r.getX() - Constantes.TILESIZE, r.getY() - Constantes.TILESIZE, r.getWidth() + Constantes.TILESIZE * 2, r.getHeight() + Constantes.TILESIZE * 2);
        this.anchor = new Circle(center, Constantes.TILESIZE * (getWidth() / Constantes.TILESIZE / 2) + Constantes.TILESIZE);

    }

}
