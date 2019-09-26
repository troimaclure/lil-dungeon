package com.kikijoli.ville.effect;

import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.manager.EntiteManager;
import com.kikijoli.ville.manager.ParticleManager;
import com.kikijoli.ville.util.MathUtils;

/**
 *
 * @author ajosse
 */
public class PoisonEffect extends AbstractEffect {

    private int count = 90;

    public PoisonEffect(float x, float y) {
        super(ParticleManager.addParticle("particle/poison.p", x, y, 0.5f));
    }

    @Override
    public void tour(Entite entite) {
        Vector2 center = MathUtils.getCenter(entite.getBoundingRectangle());
        this.effect.setPosition(center.x, center.y);
        this.count--;
        if (this.count <= 0) {
            EntiteManager.touch(entite);
        }
    }

}
