/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.automation;

import com.kikijoli.ville.abstracts.AbstractAction;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.manager.EntiteManager;
import com.kikijoli.ville.shader.ClickShader;

/**
 *
 * @author Arthur
 */
public abstract class Attack extends AbstractAction {

    public int count = 0;
    public int delay = 10;

    public Attack(Entite entite) {
        entite.shader = new ClickShader(entite, null);
        EntiteManager.attack(entite);
    }

    @Override
    public void act() {
        if (count++ > delay) onFinish();
    }

    public abstract void onFinish();

}
