/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.drawable.entite.simple;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.util.Constantes;

/**
 *
 * @author Arthur
 */
public class PlayerShield extends Entite {

    int count = 0;
    int total = 10;
    boolean down;

    public PlayerShield(int srcX, int srcY) {
        super("sprite/player-shield.png", srcX, srcY, Constantes.TILESIZE, Constantes.TILESIZE);
    }

    public PlayerShield(float srcX, float srcY, float width, float height) {
        super("sprite/player-shield.png", srcX, srcY, width, height);
    }

    public void step(float x, float y, float width, float height) {
        count += down ? (-1) : 1;
        if (down && count == 0) down = false;
        if (!down && count == total) down = true;
        this.setSize(Constantes.TILESIZE + (count), Constantes.TILESIZE + (count));
        this.setX(x - this.getWidth() / 2 + width / 2);
        this.setY(y - this.getHeight() / 2 + height / 2);
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(this.getTexture(), this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

}
