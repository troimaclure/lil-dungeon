/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.automation;

import com.kikijoli.ville.abstracts.AbstractAction;
import com.kikijoli.ville.drawable.entite.npc.Guard;
import com.kikijoli.ville.manager.EntiteManager;

/**
 *
 * @author Arthur
 */
public class WaitPlayer extends AbstractAction {

    Guard guard;

    public WaitPlayer(Guard guard) {
        this.guard = guard;
    }

    @Override
    public void act() {
        if (guard.vision.contains(EntiteManager.player.getX(), EntiteManager.player.getY())) {
           guard.action = new AttackPlayer(guard); 
        }
    }

}
