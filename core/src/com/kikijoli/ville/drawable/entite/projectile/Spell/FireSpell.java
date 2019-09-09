package com.kikijoli.ville.drawable.entite.projectile.Spell;

import com.badlogic.gdx.math.Rectangle;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.effect.AbstractEffect;
import com.kikijoli.ville.effect.FireEffect;

/**
 *
 * @author ajosse
 */
public class FireSpell extends DurationSpell {

    public FireSpell(float x, float y, Entite author) {
        super("particle/fire.p", 120, x, y, 100, 75, author);
    }

    @Override
    public AbstractEffect getEffect(float x, float y) {
        return new FireEffect(x, y);
    }

    @Override
    public Class getEffectType() {
        return FireEffect.class;
    }

    @Override
    public Rectangle getAnchors() {
        return new Rectangle(x - width / 2, y - 20, width, height);
    }
}
