/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.component;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kikijoli.ville.drawable.entite.Entite;

/**
 *
 * @author ajosseau
 */
public class VanishComponent implements IComponent {

    public Entite entite;

    public VanishComponent(Entite entite) {
        this.entite = entite;
    }

    @Override
    public void draw(SpriteBatch batch) {

    }
}
