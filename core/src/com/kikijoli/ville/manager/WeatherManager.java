/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.manager;

import com.kikijoli.ville.maps.Tmap;
import com.kikijoli.ville.weather.AbstractWeather;

/**
 *
 * @author Arthur
 */
public class WeatherManager {

    public static AbstractWeather currentWeather;

    public static void tour() {
        if (currentWeather != null)
            currentWeather.act();
    }

    public static void draw() {
        if (currentWeather != null) {
            currentWeather.draw(Tmap.spriteBatch);
        }
    }
}
