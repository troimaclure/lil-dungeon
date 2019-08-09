/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.util;

import com.badlogic.gdx.graphics.Texture;
import java.util.HashMap;

/**
 *
 * @author troïmaclure
 */
public class TextureUtil {

    public static HashMap<String, Texture> textures = new HashMap<String, Texture>();

    public static Texture getTexture(String path) {
        if (!textures.containsKey(path)) {
            Texture t = new Texture(path);
            t.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            textures.put(path, t);
        }
        return textures.get(path);
    }
}
