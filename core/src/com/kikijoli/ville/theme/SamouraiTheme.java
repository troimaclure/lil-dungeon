package com.kikijoli.ville.theme;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.kikijoli.ville.drawable.entite.npc.Guard;
import com.kikijoli.ville.drawable.hud.BowTile;
import com.kikijoli.ville.drawable.hud.SwordTile;
import com.kikijoli.ville.manager.EntiteManager;
import com.kikijoli.ville.manager.LockManager;
import java.util.Arrays;

/**
 *
 * @author ajosse
 */
public class SamouraiTheme extends AbstractTheme {

    public SamouraiTheme() {
        super(Arrays.asList(new BowTile(), new SwordTile()));
    }

    @Override
    public void handleFromTmx(TiledMapTileMapObject entite) {
        switch (entite.getName()) {
            case "samourai":
                EntiteManager.addEntite(new Guard(entite.getX(), entite.getY()));
                break;
            case "key":
                LockManager.addKey(entite.getX(), entite.getY());
                break;
            case "lock":
                LockManager.addLock(entite.getX(), entite.getY());
                break;
        }

    }

    @Override
    public Color getFontColor() {
        return Color.valueOf("#27ae60");
    }

}
