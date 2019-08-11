/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.automation;

import com.kikijoli.ville.abstracts.AbstractAction;
import com.kikijoli.ville.drawable.entite.Entite;

/**
 *
 * @author Arthur
 */
public abstract class Hit extends AbstractAction {

    Entite entite;
    public int boost = 10;
    public int count = 0;
    public int delay = 10;
    public int oldSpeed = 0;

    public Hit(Entite entite) {
        this.entite = entite;
        oldSpeed = entite.speed;
        this.entite.speed += boost;
    }

    @Override
    public void act() {
        if (count++ < delay) return;
        if (entite.speed > oldSpeed) {
            entite.speed--;
        } else onFinish();
    }

    public abstract void onFinish();

}
