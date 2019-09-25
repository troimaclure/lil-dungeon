/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.shader;

import com.badlogic.gdx.Gdx;

/**
 *
 * @author troïmaclure
 */
public class InvertColorShader extends AbstractShader {

    public InvertColorShader() {
        super(Gdx.files.internal("glsl/invert_color/vertex.glsl"), Gdx.files.internal("glsl/invert_color/frag.glsl"));
    }

    @Override
    public void step() {
    }

}
