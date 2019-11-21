/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.weather;

import com.badlogic.gdx.graphics.Color;
import com.kikijoli.ville.maps.Tmap;

/**
 *
 * @author Arthur
 */
public class ClearNightWeather extends AbstractWeather {

    public Color weatherColor = new Color(Color.BLUE.r, Color.BLUE.g, Color.BLUE.b, 0.3f);

    public ClearNightWeather() {
        Tmap.getRay().setAmbientLight(weatherColor);
    }
}
