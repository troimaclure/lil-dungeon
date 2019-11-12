/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.automation.ennemy;

import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.automation.player.AttackBow;
import com.kikijoli.ville.drawable.entite.Entite;

/**
 *
 * @author Arthur
 */
public abstract class AttackBowEnnemy extends AttackBow {

    int count = 0, delay = 20;

    public AttackBowEnnemy(Entite entite, Vector2 destination) {
        super(entite, destination);
    }

    @Override
    public void act() {
        if (count++ < delay) return;
        super.act();
    }

}
