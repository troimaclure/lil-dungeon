/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.drawable.entite.npc;

import com.badlogic.gdx.graphics.Color;
import com.kikijoli.ville.business.PlayerBuisiness;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.kikijoli.ville.business.AbstractBusiness;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.interfaces.IBusiness;
import com.kikijoli.ville.interfaces.IShapeDrawable;
import com.kikijoli.ville.manager.ColorManager;
import com.kikijoli.ville.util.Constantes;
import com.kikijoli.ville.util.Mode;

/**
 *
 * @author troïmaclure
 */
public final class Player extends Entite implements IBusiness, IShapeDrawable {

    private static final String SPRITESIMPLEPNG = "sprite/simple.png";
    public int mode = Mode.BOW;
    public float dashTotal = 2;
    public float dashCooldown = 3 * 60;
    public float dashCount = dashTotal * dashCooldown;

    public Player(int srcX, int srcY) {
        super(SPRITESIMPLEPNG, srcX, srcY, Constantes.TILESIZE / 4, Constantes.TILESIZE / 2);
        this.buisiness = this.getDefault();
        good = true;
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }

    @Override
    public void draw(ShapeRenderer batch) {
        batch.setColor(ColorManager.getTextureColor());
        batch.rect(getX() - getWidth() / 2, getY() + getHeight() + 5, getWidth() * 2, 5);
        batch.setColor(Color.RED);
        batch.rect(getX() - getWidth() / 2, getY() + getHeight() + 5, calculateDashWidth(), 5);
    }

    @Override
    public float getAnchorSize() {
        return Constantes.TILESIZE / 2 + getWidth();
    }

    @Override
    public AbstractBusiness getDefault() {
        return new PlayerBuisiness();
    }

    private float calculateDashWidth() {
        dashCount += dashCount > dashCooldown * dashTotal ? 0 : 1;
        float pourcent = (dashCount / (dashCooldown * dashTotal)) * 100;
        System.out.println(pourcent);
        return getWidth() * 2 * (pourcent / 100);
    }

    public boolean canDash() {
        return dashCount >= (dashCooldown * dashTotal) / 2;
    }

    public void dash() {
        dashCount -= (dashCooldown * dashTotal) / 2;
    }

}
