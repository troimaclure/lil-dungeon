package com.kikijoli.ville.business;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.abstracts.AbstractAction;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.manager.EntiteManager;
import com.kikijoli.ville.manager.SoundManager;
import com.kikijoli.ville.pathfind.GridManager;
import com.kikijoli.ville.util.Constantes;
import com.kikijoli.ville.util.MathUtils;

/**
 *
 * @author ajosse
 */
public class InFireBuisiness extends AbstractBusiness {

    Entite entite;

    public InFireBuisiness(Entite entite) {
        this.entite = entite;
    }

    @Override
    public AbstractAction getDefault() {
        return new FireFear();
    }

    private class FireFear extends AbstractAction {

        Vector2 vel;

        @Override
        public void act() {
            if (vel == null) {
                vel = MathUtils.destination(MathUtils.getCenter(EntiteManager.player.getBoundingRectangle()), new Vector2(entite.getX(), entite.getY()));
                SoundManager.playSound(SoundManager.IN_FIRE);
            }
            Vector2 goal = new Vector2(entite.getX() - vel.x * (entite.speed + 1), entite.getY() - vel.y * (entite.speed + 1));
            if (!GridManager.isClearZone(new Rectangle(goal.x, goal.y, entite.getWidth(), entite.getHeight()), Constantes.NPC_MOVEMENT_OK))
                return;
            entite.setX(goal.x);
            entite.setY(goal.y);
        }
    }

}
