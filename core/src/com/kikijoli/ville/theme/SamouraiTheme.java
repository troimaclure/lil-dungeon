package com.kikijoli.ville.theme;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.kikijoli.ville.drawable.entite.npc.ArcherSamourai;
import com.kikijoli.ville.drawable.entite.npc.Samourai;
import com.kikijoli.ville.drawable.entite.object.Help;
import com.kikijoli.ville.drawable.entite.object.Key;
import com.kikijoli.ville.drawable.hud.BowTile;
import com.kikijoli.ville.drawable.hud.SwordTile;
import com.kikijoli.ville.manager.EntiteManager;
import com.kikijoli.ville.manager.LockManager;
import com.kikijoli.ville.manager.ObjectManager;
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
                EntiteManager.addEntite(new Samourai(entite.getX(), entite.getY()));
                break;
            case "archersamourai":
                EntiteManager.addEntite(new ArcherSamourai(entite.getX(), entite.getY()));
                break;
            case "key":
                ObjectManager.objects.add(new Key(entite.getX(), entite.getY()));
                break;
            case "lock":
                LockManager.addLock(entite.getX(), entite.getY());
                break;
            case "help":
                ObjectManager.objects.add(new Help(entite.getX(), entite.getY()));
                break;
        }

    }

    @Override
    public Color getFontColor() {
        return Color.valueOf("#27ae60");
    }

}
