/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.automation;

import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.util.MathUtils;

/**
 *
 * @author Arthur
 */
public abstract class DashEnnemy extends Dash {

	Vector2 vel;

	public DashEnnemy(Entite entite, Vector2 destination) {
		super(entite);
		vel = MathUtils.destination(destination, new Vector2(entite.getX(), entite.getY()));
	}

	@Override
	public void act() {
		super.act();
		entite.setX(entite.getX() + vel.x * entite.speed);
		entite.setY(entite.getY() + vel.y * entite.speed);
	}

}
