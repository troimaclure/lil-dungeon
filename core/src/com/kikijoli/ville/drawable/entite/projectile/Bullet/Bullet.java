package com.kikijoli.ville.drawable.entite.projectile.Bullet;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.drawable.entite.projectile.Projectile;
import com.kikijoli.ville.interfaces.ISpriteDrawable;
import com.kikijoli.ville.util.MathUtils;
import com.kikijoli.ville.util.TextureUtil;

/**
 *
 * @author ajosse
 */
public abstract class Bullet extends Projectile implements ISpriteDrawable {

    public Sprite sprite;
    Vector2 centerOrigin;

    public Bullet(String path, Vector2 destination, int scope, Entite author, float x, float y, float width, float height) {
        super(destination, scope, author, x, y, width, height);
        this.speed = 5;
        this.sprite = new Sprite(TextureUtil.getTexture(path));
        centerOrigin = MathUtils.getCenter(new Rectangle(0, 0, width, height));

    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(sprite.getTexture(),
            getX(), getY(),
            centerOrigin.x,
            centerOrigin.y,
            (int) width, (int) height,
            1, 1,
            sprite.getRotation(),
            (int) 0,
            (int) 0,
            (int) sprite.getTexture().getWidth(), (int) sprite.getTexture().getHeight(),
            false, false);
    }

}
