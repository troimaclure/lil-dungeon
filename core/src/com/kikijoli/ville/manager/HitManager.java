package com.kikijoli.ville.manager;

import com.kikijoli.ville.drawable.entite.Entite;

/**
 *
 * @author ajosse
 */
public class HitManager {

    public static void hit(Entite entite, Entite target) {
        int hit = (int) (entite.strenght + (Math.random() * entite.strenght));
        target.pv -= hit;
    }
}
