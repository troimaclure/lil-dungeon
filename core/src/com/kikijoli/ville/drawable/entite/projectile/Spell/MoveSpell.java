package com.kikijoli.ville.drawable.entite.projectile.Spell;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.drawable.entite.projectile.Projectile;
import com.kikijoli.ville.manager.ParticleManager;

/**
 *
 * @author ajosse
 */
public abstract class MoveSpell extends Projectile implements Spell {

    public ParticleEffect effect;
    public Rectangle anchors;

    public MoveSpell(String path, Vector2 destination, int scope, Entite author, float x, float y) {
        super(destination, scope, author, x, y, 50, 50);
        effect = ParticleManager.addParticle(path, x, y, 1);
        this.speed = 2;
        this.anchors = new Rectangle(0, 0, width, height);
    }

    @Override
    public void move() {
        super.move();
        effect.setPosition(this.getX(), this.getY());
        this.anchors.setPosition(x - width / 2, y - height / 2);
    }

    @Override
    public Rectangle getAnchors() {
        return this.anchors;
    }

    @Override
    public Entite getAuthor() {
        return this.author;
    }

    @Override
    public ParticleEffect getParticleEffect() {
        return this.effect;
    }
}
