package com.kikijoli.ville.drawable.hud;

import com.kikijoli.ville.component.SwordComponent;
import com.kikijoli.ville.drawable.entite.simple.Sword;
import com.kikijoli.ville.interfaces.IShapeDrawable;
import com.kikijoli.ville.interfaces.ISpriteDrawable;
import com.kikijoli.ville.manager.EntiteManager;
import com.kikijoli.ville.util.Constantes;

/**
 *
 * @author ajosse
 */
public class SwordTile extends Tile implements ISpriteDrawable, IShapeDrawable {

    public SwordTile(int srcX, int srcY) {
        super(srcX, srcY, new Sword(0, 0, Constantes.TILESIZE, Constantes.TILESIZE));
    }

    @Override
    public void action() {
        super.action();
        EntiteManager.player.currentComponent = EntiteManager.player.getComponent(SwordComponent.class);
    }

}
