/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.business;

import com.kikijoli.ville.abstracts.AbstractAction;
import com.kikijoli.ville.automation.PlayerAttackBow;
import com.kikijoli.ville.automation.Dash;
import com.kikijoli.ville.automation.None;
import com.kikijoli.ville.manager.EntiteManager;

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
		putAction(DASH, new Dash(EntiteManager.player) {

			@Override
			public void onFinish() {
				System.out.println("remove dash");
				actions.remove(DASH);
			}
		});
	}

	public void attack() {
		putAction(ATTACK, new PlayerAttackBow(EntiteManager.player) {
			@Override
			public void onFinish() {
				actions.remove(ATTACK);
			}
		});
	}

	private void putAction(String name, AbstractAction a) {
		if (!actions.containsKey(name)) {
			System.out.println("push " + name);
			actions.put(name, a);
		}

	}

}
