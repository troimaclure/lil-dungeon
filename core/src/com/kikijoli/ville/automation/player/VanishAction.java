/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.automation.player;

import com.badlogic.gdx.graphics.Color;
import com.kikijoli.ville.abstracts.AbstractAction;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.manager.EntiteManager;
import com.kikijoli.ville.manager.ParticleManager;
import com.kikijoli.ville.util.Constantes;
import com.kikijoli.ville.util.Count;
import com.kikijoli.ville.util.Time;

/**
 *
 * @author Arthur
 */
public abstract class VanishAction extends AbstractAction {

    Entite entite;
    Count vanish = new Count(0, 2 * Time.SECONDE);

    public VanishAction(Entite entite) {
        this.entite = entite;
        EntiteManager.player.visible = false;
        EntiteManager.player.vanish = true;
        EntiteManager.player.talk("NINJA !", Color.WHITE);
        ParticleManager.addParticle("particle/disapear.p", EntiteManager.player.getX() + Constantes.TILESIZE / 2, EntiteManager.player.getY() + Constantes.TILESIZE / 2, 1.0f);

    }

    @Override
    public void act() {
        if (vanish.stepAndComplete()) {
            onFinish();
        }
    }

    public abstract void onFinish();

}
