/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.business;

import com.kikijoli.ville.abstracts.AbstractAction;
import com.kikijoli.ville.maps.Tmap;
import java.util.HashMap;

/**
 *
 * @author troïmaclure
 */
public abstract class AbstractBusiness extends AbstractAction {

    public HashMap<String, AbstractAction> actions = new HashMap<>();

    protected AbstractAction current;

    public boolean stop;

    @Override
    public void act() {
        if (stop) return;

        if (Tmap.settingLevel) return;
        ((HashMap<String, AbstractAction>) actions.clone()).values().stream().forEach((action) -> {
            action.act();
        });
        if (current == null) {
            current = getDefault();
        } else if (current != null) {
            current.act();
        }
    }

    public abstract AbstractAction getDefault();

}
