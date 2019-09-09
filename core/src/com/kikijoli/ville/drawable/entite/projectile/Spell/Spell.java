package com.kikijoli.ville.drawable.entite.projectile.Spell;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Rectangle;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.effect.AbstractEffect;

/**
 *
 * @author ajosse
 */
public interface Spell {

    public Class getEffectType();

    public AbstractEffect getEffect(float x, float y);

    public Rectangle getAnchors();

    public Entite getAuthor();

    public ParticleEffect getParticleEffect();
}
