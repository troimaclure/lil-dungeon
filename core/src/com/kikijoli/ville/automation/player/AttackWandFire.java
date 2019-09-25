/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.automation.player;

import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.drawable.entite.projectile.Spell.FireSpell;
import com.kikijoli.ville.manager.SpellManager;

/**
 *
 * @author Arthur
 */
public abstract class AttackWandFire extends AttackWandPoison {

    public AttackWandFire(Entite entite, Vector2 destination) {
        super(entite, destination);
        this.delay = 60 * 5;
    }

    @Override
    public void act() {
        super.act();
    }

    @Override
    protected void shoot() {
        FireSpell fire = new FireSpell(destination.x, destination.y, entite);
        SpellManager.spells.add(fire);
    }

}
