/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.business;

import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.abstracts.AbstractAction;
import com.kikijoli.ville.automation.player.AttackBow;
import com.kikijoli.ville.automation.player.Dash;
import com.kikijoli.ville.automation.common.None;
import com.kikijoli.ville.automation.player.AttackSword;
import com.kikijoli.ville.component.BowComponent;
import com.kikijoli.ville.component.SwordComponent;
import com.kikijoli.ville.manager.EntiteManager;
import com.kikijoli.ville.manager.SoundManager;
import com.kikijoli.ville.maps.Tmap;
import com.kikijoli.ville.util.Count;

/**
 *
 * @author Arthur
 */
public class PlayerBuisiness extends AbstractBusiness {

    public static final String DASH = "Dash";
    Count touchable = new Count(0, 60 * 2);

    public PlayerBuisiness() {
    }

    @Override
    public void act() {
        super.act();
        if (EntiteManager.player.touched) {
            if (touchable.stepAndComplete()) {
                EntiteManager.player.touched = false;
            }
        }
    }

    @Override
    public AbstractAction getDefault() {
        return new None();
    }

    public void dash() {
        if (actions.containsKey(DASH) || !EntiteManager.player.canDash()) {
            return;
        }
        EntiteManager.player.dash();
        SoundManager.playSound(SoundManager.DASH);
        actions.put(DASH, new Dash(EntiteManager.player) {
            @Override
            public void onFinish() {
                actions.remove(DASH);
            }
        });
    }

    public void attack() {

        switch (EntiteManager.player.currentComponent.getClass().getSimpleName()) {
            case "BowComponent":
                if (bow()) return;
            case "SwordComponent":
                if (sword()) return;
        }

    }

    private boolean bow() {
        if (actions.containsKey(BowComponent.class.getSimpleName()) || EntiteManager.arrowCount == 0) {
            return true;
        }
        SoundManager.playSound(SoundManager.BOW);
        EntiteManager.arrowCount -= 1;
        actions.put(BowComponent.class.getSimpleName(), new AttackBow(EntiteManager.player, new Vector2(Tmap.worldCoordinates.x, Tmap.worldCoordinates.y)) {
            @Override
            public void onFinish() {
                actions.remove(BowComponent.class.getSimpleName());
            }
        });
        return false;
    }

    private boolean sword() {

        if (actions.containsKey(SwordComponent.class.getSimpleName())) {
            return true;
        }
        SoundManager.playSound(SoundManager.SWORD);
        actions.put(SwordComponent.class.getSimpleName(), new AttackSword(EntiteManager.player) {
            @Override
            public void onFinish() {
                actions.remove(SwordComponent.class.getSimpleName());
            }
        });
        return false;
    }

}
