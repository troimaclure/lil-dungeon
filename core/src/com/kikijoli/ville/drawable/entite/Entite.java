/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.drawable.entite;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.business.AbstractBusiness;
import com.kikijoli.ville.interfaces.IDrawable;
import com.kikijoli.ville.shader.AbstractShader;
import com.kikijoli.ville.util.Constantes;
import com.kikijoli.ville.util.TextureUtil;

/**
 *
 * @author troïmaclure
 */
public class Entite extends Sprite implements IDrawable {

    public Circle anchor;

    public AbstractShader shader;
    public boolean visible = true;
    public AbstractBusiness buisiness;
    public int speed = 2;
    public int strenght = 2;
    public int pv = 5;

    public Entite(String path, int srcX, int srcY, int srcWidth, int srcHeight) {
        super(TextureUtil.getTexture(path), srcX, srcY, srcWidth, srcHeight);
        this.setX(srcX);
        this.setY(srcY);
        calculateAnchors();
    }

    @Override
    public void draw(SpriteBatch batch) {
        calculateAnchors();
        Rectangle r = this.getBoundingRectangle();
        batch.draw(this.getTexture(), r.x, r.y, r.width, r.height);

    }

    private void calculateAnchors() {
        Rectangle r = this.getBoundingRectangle();
        Vector2 center = new Vector2();
        r.getCenter(center);
        this.anchor = new Circle(center, getAnchorSize());
    }

    public float getAnchorSize() {
        return Constantes.TILESIZE / 2;
    }

}
