/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.business;

import com.kikijoli.ville.abstracts.AbstractAction;
import com.kikijoli.ville.automation.Dash;
import com.kikijoli.ville.automation.None;
import com.kikijoli.ville.manager.EntiteManager;

/**
 *
 * @author Arthur
 */
public class PlayerBuisiness extends AbstractBusiness {

    public PlayerBuisiness() {
    }

    @Override
    public AbstractAction getDefault() {
        return new None();
    }

    public void dash() {
        if (!actions.containsKey("Dash"))
            actions.put("Dash", new Dash(EntiteManager.player) {
                @Override
                public void onFinish() {
                    actions.remove("Dash");
                }
            });
    }

}
