package com.kikijoli.ville.manager;

import com.kikijoli.ville.drawable.entite.projectile.Spell.DurationSpell;
import com.kikijoli.ville.drawable.entite.projectile.Spell.MoveSpell;
import com.kikijoli.ville.drawable.entite.projectile.Spell.Spell;
import com.kikijoli.ville.pathfind.GridManager;
import com.kikijoli.ville.util.Constantes;
import com.kikijoli.ville.util.MathUtils;
import java.util.ArrayList;

/**
 *
 * @author ajosse
 */
public class SpellManager {

    public static ArrayList<Spell> spells = new ArrayList<>();
    public static ArrayList<Spell> removes = new ArrayList<>();

    public static void tour() {
        spells.forEach((spell) -> {
            if (spell instanceof MoveSpell) {
                handleMoveSpell((MoveSpell) spell);
            } else if (spell instanceof DurationSpell) {
                handleDurationSpell(spell);
            }
            testCollision(spell);
        });
        removeAll();
    }

    private static void handleDurationSpell(Spell spell) {
        ((DurationSpell) spell).cooldown -= 1;
        if (((DurationSpell) spell).cooldown <= 0) {
            removes.add(spell);
        }
    }

    private static void handleMoveSpell(MoveSpell spell) {
        ((MoveSpell) spell).move();
        if (spell.distance >= spell.scope) {
            removes.add(spell);
        }
    }

    private static void testCollision(Spell bullet) {
        if (!GridManager.isClearZone(MathUtils.getCenter(bullet.getAnchors()), Constantes.BULLET_MOVEMENT_OK)) removes.add(bullet);
        EntiteManager.entites.stream().filter((entite) -> (!entite.equals(bullet.getAuthor()) && entite.good != bullet.getAuthor().good)).filter((entite) -> (bullet instanceof Spell && entite.getBoundingRectangle().overlaps(((Spell) bullet).getAnchors()))).forEachOrdered((entite) -> {
            EntiteManager.spellEffect(entite, ((Spell) bullet));
        });
    }

    private static void removeAll() {

        removes.forEach((spell) -> {
            ParticleManager.particleEffects.remove(spell.getParticleEffect());
        });
        spells.removeAll(removes);
    }
}
