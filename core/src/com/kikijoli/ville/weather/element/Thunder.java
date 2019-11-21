/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.weather.element;

import com.kikijoli.ville.abstracts.AbstractAction;
import com.kikijoli.ville.maps.Tmap;
import com.kikijoli.ville.util.Count;

/**
 *
 * @author Arthur
 */
public class Thunder extends AbstractAction {

    public float index = 0;
    public float white = 0;
    public Count duration;

    public Thunder() {
        duration = new Count(0, 60);
    }

    @Override
    public void act() {

        index -= 0.05f;
        if (index <= white) {
            index = (float) Math.random();
        }
        Tmap.getRay().setAmbientLight(
                index,
                index,
                index,
                index
        );

    }

    @Override
    public boolean isFinish() {
        return this.duration.stepAndComplete();
    }

}
