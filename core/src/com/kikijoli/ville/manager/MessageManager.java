/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.manager;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.drawable.entite.simple.Indicator;
import com.kikijoli.ville.drawable.entite.simple.Message;
import com.kikijoli.ville.maps.Tmap;
import java.util.ArrayList;

/**
 *
 * @author troïmaclure
 */
public class MessageManager {

    public static BitmapFont segoe;
    public static BitmapFont SHOWG;
    public static ArrayList<Indicator> indicators = new ArrayList<>();
    public static ArrayList<Message> messages = new ArrayList<>();

    static {
        SHOWG = FontManager.getFont("SHOWG", 25, Color.GREEN);
        segoe = FontManager.getFont("vinet", 25, Color.WHITE);
    }

    public static void addIndicator(float x, float y, String message, Entite entite) {
        indicators.add(new Indicator(x, y, message, entite));
    }

    public static void addIndicator(float x, float y, String message, Entite entite, Color color) {
        indicators.add(new Indicator(x, y, message, entite, color));
    }

    public static void addIndicator(float x, float y, String message, Entite entite, Color color, int count) {
        indicators.add(new Indicator(x, y, message, entite, color, count));
    }

    public static void addMessage(float x, float y, String message, Color color, int time) {
        messages.add(new Message(x, y, message, color, time));
    }

    public static void tour() {
        getIndicators().forEach((indicator) -> {
            indicator.draw(Tmap.spriteBatch);
            if (indicator.count <= 0) {
                indicators.remove(indicator);
            }
        });
        getMessages().forEach((indicator) -> {
            indicator.draw(Tmap.spriteBatch);
            if (indicator.count <= 0) {
                messages.remove(indicator);
            }
        });
    }

    private static ArrayList<Indicator> getIndicators() {
        return (ArrayList<Indicator>) indicators.clone();
    }

    private static ArrayList<Message> getMessages() {
        return (ArrayList<Message>) messages.clone();
    }

}
