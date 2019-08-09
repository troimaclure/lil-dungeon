/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.manager;

import com.badlogic.gdx.graphics.Color;

/**
 *
 * @author Arthur
 */
public class ColorManager {

    public static boolean mode = true;

    public static void toggle() {
        mode = !mode;
    }

    public static Color getTextureColor() {
        return mode ? Color.BLACK : Color.WHITE;
    }

    public static Color getBackgroundColor() {
        return mode ? Color.WHITE : Color.BLACK;
    }
}
