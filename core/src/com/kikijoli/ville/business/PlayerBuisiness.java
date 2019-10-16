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
import com.kikijoli.ville.automation.player.AttackWandFire;
import com.kikijoli.ville.automation.player.AttackWandPoison;
import com.kikijoli.ville.drawable.hud.WandFireTile;
import com.kikijoli.ville.drawable.hud.WandTile;
import com.kikijoli.ville.manager.EntiteManager;
import com.kikijoli.ville.manager.HudManager;
import com.kikijoli.ville.manager.SoundManager;
import com.kikijoli.ville.maps.Tmap;
import com.kikijoli.ville.util.Mode;

/**
 *
 * @author Arthur
 */
public class PlayerBuisiness extends AbstractBusiness {

    public static final String DASH = "Dash";
    public int touchableCount;
    public int touchabledelay = 60 * 2;

    public PlayerBuisiness() {
    }

    @Override
    public void act() {
        super.act();
        if (EntiteManager.player.touched) {
            touchableCount++;
            if (touchableCount++ >= touchabledelay) {
                EntiteManager.player.touched = false;
                touchableCount = 0;
            }
        }
    }

    @Override
    public AbstractAction getDefault() {
        return new None();
    }

    public void dash() {
        if (actions.containsKey(DASH) || !EntiteManager.player.canDash())
            return;
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

        AbstractAction abstractAction = null;
        switch (EntiteManager.player.mode) {
            case Mode.BOW:
                if (actions.containsKey(Integer.toString(Mode.BOW)) || EntiteManager.arrowCount == 0)
                    return;
                SoundManager.playSound(SoundManager.BOW);
                EntiteManager.arrowCount -= 1;
                abstractAction = new AttackBow(EntiteManager.player, new Vector2(Tmap.worldCoordinates.x, Tmap.worldCoordinates.y)) {
                    @Override
                    public void onFinish() {
                        actions.remove(Integer.toString(Mode.BOW));
                    }
                };
                actions.put(Integer.toString(Mode.BOW), abstractAction);
                break;
            case Mode.SWORD:
                if (actions.containsKey(Integer.toString(Mode.SWORD)) || !EntiteManager.player.canDash())
                    return;
                dash();
                SoundManager.playSound(SoundManager.SWORD);
                abstractAction = new AttackSword(EntiteManager.player) {
                    @Override
                    public void onFinish() {
                        actions.remove(Integer.toString(Mode.SWORD));
                    }
                };
                actions.put(Integer.toString(Mode.SWORD), abstractAction);
                break;
            case Mode.WAND:
                if (actions.containsKey(Integer.toString(Mode.WAND))) return;
                com.kikijoli.ville.drawable.hud.Tile t = HudManager.tiles.stream().filter(e -> e instanceof WandTile).findFirst().get();
                t.disabled = true;
                SoundManager.playSound(SoundManager.POISON_SPELL);
                abstractAction = new AttackWandPoison(EntiteManager.player, new Vector2(Tmap.worldCoordinates.x, Tmap.worldCoordinates.y)) {
                    @Override
                    public void onFinish() {
                        actions.remove(Integer.toString(Mode.WAND));
                        t.disabled = false;
                    }

                    @Override
                    public void step(int step) {
                        t.count = step;
                    }
                };
                actions.put(Integer.toString(Mode.WAND), abstractAction);
                break;
            case Mode.WANDFIRE:
                if (actions.containsKey(Integer.toString(Mode.WANDFIRE)))
                    return;
                t = HudManager.tiles.stream().filter(e -> e instanceof WandFireTile).findFirst().get();
                t.disabled = true;
                SoundManager.playSound(SoundManager.FIRE_SPELL);
                abstractAction = new AttackWandFire(EntiteManager.player, new Vector2(Tmap.worldCoordinates.x, Tmap.worldCoordinates.y)) {
                    @Override
                    public void onFinish() {
                        actions.remove(Integer.toString(Mode.WANDFIRE));
                        t.disabled = false;
                    }

                    @Override
                    public void step(int step) {
                        t.count = step;
                    }
                };
                actions.put(Integer.toString(Mode.WANDFIRE), abstractAction);
                break;
        }

    }

}
