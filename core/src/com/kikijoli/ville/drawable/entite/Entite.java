/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.drawable.entite;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.business.AbstractBusiness;
import com.kikijoli.ville.component.IComponent;
import com.kikijoli.ville.drawable.entite.simple.PlayerShield;
import com.kikijoli.ville.shader.AbstractShader;
import com.kikijoli.ville.util.Constantes;
import com.kikijoli.ville.util.TextureUtil;
import com.kikijoli.ville.interfaces.ISpriteDrawable;
import com.kikijoli.ville.manager.MessageManager;
import com.kikijoli.ville.save.EntiteWrapper;
import com.kikijoli.ville.util.Count;
import com.kikijoli.ville.util.MathUtils;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author troïmaclure
 */
public abstract class Entite extends Sprite implements ISpriteDrawable {

    public Count touchedCount = new Count(0, 60);
    public boolean touched = false;
    public ArrayList<IComponent> components = new ArrayList<>();
    public IComponent currentComponent;
    public boolean isTouchable = true;
    public int point = 500;
    public Circle anchor;
    public boolean good;
    public AbstractShader shader;
    public boolean visible = true;
    public AbstractBusiness business;
    public int speed = 2;
    public int strenght = 2;
    public Vector2 centerOrigin;
    public int customWidth;
    public int customHeight;
    public PlayerShield shield;

    public Entite(String path, float srcX, float srcY, float srcWidth, float srcHeight) {
        super(TextureUtil.getTexture(path), (int) srcX, (int) srcY, (int) srcWidth, (int) srcHeight);
        this.setX(srcX);
        this.setY(srcY);
        calculateAnchors();
        centerOrigin = MathUtils.getCenter(new Rectangle(0, 0, srcWidth, srcHeight));
        customWidth = (int) srcWidth;
        customHeight = (int) srcHeight;
    }

    public Entite(String path, float x, float y) {
        this(path, x, y, Constantes.TILESIZE, Constantes.TILESIZE);
    }

    public void load(EntiteWrapper wrapper) {
        this.setX(wrapper.x);
        this.setY(wrapper.y);
        try {
            Class<?> loadClass = Entite.class.getClassLoader().loadClass(wrapper.classDestination);
            Method declaredMethod = loadClass.getDeclaredMethod(wrapper.methodDestination, Entite.class);
            declaredMethod.invoke(null, this);
        } catch (ClassNotFoundException | SecurityException | NoSuchMethodException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(Entite.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        calculateAnchors();
        if (touched) {
            touched = !touchedCount.stepAndComplete();
            batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, 0.5f);
        }
        if (this.shield != null) {
            this.shield.step(this.getX(), this.getY(), this.getWidth(), this.getHeight());
            this.shield.draw(batch);
        }
        this.draw(batch, getTexture());
        if (currentComponent != null) {
            this.currentComponent.draw(batch);
        }
        batch.setColor(Color.WHITE);
        batch.setColor(batch.getColor().r, batch.getColor().g, batch.getColor().b, 1.0f);

    }

    protected void draw(SpriteBatch batch, Texture texture) {
        batch.draw(texture, getX(), getY(), centerOrigin.x, centerOrigin.y, (int) customWidth, (int) customHeight, 1, 1,
                getRotation(), (int) 0, (int) 0, (int) getTexture().getWidth(), (int) getTexture().getHeight(), false,
                false);
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
        if (this.business != null)
            this.business.stop = true;
    }

    public IComponent getComponent(Class clazz) throws NullPointerException {
        Optional<IComponent> findFirst = this.components.stream().filter(e -> e.getClass().equals(clazz)).findFirst();
        if (findFirst.isPresent())
            return findFirst.get();
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
        MessageManager.addIndicator(centerString.x, getY() + getHeight() + 10, message, color, duration);
    }

    public void talk(String message, Color color) {
        this.talk(message, color, 60);
    }

    public void talkDouble(String message, Color color, Color fore) {
        this.talkDouble(message, color, fore, 60);
    }

    public void talkDouble(String message, Color color, Color fore, int duration) {
        Vector2 centerString = MathUtils.centerString(message, MessageManager.SHOWG, this.getBoundingRectangle());
        MessageManager.addIndicator(centerString.x + 4, getY() + getHeight() + 8, message, color, 60);
        MessageManager.addIndicator(centerString.x, getY() + getHeight() + 10, message, fore, 60);

    }

    public void lookAt(Entite e) {
        Vector2 center = getCenter();
        this.setRotation(90 + MathUtils.getRotation(e.getX(), e.getY(), center.x, center.y));

    }

    public EntiteWrapper write(String classDest, String propertyDest) {
        return new EntiteWrapper(getX(), getY(), classDest, propertyDest, this.getClass().getCanonicalName());
    }

}
