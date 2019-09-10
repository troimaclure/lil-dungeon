/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.manager;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.kikijoli.ville.drawable.entite.simple.Indicator;
import com.kikijoli.ville.maps.Tmap;
import java.util.ArrayList;

/**
 *
 * @author troïmaclure
 */
public class MessageManager {

    public static BitmapFont segoe = new BitmapFont(new FileHandle("font/segoeui.fnt"));
    public static ArrayList<Indicator> indicators = new ArrayList<>();

    public static void addIndicator(float x, float y, String message, Texture texture) {
        indicators.add(new Indicator(x, y, message, texture));
    }

    public static void drawIndicators() {
        MessageManager.segoe.setColor(ColorManager.getTextureColor());
        Tmap.spriteBatch.setColor(ColorManager.getTextureColor());
        MessageManager.segoe.getData().setScale(.50f);
        getIndicators().forEach((indicator) -> {
            indicator.draw(Tmap.spriteBatch);
        });

    }

    private static ArrayList<Indicator> getIndicators() {
        return (ArrayList<Indicator>) indicators.clone();
    }

}
