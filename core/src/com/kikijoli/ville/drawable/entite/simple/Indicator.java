/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.drawable.entite.simple;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.manager.MessageManager;
import com.kikijoli.ville.util.Constantes;
import com.kikijoli.ville.interfaces.ISpriteDrawable;

/**
 *
 * @author troïmaclure
 */
public class Indicator implements ISpriteDrawable {

    String message;
    Entite entite;
    float x, y;
    public int count = 60 * 2;

    public Indicator(float x, float y, String message, Entite entite) {
        this.x = x;
        this.y = y;
        this.message = message;
        this.entite = entite;
    }

    @Override
    public void draw(SpriteBatch batch) {
        y += 0.50f;
        if (entite != null) {
            entite.draw(batch);
        }
        MessageManager.SHOWG.setColor(Color.BLACK);
        MessageManager.SHOWG.draw(batch, message, x + 2 + Constantes.TILESIZE / 2, y + Constantes.TILESIZE / 2);
        MessageManager.SHOWG.setColor(Color.GREEN);
        MessageManager.SHOWG.draw(batch, message, x + Constantes.TILESIZE / 2, y + Constantes.TILESIZE / 2);
        count -= 1;

    }
}
