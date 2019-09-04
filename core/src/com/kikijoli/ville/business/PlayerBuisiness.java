/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.business;

import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.abstracts.AbstractAction;
import com.kikijoli.ville.automation.AttackBow;
import com.kikijoli.ville.automation.Dash;
import com.kikijoli.ville.automation.None;
import com.kikijoli.ville.automation.AttackSword;
import com.kikijoli.ville.automation.AttackWandPoison;
import com.kikijoli.ville.manager.EntiteManager;
import com.kikijoli.ville.maps.Tmap;
import com.kikijoli.ville.util.Mode;

/**
 *
 * @author Arthur
 */
public class PlayerBuisiness extends AbstractBusiness {

	private static final String DASH = "Dash";
	private static final String ATTACK = "Attack";

	public PlayerBuisiness() {
	}

	@Override
	public AbstractAction getDefault() {
		return new None();
	}

	public void dash() {
		if (actions.containsKey(DASH)) return;
		actions.put(DASH, new Dash(EntiteManager.player) {

			@Override
			public void onFinish() {
				actions.remove(DASH);
			}
		});
	}

	public void attack() {
		if (actions.containsKey(ATTACK)) return;
		AbstractAction abstractAction = null;
		switch (EntiteManager.player.mode) {
			case Mode.BOW:
				abstractAction = new AttackBow(EntiteManager.player, new Vector2(Tmap.worldCoordinates.x, Tmap.worldCoordinates.y)) {
					@Override
					public void onFinish() {
						actions.remove(ATTACK);
					}
				};
				break;
			case Mode.SWORD:
				dash();
				abstractAction = new AttackSword(EntiteManager.player) {
					@Override
					public void onFinish() {
						actions.remove(ATTACK);
					}
				};
				break;
			case Mode.WAND:
				abstractAction = new AttackWandPoison(EntiteManager.player, new Vector2(Tmap.worldCoordinates.x, Tmap.worldCoordinates.y)) {
					@Override
					public void onFinish() {
						actions.remove(ATTACK);
					}
				};
				break;
		}

		actions.put(ATTACK, abstractAction);
	}

}
