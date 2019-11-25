package com.kikijoli.ville.state;

import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.util.Count;

/**
 *
 * @author ajosse
 */
public abstract class AbstractState {

    public Count duration;
    public boolean moveImpact;
    public Entite entite;

    public AbstractState(Count duration, Entite entite, boolean moveImpact) {
        this(duration, entite);
        this.moveImpact = moveImpact;
    }

    public AbstractState(Count duration, Entite entite) {
        this.duration = duration;
        this.entite = entite;
    }

    public abstract void handleMove();

    public void step() {
        duration.step();
        handleMove();
    }

    public boolean isFinish() {
        return duration.isComplete();
    }

    public abstract void stop();
}
