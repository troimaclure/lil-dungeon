/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.business;

import com.badlogic.gdx.graphics.Texture;
import com.kikijoli.ville.abstracts.AbstractAction;
import java.util.HashMap;

/**
 *
 * @author troïmaclure
 */
public abstract class AbstractBusiness extends AbstractAction {

    protected HashMap<String, AbstractAction> actions = new HashMap<>();

    protected AbstractAction current;

    @Override
    public void act() {
        actions.values().stream().forEach((action) -> {
            action.act();
        });
        if (current == null) {
            current = getDefault();
        } else if (current != null) {
            current.act();
        }
    }

    public abstract AbstractAction getDefault();

    public abstract boolean isWorking();

    public abstract Texture getTexture();

}
