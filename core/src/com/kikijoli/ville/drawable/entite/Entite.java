/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.drawable.entite;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.business.AbstractBusiness;
import com.kikijoli.ville.component.IComponent;
import com.kikijoli.ville.drawable.entite.simple.PlayerShield;
import com.kikijoli.ville.effect.AbstractEffect;
import com.kikijoli.ville.shader.AbstractShader;
import com.kikijoli.ville.util.Constantes;
import com.kikijoli.ville.util.TextureUtil;
import com.kikijoli.ville.interfaces.ISpriteDrawable;
import com.kikijoli.ville.manager.MessageManager;
import com.kikijoli.ville.util.MathUtils;
import java.util.ArrayList;
import java.util.Optional;

/**
 *
 * @author troïmaclure
 */
public abstract class Entite extends Sprite implements ISpriteDrawable {

    public ArrayList<IComponent> components = new ArrayList<>();
    public IComponent currentComponent;
    public boolean isTouchable = true;
    public int point = 500;
    public Circle anchor;
    public boolean good;
    public AbstractShader shader;
    public boolean visible = true;
    public AbstractBusiness buisiness;
    public int speed = 2;
    public int strenght = 2;
    public Vector2 centerOrigin;
    public int width;
    public int height;
    public ArrayList<AbstractEffect> effects = new ArrayList<>();
    public PlayerShield shield;

    public Entite(String path, float srcX, float srcY, float srcWidth, float srcHeight) {
        super(TextureUtil.getTexture(path), (int) srcX, (int) srcY, (int) srcWidth, (int) srcHeight);
        this.setX(srcX);
        this.setY(srcY);
        calculateAnchors();
        centerOrigin = MathUtils.getCenter(new Rectangle(0, 0, srcWidth, srcHeight));
        width = (int) srcWidth;
        height = (int) srcHeight;
    }

    public Entite(String path, float x, float y) {
        this(path, x, y, Constantes.TILESIZE, Constantes.TILESIZE);
    }

    @Override
    public void draw(SpriteBatch batch) {
        calculateAnchors();

        if (this.shield != null) {
            this.shield.step(this.getX(), this.getY(), this.getWidth(), this.getHeight());
            this.shield.draw(batch);
        }
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
        if (currentComponent != null) {
            this.currentComponent.draw(batch);
        }
    }

    protected void calculateAnchors() {
        Rectangle r = this.getBoundingRectangle();
        Vector2 center = new Vector2();
        r.getCenter(center);
        this.anchor = new Circle(center, getAnchorSize());
    }

    public float getAnchorSize() {
        return Constantes.TILESIZE;
    }

    public void dead() {
        this.currentComponent = null;
        this.components.clear();
        if (this.buisiness != null)
            this.buisiness.stop = true;
    }

    public IComponent getComponent(Class clazz) throws NullPointerException {
        Optional<IComponent> findFirst = this.components.stream().filter(e -> e.getClass().equals(clazz)).findFirst();
        if (findFirst.isPresent()) return findFirst.get();
        throw new NullPointerException();
    }

    public Vector2 getCenter() {
        return MathUtils.getCenter(this.getBoundingRectangle());
    }

    public Vector2 getPosition() {
        Vector2 pos = new Vector2();
        return this.getBoundingRectangle().getPosition(pos);
    }

    public void talk(String message, Color color, int duration) {
        Vector2 centerString = MathUtils.centerString(message, MessageManager.SHOWG, this.getBoundingRectangle());
        MessageManager.addIndicator(centerString.x, getY() + getHeight() + 10, message, this, color, duration);
    }

    public void talk(String message, Color color) {
        this.talk(message, color, 60);
    }

    public void talkDouble(String message, Color color, Color fore) {
        this.talkDouble(message, color, fore, 60);
    }

    public void talkDouble(String message, Color color, Color fore, int duration) {
        Vector2 centerString = MathUtils.centerString(message, MessageManager.SHOWG, this.getBoundingRectangle());
        MessageManager.addIndicator(centerString.x + 4, getY() + getHeight() + 8, message, this, color, 60);
        MessageManager.addIndicator(centerString.x, getY() + getHeight() + 10, message, this, fore, 60);

    }
}
