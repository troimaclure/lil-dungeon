/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.shader;

import com.badlogic.gdx.Gdx;

/**
 *
 * @author Arthur
 */
public class ShadowRender extends AbstractShader {

    public ShadowRender() {
        super(Gdx.files.internal("glsl/shadow/pass.vert"), Gdx.files.internal("glsl/shadow/shadowRender.frag"));
    }

    @Override
    public void step() {

    }

}
