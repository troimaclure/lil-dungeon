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
public abstract class AttackSword extends AbstractAction {

    public int countSword = 0;
    public int delaySword = 30;
    private final Entite entite;
    private final Sword sword;
    Count cooldown = new Count(0, 30);

    /**
     *
     * @param entite
     */
    public AttackSword(Entite entite) {
        this.entite = entite;
        this.sword = ((SwordComponent) this.entite.getComponent(SwordComponent.class)).sword;
    }

    @Override
    public void act() {
        countSword += 3;
        if (countSword < delaySword)
            sword.setRotation(-countSword * 12 + entite.getRotation());
        EntiteManager.attack(entite);
        if (cooldown.stepAndComplete()) {
            end();
        }
    }

    public abstract void onFinish();

    private void end() {
        onFinish();
    }

}
