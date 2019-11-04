package com.kikijoli.ville.drawable.entite.npc;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.maps.Tmap;
import com.kikijoli.ville.util.Constantes;

/**
 *
 * @author ajosse
 */
public abstract class Npc extends Entite {

    public Body body;

    public Npc(String path, float srcX, float srcY, float srcWidth, float srcHeight) {
        super(path, srcX, srcY, srcWidth, srcHeight);
    }

    public Npc(String path, float srcX, float srcY) {
        super(path, srcX, srcY, Constantes.TILESIZE, Constantes.TILESIZE);
    }

    @Override
    protected void calculateAnchors() {
        super.calculateAnchors();
        this.getBody().setTransform(this.getX() + 32, this.getY() + 32, getRotation() * MathUtils.degreesToRadians);
    }

    public void setVisionDirection(float rotation) {
        this.getBody().setTransform(this.getX() + 32, this.getY() + 32, rotation * MathUtils.degreesToRadians);
    }

    public Body getBody() {
        if (this.body == null) {
            this.body = Tmap.addBody(this);
        }
        return body;
    }

}
