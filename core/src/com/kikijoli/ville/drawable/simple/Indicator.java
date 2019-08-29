/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.drawable.simple;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kikijoli.ville.manager.MessageManager;
import com.kikijoli.ville.util.Constantes;
import com.kikijoli.ville.interfaces.ISpriteDrawable;

/**
 *
 * @author troïmaclure
 */
public class Indicator implements ISpriteDrawable {

    String message;
    Texture texture;
    float x, y;

    public Indicator(float x, float y, String message, Texture texture) {
        this.x = x;
        this.y = y;
        this.message = message;
        this.texture = texture;
    }

    @Override
    public void draw(SpriteBatch batch) {
        y += 0.50f;
        if (texture != null) {
            batch.draw(texture, x, y, Constantes.TILESIZE / 2, Constantes.TILESIZE / 2);
        }
        MessageManager.segoe.draw(batch, message, x + Constantes.TILESIZE / 2, y + Constantes.TILESIZE / 2);
    }
}
