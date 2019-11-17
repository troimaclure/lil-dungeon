package com.kikijoli.ville.drawable.hud;

import com.kikijoli.ville.component.BowComponent;
import com.kikijoli.ville.drawable.entite.simple.Bow;
import com.kikijoli.ville.manager.EntiteManager;
import com.kikijoli.ville.util.Constantes;

/**
 *
 * @author ajosse
 */
public class BowTile extends TileWithAmmo {

    public BowTile() {
        super(new Bow(0, 0, Constantes.TILESIZE, Constantes.TILESIZE), () -> {
            return EntiteManager.arrowCount;
        });
    }

    @Override
    public void action() {
        super.action();
        EntiteManager.player.currentComponent = EntiteManager.player.getComponent(BowComponent.class);
    }

}
