package com.kikijoli.ville.theme;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.maps.tiled.objects.TiledMapTileMapObject;
import com.kikijoli.ville.drawable.entite.build.Firecamp;
import com.kikijoli.ville.drawable.entite.npc.ArcherSamourai;
import com.kikijoli.ville.drawable.entite.npc.Samourai;
import com.kikijoli.ville.drawable.entite.object.ArrowObject;
import com.kikijoli.ville.drawable.entite.object.Help;
import com.kikijoli.ville.drawable.entite.object.Key;
import com.kikijoli.ville.drawable.hud.BowTile;
import com.kikijoli.ville.drawable.hud.VanishTile;
import com.kikijoli.ville.drawable.hud.PebbleTile;
import com.kikijoli.ville.drawable.hud.SwordTile;
import com.kikijoli.ville.manager.CheckpointManager;
import com.kikijoli.ville.manager.EntiteManager;
import com.kikijoli.ville.manager.LockManager;
import com.kikijoli.ville.manager.ObjectManager;
import java.util.Arrays;

/**
 *
 * @author ajosse
 */
public class SamouraiTheme extends AbstractTheme {

    private static final String ARROW = "arrow";
    private static final String HELP = "help";
    private static final String LOCK = "lock";
    private static final String KEY = "key";
    private static final String ARCHERSAMOURAI = "archersamourai";
    private static final String SAMOURAI = "samourai";
    public static final String FIRECAMP = "firecamp";

    public SamouraiTheme() {
        super(Arrays.asList(new BowTile(), new SwordTile(), new PebbleTile(), new VanishTile()));

    }

    @Override
    public void handleFromTmx(TiledMapTileMapObject entite) {
        switch (entite.getName()) {
            case SAMOURAI:
                EntiteManager.addEntite(new Samourai(entite.getX(), entite.getY()));
                break;
            case ARCHERSAMOURAI:
                EntiteManager.addEntite(new ArcherSamourai(entite.getX(), entite.getY()));
                break;
            case KEY:
                ObjectManager.objects.add(new Key(entite.getX(), entite.getY()));
                break;
            case LOCK:
                LockManager.addLock(entite.getX(), entite.getY());
                break;
            case HELP:
                ObjectManager.objects.add(new Help(entite.getX(), entite.getY()));
                break;
            case ARROW:
                ObjectManager.objects.add(new ArrowObject(entite.getX(), entite.getY()));
                break;
            case FIRECAMP:
                CheckpointManager.firecamps.add(new Firecamp(entite.getX(), entite.getY()));
                break;
        }

    }

    @Override
    public Color getFontColor() {
        return Color.valueOf("#27ae60");
    }

}
