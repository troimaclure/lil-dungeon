/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.weather;

import com.kikijoli.ville.weather.element.Thunder;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.kikijoli.ville.abstracts.AbstractAction;
import com.kikijoli.ville.manager.ParticleManager;
import com.kikijoli.ville.maps.Tmap;
import com.kikijoli.ville.util.Count;

/**
 *
 * @author Arthur
 */
public class StormNightWeather extends AbstractWeather {

    public Color weatherColor = new Color(Color.BLUE.r, Color.BLUE.g, Color.BLUE.b, 0.3f);

    public final int THUNDER_DELAY_MAX = 30;
    public final int THUNDER_DELAY_MIN = 10;
    public Count thunder = getThunerCount();
    public boolean isAnimationRunning = false;
    public AbstractAction currentAnimation;

    public StormNightWeather() {
        Tmap.getRay().setAmbientLight(weatherColor);
        ParticleManager.addParticleFixed("particle/rain.p", -20, Gdx.graphics.getHeight(), 1f);
        ParticleManager.addParticleFixed("particle/rain.p", Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 1f);
    }

    public Count getThunerCount() {
        return new Count(0, MathUtils.random(60 * THUNDER_DELAY_MIN, 60 * THUNDER_DELAY_MAX));
    }

    @Override
    public void act() {

        if (currentAnimation != null) {
            currentAnimation.act();
            if (currentAnimation.isFinish()) {
                currentAnimation = null;
                Tmap.getRay().setAmbientLight(weatherColor);
            }
        } else if (thunder.stepAndComplete()) {
            currentAnimation = new Thunder();
            thunder = getThunerCount();
        }

    }

 

}
