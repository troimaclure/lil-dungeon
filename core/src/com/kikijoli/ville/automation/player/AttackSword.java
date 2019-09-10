/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.automation.player;

import com.kikijoli.ville.abstracts.AbstractAction;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.drawable.entite.simple.Sword;
import com.kikijoli.ville.manager.DrawManager;
import com.kikijoli.ville.manager.EntiteManager;

/**
 *
 * @author Arthur
 */
public abstract class AttackSword extends AbstractAction {

    public int countSword = 0;
    public int delaySword = 30;
    Sword sword;
    private final Entite entite;

    /**
     *
     * @param entite
     */
    public AttackSword(Entite entite) {
        this.entite = entite;
    }

    @Override
    public void act() {
        addSwordIfNotExist();
        sword.setX((float) (entite.getX() - (entite.getWidth() * 1.5)));
        sword.setY(entite.getY() - entite.getHeight() / 2);
        sword.setRotation(-countSword * 12);
        EntiteManager.attack(entite);
        if (countSword++ > delaySword) end();
    }

    private void addSwordIfNotExist() {
        if (sword != null) return;
        sword = new Sword((int) (entite.getX()), (int) (entite.getY()));
        DrawManager.entites.add(sword);

    }

    public abstract void onFinish();

    private void end() {
        if (sword != null)
            DrawManager.entites.remove(sword);
        onFinish();
    }

}
