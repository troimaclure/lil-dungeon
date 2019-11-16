/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.weather;

import com.badlogic.gdx.graphics.Color;
import com.kikijoli.ville.abstracts.AbstractAction;
import com.kikijoli.ville.maps.Tmap;

/**
 *
 * @author Arthur
 */
public class FogWeather extends AbstractAction {

    public Color weatherColor = new Color(Color.GRAY.r, Color.GRAY.g, Color.GRAY.b, 0.6f);

    public FogWeather() {
        Tmap.getRay().setAmbientLight(weatherColor);
    }

    @Override
    public void act() {

    }

}
