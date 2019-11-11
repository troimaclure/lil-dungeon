/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.manager;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.drawable.entite.simple.Indicator;
import com.kikijoli.ville.drawable.entite.simple.Message;
import com.kikijoli.ville.maps.Tmap;
import com.kikijoli.ville.util.MathUtils;
import com.kikijoli.ville.util.Time;
import java.util.ArrayList;

/**
 *
 * @author troïmaclure
 */
public class MessageManager {

    public static BitmapFont segoe;
    public static BitmapFont SHOWG;
    public static BitmapFont LEVELFONT;
    public static ArrayList<Indicator> indicators = new ArrayList<>();
    public static ArrayList<Message> messages = new ArrayList<>();

    static {
        SHOWG = FontManager.getFont("SHOWG", 25, Color.GREEN);
        LEVELFONT = FontManager.getFont("SHOWG", 150, Color.GREEN);
        segoe = FontManager.getFont("vinet", 25, Color.WHITE);
    }

    public static void talk(Rectangle rect, String message, Color color, int duration) {
        Vector2 centerString = MathUtils.centerString(message, MessageManager.SHOWG, rect);
        indicators.add(new Indicator(centerString.x, rect.y + rect.height + 10, message, color, duration));
    }

    public static void talk(Rectangle rect, String message, Color color) {
        talk(rect, message, color, Time.SECONDE);
    }

    public static void addIndicator(float x, float y, String message) {
        indicators.add(new Indicator(x, y, message));
    }

    public static void addIndicator(float x, float y, String message, Color color) {
        indicators.add(new Indicator(x, y, message, color));
    }

    public static void addIndicator(float x, float y, String message, Color color, int count) {
        indicators.add(new Indicator(x, y, message, color, count));
    }

    public static void addMessage(float x, float y, String message, Color color, int time) {
        messages.add(new Message(x, y, message, color, time));
    }

    public static void draw() {
        Tmap.spriteBatch.begin();
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
        Tmap.spriteBatch.flush();
        Tmap.spriteBatch.end();
    }

    private static ArrayList<Indicator> getIndicators() {
        return (ArrayList<Indicator>) indicators.clone();
    }

    private static ArrayList<Message> getMessages() {
        return (ArrayList<Message>) messages.clone();
    }

}
