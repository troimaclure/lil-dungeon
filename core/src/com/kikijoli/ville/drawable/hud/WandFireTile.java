package com.kikijoli.ville.drawable.hud;

import com.kikijoli.ville.drawable.entite.simple.Wand;
import com.kikijoli.ville.util.Constantes;

/**
 *
 * @author ajosse
 */
public class WandFireTile extends Tile {

    public WandFireTile(int srcX, int srcY) {
        super(srcX, srcY, new Wand(0, 0, Constantes.TILESIZE, Constantes.TILESIZE));
    }

    @Override
    public void action() {
        super.action();
//        EntiteManager.player.mode = Mode.WANDFIRE;
    }

}
