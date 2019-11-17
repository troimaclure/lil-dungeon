package com.kikijoli.ville.drawable.hud;

import com.kikijoli.ville.component.VanishComponent;
import com.kikijoli.ville.drawable.entite.simple.Vanish;
import com.kikijoli.ville.manager.EntiteManager;
import com.kikijoli.ville.util.Constantes;

/**
 *
 * @author ajosse
 */
public class VanishTile extends TileWithAmmo {

    public VanishTile() {
        super(new Vanish(0, 0, Constantes.TILESIZE, Constantes.TILESIZE), () -> {
            return EntiteManager.vanishCount;
        });
    }

    @Override
    public void action() {
        EntiteManager.player.currentComponent = EntiteManager.player.getComponent(VanishComponent.class);
        super.action();
    }

}
