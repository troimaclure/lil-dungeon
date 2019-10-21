/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.component;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.drawable.entite.simple.Sword;

/**
 *
 * @author ajosseau
 */
public class SwordComponent implements IComponent {

    public Sword sword = new Sword(0, 0);
    public Entite entite;

    public SwordComponent(Entite entite) {
        this.entite = entite;
    }

    @Override
    public void handle() {

    }

    @Override
    public void draw(SpriteBatch batch) {
        sword.setX((float) (this.entite.getX() - (sword.getWidth() / 2 - this.entite.getWidth() / 2)));
        sword.setY(this.entite.getY() - this.entite.getHeight() / 2);
        sword.draw(batch);
    }

}
