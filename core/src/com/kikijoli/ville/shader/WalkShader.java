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
public class WalkShader extends AbstractShader {

    float y = 0;
    int count = 0, delay = 2;
    boolean incre = true;

    public WalkShader() {
        super(new FileHandle("glsl/walk/vertex.glsl"), new FileHandle("glsl/walk/frag.glsl"));
    }

    public WalkShader(Entite entite) {
        super(new FileHandle("glsl/walk/vertex.glsl"), new FileHandle("glsl/walk/frag.glsl"), entite);
    }

    @Override
    public void step() {
        count++;
        if (count < delay) {
            return;
        }
        count = 0;
        if (incre) {
            y += 1;
        } else {
            y -= 1;
        }
        if (y > 3) {
            incre = false;
        }
        if (y < 0) {
            incre = true;
        }

        this.begin();
        this.setUniformf("y", y);
        this.end();
    }
}
