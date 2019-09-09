package com.kikijoli.ville.drawable.entite.projectile.Spell;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Rectangle;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.manager.ParticleManager;

/**
 *
 * @author ajosse
 */
public abstract class DurationSpell extends Rectangle implements Spell {

    public ParticleEffect effect;
    public int cooldown;
    public String path;
    public Entite author;

    public DurationSpell(String path, int cooldown, float x, float y, float width, float height, Entite author) {
        super(x, y, width, height);
        effect = ParticleManager.addParticle(path, x, y, 1);
        this.cooldown = cooldown;
        this.author = author;
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
