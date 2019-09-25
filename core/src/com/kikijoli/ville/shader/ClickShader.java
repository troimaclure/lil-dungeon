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
public class ClickShader extends AbstractShader {

    public float amplitudeWave = 8.075f;
    public float angleWave = 360.0f;
    public float angleWaveSpeed = 50f;
    public final float PI2 = 3.1415926535897932384626433832795f * 2.0f;
    public AbstractShader onFinish;

    public ClickShader(Entite entite, AbstractShader onFinish) {
        super(Gdx.files.internal("glsl/beat/vertex.glsl"), Gdx.files.internal("glsl/beat/frag.glsl"), entite);
        this.onFinish = onFinish;
    }

    @Override
    public void step() {
        final float dt = Gdx.graphics.getRawDeltaTime();
        angleWave += dt * angleWaveSpeed;
        while (angleWave > PI2) {
            angleWave -= PI2;
        }
        amplitudeWave -= 0.5f;
        this.begin();
        this.setUniformf("waveData", angleWave, amplitudeWave);
        this.end();
        if (amplitudeWave < 1f) {
            this.dispose();
            entite.shader = onFinish;
        }
    }

}
