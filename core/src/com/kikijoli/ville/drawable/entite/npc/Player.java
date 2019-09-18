/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.drawable.entite.npc;

import com.badlogic.gdx.graphics.Color;
import com.kikijoli.ville.business.PlayerBuisiness;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kikijoli.ville.business.AbstractBusiness;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.interfaces.IBusiness;
import com.kikijoli.ville.util.Constantes;
import com.kikijoli.ville.util.Mode;

/**
 *
 * @author troïmaclure
 */
public final class Player extends Entite implements IBusiness {

    public int mode = Mode.SWORD;
    private static final String SPRITESIMPLEPNG = "sprite/simple.png";

    public Player(int srcX, int srcY) {
        super(SPRITESIMPLEPNG, srcX, srcY, Constantes.TILESIZE / 4, Constantes.TILESIZE / 2);
        this.buisiness = this.getDefault();
        good = true;
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (((PlayerBuisiness) this.buisiness).actions.containsKey(PlayerBuisiness.DASH)) {
            batch.setColor(new Color(batch.getColor().r, batch.getColor().g, batch.getColor().b, 0.5f));
        }
        super.draw(batch);
        batch.setColor(new Color(batch.getColor().r, batch.getColor().g, batch.getColor().b, 1f));

    }

    @Override
    public float getAnchorSize() {
        return Constantes.TILESIZE / 2 + getWidth();
    }

    @Override
    public AbstractBusiness getDefault() {
        return new PlayerBuisiness();
    }

}
