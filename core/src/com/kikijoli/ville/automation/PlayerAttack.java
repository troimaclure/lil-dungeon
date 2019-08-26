/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.automation;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.abstracts.AbstractAction;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.drawable.entite.simple.Bow;
import com.kikijoli.ville.manager.DrawManager;
import com.kikijoli.ville.manager.EntiteManager;

/**
 *
 * @author Arthur
 */
public abstract class PlayerAttack extends AbstractAction {

    public int count = 0;
    public int delay = 200;
    Bow bow;
    Entite entite;

    public PlayerAttack(Entite entite) {
        this.entite = entite;
    }

    @Override
    public void act() {
        if (EntiteManager.entiteSelected == null) return;
        addBow();

        bow.setX((float) (entite.getX() - (entite.getWidth() * 1.5)));
        bow.setY(entite.getY() - entite.getHeight() / 2);
        double degrees = Math.atan2(
                entite.getY() - EntiteManager.entiteSelected.getY(),
                entite.getX() - EntiteManager.entiteSelected.getX()
        ) * 180.0d / Math.PI;
        bow.setRotation(90 + (float) degrees);
        if (count++ > delay) end();
    }

    private void addBow() {
        if (bow != null) return;
        bow = new Bow(
                (int) (entite.getX()),
                (int) (entite.getY())
        );

        DrawManager.sprites.add(bow);
        EntiteManager.attack(entite);
    }

    public abstract void onFinish();

    private void end() {
        DrawManager.sprites.remove(bow);
        onFinish();
    }

}
