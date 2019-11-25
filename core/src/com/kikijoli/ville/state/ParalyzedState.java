package com.kikijoli.ville.state;

import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.util.Count;

/**
 *
 * @author ajosse
 */
public class ParalyzedState extends AbstractState {

    public ParalyzedState(Count duration, Entite entite) {
        super(duration, entite, true);
    }

    @Override
    public void handleMove() {

    }

    @Override
    public void stop() {

    }

}
