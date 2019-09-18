package com.kikijoli.ville.automation.ennemy;

import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.abstracts.AbstractAction;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.manager.DrawManager;
import com.kikijoli.ville.manager.EntiteManager;
import com.kikijoli.ville.shader.ClickShader;
import com.kikijoli.ville.util.MathUtils;

/**
 *
 * @author ajosse
 */
public abstract class AttackDirectionPreparation extends AbstractAction {

    public Vector2 destination;
    boolean prepared = false;
    public Entite entite;
    int count = 0;
    int delay = 30;
    Entite draw;

    public AttackDirectionPreparation(Entite entite, Entite draw) {
        this.entite = entite;
        this.draw = draw;

    }

    @Override
    public void act() {
        if (entite.shader == null) {
            entite.shader = new ClickShader(entite, null);
        }
        if (prepared) {
            if (count++ >= delay) finish();
            return;
        }
        prepared = true;
        destination = MathUtils.getCenter(EntiteManager.player.getBoundingRectangle());

        DrawManager.entites.add(draw);
    }

    public void finish() {
        DrawManager.entites.remove(draw);
        onComplete();
    }

    public abstract void onComplete();

}
