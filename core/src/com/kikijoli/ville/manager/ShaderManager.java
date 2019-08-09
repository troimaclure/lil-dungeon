/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.kikijoli.ville.shader.AbstractShader;
import com.kikijoli.ville.shader.WaveShader;
import java.util.HashMap;

/**
 *
 * @author troïmaclure
 */
public class ShaderManager {

    public static AbstractShader waveShader = new WaveShader();
//    public static AbstractShader windShader = new WindShader();

    public static void step() {
        final float dt = Gdx.graphics.getRawDeltaTime();
        EntiteManager.getEntites().stream().filter((entite) -> (entite.shader != null && !entite.shader.global)).forEach((entite) -> {
            entite.shader.step(dt);
        });
        //shared global shader step
        waveShader.step(dt);
//        windShader.step(dt);
    }

    public static ShaderProgram defaultShader = SpriteBatch.createDefaultShader();
    public static HashMap<String, FileHandle> shadersFiles = new HashMap<String, FileHandle>();

    public static ShaderProgram getDefault() {
        return defaultShader;
    }

}
