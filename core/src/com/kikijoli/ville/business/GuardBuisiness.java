/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.business;

import com.kikijoli.ville.automation.WaitPlayer;
import com.kikijoli.ville.abstracts.AbstractAction;
import com.kikijoli.ville.drawable.entite.npc.Guard;

/**
 *
 * @author Arthur
 */
public class GuardBuisiness extends AbstractBusiness {

    Guard guard;

    public GuardBuisiness(Guard guard) {
        this.guard = guard;
    }

    @Override
    public AbstractAction getDefault() {
        return new WaitPlayer(guard);
    }

}
