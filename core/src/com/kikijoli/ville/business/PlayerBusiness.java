/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.business;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.abstracts.AbstractAction;
import com.kikijoli.ville.automation.player.AttackBow;
import com.kikijoli.ville.automation.player.Dash;
import com.kikijoli.ville.automation.common.None;
import com.kikijoli.ville.automation.player.AttackSword;
import com.kikijoli.ville.automation.player.VanishAction;
import com.kikijoli.ville.automation.player.ThrowPebble;
import com.kikijoli.ville.component.BowComponent;
import com.kikijoli.ville.component.VanishComponent;
import com.kikijoli.ville.component.PebbleComponent;
import com.kikijoli.ville.component.SwordComponent;
import com.kikijoli.ville.manager.EntiteManager;
import com.kikijoli.ville.manager.SoundManager;
import com.kikijoli.ville.maps.Tmap;
import com.kikijoli.ville.util.Count;

/**
 *
 * @author Arthur
 */
public class PlayerBusiness extends AbstractBusiness {

    public static final String DASH = "Dash";
    Count touchable = new Count(0, 60 * 2);

    public PlayerBusiness() {
    }

    @Override
    public void act() {
        super.act();
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
                bow();
                break;
            case "SwordComponent":
                sword();
                break;
            case "PebbleComponent":
                pebble();
                break;
            case "VanishComponent":
                vanish();
                break;
        }

    }

    private void bow() {
        if (EntiteManager.arrowCount == 0) {
            EntiteManager.player.talk("Arrow needed", Color.WHITE);
            return;
        }
        if (actions.containsKey(BowComponent.class.getSimpleName())) {
            return;
        }
        SoundManager.playSound(SoundManager.BOW);
        EntiteManager.arrowCount -= 1;
        actions.put(BowComponent.class.getSimpleName(), new AttackBow(EntiteManager.player, new Vector2(Tmap.worldCoordinates.x, Tmap.worldCoordinates.y)) {
            @Override
            public void onFinish() {
                actions.remove(BowComponent.class.getSimpleName());
            }
        });
    }

    private void sword() {

        if (actions.containsKey(SwordComponent.class.getSimpleName())) {
            return;
        }
        SoundManager.playSound(SoundManager.SWORD);
        actions.put(SwordComponent.class.getSimpleName(), new AttackSword(EntiteManager.player, () -> {
            actions.remove(SwordComponent.class.getSimpleName());
        }));
    }

    private void pebble() {
        if (EntiteManager.pebbleCount <= 0) {
            EntiteManager.player.talk("Pebble needed", Color.WHITE);
            return;
        }
        if (actions.containsKey(PebbleComponent.class.getSimpleName())) {
            return;
        }
        SoundManager.playSound(SoundManager.PEBBLE);
        EntiteManager.pebbleCount -= 1;
        actions.put(PebbleComponent.class.getSimpleName(), new ThrowPebble(EntiteManager.player, new Vector2(Tmap.worldCoordinates.x, Tmap.worldCoordinates.y)) {
            @Override
            public void onFinish() {
                actions.remove(PebbleComponent.class.getSimpleName());
            }
        });
    }

    private void vanish() {
        if (EntiteManager.vanishCount <= 0) {
            EntiteManager.player.talk("Can't vanish", Color.WHITE);
            return;
        }
        if (actions.containsKey(VanishComponent.class.getSimpleName()))
            return;

        SoundManager.playSound(SoundManager.PEBBLE);
        EntiteManager.vanishCount -= 1;
        actions.put(VanishComponent.class.getSimpleName(), new VanishAction(EntiteManager.player) {
            @Override
            public void onFinish() {
                actions.remove(VanishComponent.class.getSimpleName());
                EntiteManager.player.vanish = false;
                EntiteManager.player.visible = true;
            }
        });
    }

}
