/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.automation.player;

import com.kikijoli.ville.abstracts.AbstractAction;
import com.kikijoli.ville.component.SwordComponent;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.drawable.entite.simple.Sword;
import com.kikijoli.ville.manager.EntiteManager;
import com.kikijoli.ville.util.Count;

/**
 *
 * @author Arthur
 */
public class AttackSword extends AbstractAction {

    public int countSword = 0;
    private final Entite entite;
    private final Sword sword;
    Count cooldown = new Count(0, 10);
    public Runnable finish;

    /**
     *
     * @param entite
     * @param finish
     */
    public AttackSword(Entite entite, Runnable finish) {
        this.entite = entite;
        this.sword = ((SwordComponent) this.entite.getComponent(SwordComponent.class)).sword;
        this.finish = finish;
    }

    @Override
    public void act() {

        sword.setRotation(-countSword++ * 24 + entite.getRotation());
        EntiteManager.attack(entite);
        if (cooldown.stepAndComplete()) {
            end();
        }
    }

    private void end() {
        finish.run();
    }

}
