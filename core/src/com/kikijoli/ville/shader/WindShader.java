/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.shader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.kikijoli.ville.drawable.entite.Entite;

/**
 *
 * @author troïmaclure
 */
public class WindShader extends AbstractShader {

    public float amplitudeWave = 3.075f;
    public float angleWave = 0.0f;
    public float angleWaveSpeed = 1f;
    public final float PI2 = 3.1415926535897932384626433832795f * 2.0f;

    public WindShader() {
        super(new FileHandle("glsl/wind/vertex.glsl"), new FileHandle("glsl/wind/frag.glsl"));
    }

    public WindShader(Entite entite) {
        super(new FileHandle("glsl/wind/vertex.glsl"), new FileHandle("glsl/wind/frag.glsl"), entite);

    }

    @Override
    public void step() {
        final float dt = Gdx.graphics.getRawDeltaTime();
        angleWave += dt * angleWaveSpeed;
        while (angleWave > PI2) {
            angleWave -= PI2;
        }
        this.begin();
        this.setUniformf("waveData", angleWave, amplitudeWave);
        this.end();
    }

}
