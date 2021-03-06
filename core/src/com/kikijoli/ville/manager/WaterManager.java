/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.manager;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.drawable.entite.decor.Water;
import com.kikijoli.ville.maps.Tmap;
import static com.kikijoli.ville.maps.Tmap.spriteBatch;
import java.util.ArrayList;

/**
 *
 * @author troïmaclure
 */
public class WaterManager {

    public static ArrayList<Water> waters = new ArrayList<>();

    public static void tour() {
        drawWater();
    }

    public static void drawWater() {

        spriteBatch.begin();

        getWaters().forEach((entite) -> {
            ShaderProgram shader = Tmap.spriteBatch.getShader();
            if (entite.shader != null) {
                Tmap.spriteBatch.setShader(entite.shader);
            }
            entite.draw(Tmap.spriteBatch);
            Tmap.spriteBatch.setShader(shader);
        });
        spriteBatch.end();

    }

    private static void drawDottedLine(ShapeRenderer shapeRenderer, int dotDist, float x1, float y1, float x2, float y2) {

        Vector2 vec2 = new Vector2(x2, y2).sub(new Vector2(x1, y1));
        float length = vec2.len();
        for (int i = 0; i < length; i += dotDist) {
            vec2.clamp(length - i, length - i);
            shapeRenderer.circle(x1 + vec2.x, y1 + vec2.y, 5);
        }

    }

    static void addWater(Water water) {
        waters.add(water);
    }

    public static ArrayList<Water> getWaters() {
        return (ArrayList<Water>) waters.clone();
    }

}
