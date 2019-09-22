/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import java.util.HashMap;

/**
 *
 * @author Kikijoli
 */
public class FontManager {

    public static HashMap<String, BitmapFont> h = new HashMap<String, BitmapFont>();

    public static BitmapFont getFont(String name, int size, Color c) {
        String key = name + size + c.toString();
        if (!h.containsKey(key)) {
            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/" + name + ".TTF"));

            FreeTypeFontParameter type = new FreeTypeFontParameter();
            type.size = size;
            BitmapFont f = generator.generateFont(type);
            f.setColor(c);
            h.put(key, f);
            generator.dispose();
        }
        BitmapFont font = h.get(key);

        return font;
    }
}
