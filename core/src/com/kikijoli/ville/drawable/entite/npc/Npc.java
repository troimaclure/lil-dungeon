package com.kikijoli.ville.drawable.entite.npc;

import box2dLight.ConeLight;
import box2dLight.PointLight;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Filter;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.maps.Tmap;
import com.kikijoli.ville.util.Constantes;

/**
 *
 * @author ajosse
 */
public abstract class Npc extends Entite {

    public Body body;
    public ConeLight vision;
    public PointLight sonar;

    public Npc(String path, float srcX, float srcY, float srcWidth, float srcHeight) {
        super(path, srcX, srcY, srcWidth, srcHeight);
        initVision(srcX, srcY);
    }

    public Npc(String path, float srcX, float srcY) {
        this(path, srcX, srcY, Constantes.TILESIZE, Constantes.TILESIZE);
    }

    public void initVision(float srcX, float srcY) {
        this.vision = new ConeLight(Tmap.getRay(), 100, Color.BLACK, 500, srcX, srcY, 0, 75);
        this.vision.attachToBody(getBody(), 0, 0, -90f);
        this.vision.setSoftnessLength(64);
        this.vision.setIgnoreAttachedBody(true);
        Filter filterA = new Filter();
  
        this.vision.setContactFilter(filterA);
        this.sonar = new PointLight(Tmap.getRay(), 20, Color.CLEAR, 500, srcX, srcY);
        this.sonar.attachToBody(getBody(), 0, 0);
        this.sonar.setIgnoreAttachedBody(true);
    }

    @Override
    protected void calculateAnchors() {
        super.calculateAnchors();
        this.getBody().setTransform(this.getX() + 32, this.getY() + 32, getRotation() * MathUtils.degreesToRadians);
    }

    public Body getBody() {
        if (this.body == null) {
            this.body = Tmap.addBody(this);
        }
        return body;
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch); //To change body of generated methods, choose Tools | Templates.
        this.vision.setPosition(this.getX(), this.getY());
    }

    @Override
    public void dead() {
        super.dead(); //To change body of generated methods, choose Tools | Templates.
        this.vision.setActive(false);
    }

}
