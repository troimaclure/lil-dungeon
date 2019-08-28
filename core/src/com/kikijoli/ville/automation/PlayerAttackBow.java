/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.automation;

import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.abstracts.AbstractAction;
import com.kikijoli.ville.drawable.entite.Bullet.Arrow;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.drawable.entite.simple.Bow;
import com.kikijoli.ville.manager.BulletManager;
import com.kikijoli.ville.manager.DrawManager;
import com.kikijoli.ville.manager.EntiteManager;
import com.kikijoli.ville.util.MathUtils;

/**
 *
 * @author Arthur
 */
public abstract class PlayerAttackBow extends AbstractAction {

	public int count = 0;
	public int countArrow = 50;
	public int delayArrow = 50;
	public int delay = 30;
	Bow bow;
	Entite entite;

	public PlayerAttackBow(Entite entite) {
		this.entite = entite;
	}

	@Override
	public void act() {
		if (EntiteManager.entiteSelected == null) {
			end();
			return;
		}
		addBowIfNotExist();
		bow.setX((float) (entite.getX() - (entite.getWidth() * 1.5)));
		bow.setY(entite.getY() - entite.getHeight() / 2);
		bow.setRotation(90 + MathUtils.getRotation(entite.getX(), entite.getY(), EntiteManager.entiteSelected.getX(), EntiteManager.entiteSelected.getY()));
		if (countArrow++ >= delayArrow) shoot();
		if (count++ > delay) end();
	}

	private void addBowIfNotExist() {
		if (bow != null) return;
		bow = new Bow((int) (entite.getX()), (int) (entite.getY()));
		DrawManager.sprites.add(bow);
		EntiteManager.attack(entite);
	}

	public abstract void onFinish();

	private void end() {
		if (bow != null)
			DrawManager.sprites.remove(bow);
		onFinish();
	}

	private void shoot() {
		countArrow = 0;
		Vector2 center = new Vector2();
		bow.getBoundingRectangle().getCenter(center);
		Arrow arrow = new Arrow((int) center.x, (int) center.y, new Vector2(EntiteManager.entiteSelected.getX(), EntiteManager.entiteSelected.getY()), entite);
		arrow.setRotation(90 + MathUtils.getRotation(entite.getX(), entite.getY(), EntiteManager.entiteSelected.getX(), EntiteManager.entiteSelected.getY()));
		BulletManager.bullets.add(arrow);
	}

}
