/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.shader;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.kikijoli.ville.drawable.entite.Entite;

/**
 *
 * @author troïmaclure
 */
public abstract class AbstractShader extends ShaderProgram {

    public Entite entite;
    public boolean global;

    public AbstractShader(FileHandle vertexShader, FileHandle fragmentShader) {
        super(vertexShader, fragmentShader);
        this.global = true;
    }

    public AbstractShader(FileHandle vertexShader, FileHandle fragmentShader, Entite entite) {
        super(vertexShader, fragmentShader);
        this.entite = entite;
    }

    public abstract void step(float rawTime);

}
