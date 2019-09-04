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
import com.kikijoli.ville.shader.AbstractShader;
import com.kikijoli.ville.util.Constantes;
import com.kikijoli.ville.util.TextureUtil;
import com.kikijoli.ville.interfaces.ISpriteDrawable;
import com.kikijoli.ville.util.MathUtils;

/**
 *
 * @author troïmaclure
 */
public class Entite extends Sprite implements ISpriteDrawable {

	public Circle anchor;
	public boolean good;
	public AbstractShader shader;
	public boolean visible = true;
	public AbstractBusiness buisiness;
	public int speed = 2;
	public int strenght = 2;
	public int pv = 5;
	Vector2 centerOrigin;
	int width;
	int height;

	public Entite(String path, int srcX, int srcY, int srcWidth, int srcHeight) {
		super(TextureUtil.getTexture(path), srcX, srcY, srcWidth, srcHeight);
		this.setX(srcX);
		this.setY(srcY);
		calculateAnchors();
		centerOrigin = MathUtils.getCenter(new Rectangle(0, 0, srcWidth, srcHeight));
		width = srcWidth;
		height = srcHeight;
	}

	@Override
	public void draw(SpriteBatch batch) {
		calculateAnchors();
//        Rectangle r = this.getBoundingRectangle();
		batch.draw(getTexture(),
				getX(), getY(),
				centerOrigin.x,
				centerOrigin.y,
				(int) width, (int) height,
				1, 1,
				getRotation(),
				(int) 0,
				(int) 0,
				(int) getTexture().getWidth(), (int) getTexture().getHeight(),
				false, false);
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
