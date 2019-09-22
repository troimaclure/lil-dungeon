package com.kikijoli.ville.drawable.entite.projectile.Spell;

import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.effect.AbstractEffect;
import com.kikijoli.ville.effect.PoisonEffect;
import com.kikijoli.ville.util.Constantes;

/**
 *
 * @author ajosse
 */
public class PoisonSpell extends MoveSpell {

    public PoisonSpell(Vector2 destination, Entite author, float x, float y) {
        super("particle/poison.p", destination, 1000, author, x, y);
        this.speed = 15;
    }

    @Override
    public AbstractEffect getEffect(float x, float y) {
        return new PoisonEffect(x, y);
    }

    @Override
    public Class getEffectType() {
        return PoisonEffect.class;
    }

    @Override
    public String getMouvementFilter() {
        return Constantes.BULLET_MOVEMENT_OK;
    }
}
