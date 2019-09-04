/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.automation;

import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.abstracts.AbstractAction;
import com.kikijoli.ville.drawable.entite.projectile.Bullet.Arrow;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.drawable.entite.projectile.Spell.PoisonSpell;
import com.kikijoli.ville.drawable.entite.simple.Wand;
import com.kikijoli.ville.manager.ProjectileManager;
import com.kikijoli.ville.manager.DrawManager;
import com.kikijoli.ville.util.MathUtils;

/**
 *
 * @author Arthur
 */
public abstract class AttackWandPoison extends AbstractAction {

	public int count = 0;
	public int countWand = 50;
	public int delaySpell = 50;
	public int delay = 30;

	Wand wand;
	Entite entite;
	Vector2 destination;

	public AttackWandPoison(Entite entite, Vector2 destination) {
		this.entite = entite;
		this.destination = destination;
	}

	@Override
	public void act() {

		addWandIfNotExist();
		wand.setX((float) (entite.getX() - (entite.getWidth() * 1.5)));
		wand.setY(entite.getY() - entite.getHeight() / 2);
		wand.setRotation(90 + MathUtils.getRotation(entite.getX(), entite.getY(), destination.x, destination.y));
		if (countWand++ >= delaySpell) shoot();
		if (count++ > delay) end();
	}

	private void addWandIfNotExist() {
		if (wand != null) return;
		wand = new Wand((int) (entite.getX()), (int) (entite.getY()));
		DrawManager.entites.add(wand);
	}

	public abstract void onFinish();

	private void end() {
		if (wand != null)
			DrawManager.entites.remove(wand);
		onFinish();
	}

	private void shoot() {
		countWand = 0;
		Vector2 center = MathUtils.getCenter(wand.getBoundingRectangle());
//		PoisonSpell arrow = new PoisonSpell((int) center.x, (int) center.y, new Vector2(destination.x, destination.y), entite);
		PoisonSpell poison = new PoisonSpell(new Vector2(destination.x, destination.y), entite, (int) center.x, (int) center.y);
		ProjectileManager.projectiles.add(poison);
	}

}
