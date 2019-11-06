package com.kikijoli.ville.effect;

import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.drawable.entite.projectile.Spell.MoveSpell;

/**
 *
 * @author ajosse
 */
public abstract class AbstractEffect {

    public ParticleEffect effect;
    MoveSpell spell;
    public boolean end = false;

    public AbstractEffect(ParticleEffect effect) {
        this.effect = effect;
    }

    public abstract void draw(Entite entite);
}
