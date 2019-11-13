package com.kikijoli.ville.drawable.hud;

import com.kikijoli.ville.component.PebbleComponent;
import com.kikijoli.ville.drawable.entite.simple.Pebble;
import com.kikijoli.ville.manager.EntiteManager;
import com.kikijoli.ville.util.Constantes;

/**
 *
 * @author ajosse
 */
public class PebbleTile extends Tile {

    public PebbleTile() {
        super(new Pebble(0, 0, Constantes.TILESIZE, Constantes.TILESIZE));
    }

    @Override
    public void action() {
        super.action();
        EntiteManager.player.currentComponent = EntiteManager.player.getComponent(PebbleComponent.class);
    }

}
