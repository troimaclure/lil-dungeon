/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.drawable.entite.npc;

import box2dLight.PointLight;
import com.badlogic.gdx.graphics.Color;
import com.kikijoli.ville.business.PlayerBusiness;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.business.AbstractBusiness;
import com.kikijoli.ville.component.BowComponent;
import com.kikijoli.ville.component.IComponent;
import com.kikijoli.ville.component.PebbleComponent;
import com.kikijoli.ville.component.SwordComponent;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.interfaces.IBusiness;
import com.kikijoli.ville.interfaces.IShapeDrawable;
import com.kikijoli.ville.interfaces.Ipv;
import static com.kikijoli.ville.manager.EntiteManager.player;
import com.kikijoli.ville.maps.Tmap;
import com.kikijoli.ville.util.Constantes;
import java.util.Arrays;

/**
 *
 * @author troïmaclure
 */
public final class Player extends Entite implements IBusiness, IShapeDrawable, Ipv {

    private static final String SPRITESIMPLEPNG = "sprite/simple.png";
    public float dashTotal = 2;
    public float dashCooldown = 3 * 60;
    public float dashCount = dashTotal * dashCooldown;
    public PointLight vision;
    private boolean hide;
    public boolean invincible = false;
    public int maxPv = 3;
    public int pv = maxPv;

    public Player(float srcX, float srcY) {
        super(SPRITESIMPLEPNG, srcX, srcY);
        this.buisiness = this.getDefault();
        good = true;
        this.speed = 7;
//        this.shield = new PlayerShield((int) this.getX(), (int) this.getY());
        this.components.addAll(Arrays.asList(new IComponent[]{new BowComponent(this, (t) -> {
            return new Vector2(Tmap.worldCoordinates.x, Tmap.worldCoordinates.y);
        }), new SwordComponent(this), new PebbleComponent(this)}));
        this.currentComponent = this.components.get(0);
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.setColor(Color.WHITE);
        if (this.invincible) batch.setColor(Color.RED);
        if (this.hide)
            batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, 0.5f);
        super.draw(batch);
    }

    @Override
    public void draw(ShapeRenderer batch) {
        batch.setColor(Color.GRAY);
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
        return new PlayerBusiness();
    }

    private float calculateDashWidth() {

        dashCount += dashCount > dashCooldown * dashTotal ? 0 : 1;
        float pourcent = (dashCount / (dashCooldown * dashTotal)) * 100;
        return getWidth() * 2 * (pourcent / 100);
    }

    public boolean canDash() {
        return dashCount >= (dashCooldown * dashTotal) / 2;
    }

    public void dash() {
        dashCount -= (dashCooldown * dashTotal) / 2;
    }

    public void setHide(boolean hide) {
        this.hide = hide;
    }

    public boolean isHidden() {
        return this.hide;
    }

    public void hurted() {

        player.talk("Ouch", Color.RED);

    }

    @Override
    public int getPv() {
        return this.pv;
    }

    @Override
    public void setPv(int pv) {
        this.pv = pv;
    }

}
