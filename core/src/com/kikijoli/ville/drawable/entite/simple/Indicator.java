/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.drawable.entite.simple;

import com.badlogic.gdx.graphics.Color;
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
    float x, y;
    public int count = 60 * 2;
    Color color;

    public Indicator(float x, float y, String message) {
        this.x = x;
        this.y = y;
        this.message = message;
        this.color = Color.GREEN;
    }

    public Indicator(float x, float y, String message, Color color) {
        this.x = x;
        this.y = y;
        this.message = message;
        this.color = color;
    }

    public Indicator(float x, float y, String message, Color color, int count) {
        this.x = x;
        this.y = y;
        this.message = message;
        this.color = color;
        this.count = count;
    }

    @Override
    public void draw(SpriteBatch batch) {
        y += 0.1f;
        MessageManager.SHOWG.setColor(Color.BLACK);
        MessageManager.SHOWG.draw(batch, message, x + Constantes.TILESIZE / 2, y + Constantes.TILESIZE / 2);
        MessageManager.SHOWG.setColor(this.color);
        MessageManager.SHOWG.draw(batch, message, x + Constantes.TILESIZE / 2, y + Constantes.TILESIZE / 2);
        count -= 1;
        
    }
}
