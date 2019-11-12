package com.kikijoli.ville.drawable.entite.object;

import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.manager.EntiteManager;

/**
 *
 * @author ajosse
 */
public class Help extends Entite implements IObject {

    public Help(float x, float y) {
        super("sprite/help.png", x, y);
    }

    @Override
    public void get() {
        EntiteManager.player.pv += EntiteManager.player.maxPv > EntiteManager.player.pv ? 1 : 0;
    }

}
