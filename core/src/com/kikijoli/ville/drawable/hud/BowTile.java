package com.kikijoli.ville.drawable.hud;

import com.kikijoli.ville.component.BowComponent;
import com.kikijoli.ville.drawable.entite.simple.Bow;
import com.kikijoli.ville.manager.EntiteManager;
import com.kikijoli.ville.util.Constantes;
import com.kikijoli.ville.util.Mode;

/**
 *
 * @author ajosse
 */
public class BowTile extends Tile {

    public BowTile(int srcX, int srcY) {
        super(srcX, srcY, new Bow(0, 0, Constantes.TILESIZE, Constantes.TILESIZE));
    }

    @Override
    public void action() {
        super.action();
        EntiteManager.player.mode = Mode.BOW;
        EntiteManager.player.currentComponent = new BowComponent(EntiteManager.player);
    }

}
