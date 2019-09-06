/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.business;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.abstracts.AbstractAction;
import com.kikijoli.ville.automation.AttackBow;
import com.kikijoli.ville.automation.DashEnnemy;
import com.kikijoli.ville.automation.GoTo;
import com.kikijoli.ville.automation.AttackSword;
import com.kikijoli.ville.drawable.entite.npc.Guard;
import com.kikijoli.ville.manager.EntiteManager;
import com.kikijoli.ville.shader.ClickShader;
import com.kikijoli.ville.shader.WalkShader;

/**
 *
 * @author Arthur
 */
public class GuardBuisiness extends AbstractBusiness {

	Guard guard;

	public GuardBuisiness(Guard guard) {
		this.guard = guard;
	}

	@Override
	public AbstractAction getDefault() {
		return new WaitPlayer();
	}

	private class AttackPlayer extends AbstractAction {

		private static final String GOTO = "GOTO";
		private static final String DASH = "DASH";
		private static final String BOW = "BOW";
		private static final String ATTACK = "ATTACK";

		int count = 50;
		int delay = 50;
		int dashDelay = 150;
		int countDash = 0;
		int bowDelay = 150;
		int countBow = 50;

		@Override
		public void act() {
			if (isContacted()) {
				actions.remove(GOTO);
				if (!(guard.shader instanceof ClickShader)) {
					guard.shader = null;
				}
				if (count++ >= delay) {
					guard.shader = new ClickShader(guard, null);
					EntiteManager.attack(guard);
					count = 0;
				}
			} else {
				handleWalk();
				handleDash();
				handleBow();
			}
		}

		private boolean isContacted() {
			return Intersector.overlaps(guard.anchor, EntiteManager.player.getBoundingRectangle());
		}

		private void handleWalk() {
			if (!actions.containsKey(GOTO) && !actions.containsKey(DASH))
				actions.put(GOTO, new GoTo(guard, EntiteManager.player));
			if (!(guard.shader instanceof WalkShader))
				guard.shader = new WalkShader(guard);
		}

		private void handleDash() {

			if (!actions.containsKey(DASH) && countDash++ > dashDelay && !isContacted()) {
				actions.remove(GOTO);
				countDash = 0;
				actions.put(DASH, new DashEnnemy(guard, new Vector2(EntiteManager.player.getX(), EntiteManager.player.getY())) {
					@Override
					public void onFinish() {
					
						handleWalk();
						actions.remove(DASH);
					}
				});
				actions.put(ATTACK, new AttackSword(guard) {
					@Override
					public void onFinish() {
						actions.remove(ATTACK);
					}
				});
			}
		}

		private void handleBow() {
			if (!actions.containsKey(BOW) && countBow++ > bowDelay && !isContacted()) {
				countBow = 0;
				actions.put(BOW, new AttackBow(guard, new Vector2(EntiteManager.player.getX(), EntiteManager.player.getY())) {
					@Override
					public void onFinish() {
						actions.remove(BOW);
					}
				});
			}
		}

	}

	public class WaitPlayer extends AbstractAction {

		@Override
		public void act() {
			if (guard.vision.contains(EntiteManager.player.getX(), EntiteManager.player.getY())) {
				current = new AttackPlayer();
			}
		}

	}

}
