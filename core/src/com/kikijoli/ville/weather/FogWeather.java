/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.weather;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kikijoli.ville.manager.StageManager;
import com.kikijoli.ville.maps.Tmap;
import com.kikijoli.ville.util.Constantes;
import com.kikijoli.ville.util.Count;
import com.kikijoli.ville.util.TextureUtil;

/**
 *
 * @author Arthur
 */
public class FogWeather extends AbstractWeather {

    public Color weatherColor = new Color(Color.WHITE.r, Color.WHITE.g, Color.WHITE.b, 0.5f);
    Sprite fog = new Sprite(TextureUtil.getTexture("sprite/fog.png"));
    int index = 0;
    int max = 100;
    boolean up = false;
    Count count = new Count(0, 5);

    public FogWeather() {
        Tmap.getRay().setAmbientLight(weatherColor);
    }

    @Override
    public void act() {
        if (count.stepAndComplete()) {
            up = index == max ? false : index == 0 ? true : up;
            index += up ? 1 : (-1);
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(fog.getTexture(), -max + index, 0, StageManager.widthd * Constantes.TILESIZE + max * 2, StageManager.heightd * Constantes.TILESIZE);
        batch.setColor(new Color(1, 1, 1, 0.4f));
        batch.draw(fog.getTexture(), -max + -index, 0, StageManager.widthd * Constantes.TILESIZE * 1.5f + max * 2, StageManager.heightd * Constantes.TILESIZE * 1.5f);
        batch.setColor(Color.WHITE);
    }

}
