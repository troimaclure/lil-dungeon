/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.drawable.entite.build;

import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kikijoli.ville.manager.ColorManager;
import com.kikijoli.ville.manager.MessageManager;
import com.kikijoli.ville.util.Constantes;

/**
 *
 * @author troïmaclure
 */
public class Door extends Build {

    private static final String SPRITELOCKPNG = "sprite/lock.png";

    public String data;

    public Door(int srcX, int srcY, String data) {
        super(SPRITELOCKPNG, srcX, srcY, Constantes.TILESIZE, Constantes.TILESIZE);
        this.data = data;
    }

    @Override
    public void draw(SpriteBatch batch) {
        MessageManager.segoe.getData().setScale(1);
        final GlyphLayout layout = new GlyphLayout(MessageManager.segoe, data);
        MessageManager.segoe.setColor(ColorManager.getTextureColor());

        final float fontX = getX() + (getWidth() - layout.width) / 2;
        final float fontY = getY() + (getHeight() + layout.height) / 2;
        MessageManager.segoe.draw(batch, data, fontX, fontY);
    }



}
