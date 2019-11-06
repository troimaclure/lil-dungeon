package com.kikijoli.ville.effect;

import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.business.InFireBuisiness;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.interfaces.IBusiness;
import com.kikijoli.ville.manager.ParticleManager;
import com.kikijoli.ville.util.MathUtils;

/**
 *
 * @author ajosse
 */
public class FireEffect extends AbstractEffect {

    private int count = 60 * 3;

    public FireEffect(float x, float y) {
        super(ParticleManager.addParticle("particle/fireeffect.p", x, y, 0.5f));
    }

    @Override
    public void draw(Entite entite) {
        if (!(entite.buisiness instanceof InFireBuisiness)) {
            entite.buisiness = new InFireBuisiness(entite);
        }
        Vector2 center = MathUtils.getCenter(entite.getBoundingRectangle());
        this.effect.setPosition(center.x, center.y);
        this.count--;
        if (this.count <= 0) {
            if (entite instanceof IBusiness) {
                entite.buisiness = ((IBusiness) entite).getDefault();
            }
            end = true;
        }
    }

}
