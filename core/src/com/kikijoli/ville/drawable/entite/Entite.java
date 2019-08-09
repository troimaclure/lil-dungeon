/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.drawable.entite;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.kikijoli.ville.abstracts.AbstractAction;
import com.kikijoli.ville.automation.None;
import com.kikijoli.ville.business.AbstractBusiness;
import com.kikijoli.ville.interfaces.IDrawable;
import com.kikijoli.ville.shader.AbstractShader;
import com.kikijoli.ville.util.TextureUtil;

/**
 *
 * @author troïmaclure
 */
public class Entite extends Sprite implements IDrawable {

    public AbstractAction action = new None();
    public AbstractShader shader;
    public boolean visible = true;
    public AbstractBusiness buisiness;

    public Entite(String path, int srcX, int srcY, int srcWidth, int srcHeight) {
        super(TextureUtil.getTexture(path), srcX, srcY, srcWidth, srcHeight);
        this.setX(srcX);
        this.setY(srcY);
    }

    @Override
    public void draw(SpriteBatch batch) {
        Rectangle r = this.getBoundingRectangle();
        batch.draw(this.getTexture(), r.x, r.y, r.width, r.height);

    }

}
