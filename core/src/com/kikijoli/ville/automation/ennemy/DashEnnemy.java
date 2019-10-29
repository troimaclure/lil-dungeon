/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.automation.ennemy;

import com.badlogic.gdx.math.Rectangle;
import com.kikijoli.ville.automation.player.Dash;
import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.manager.StageManager;
import com.kikijoli.ville.util.MathUtils;
import com.kikijoli.ville.util.Move;

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
        Vector2 goal = new Vector2(entite.getX() + vel.x * entite.speed, entite.getY() + vel.y * entite.speed);
        Rectangle r = new Rectangle(goal.x, goal.y, entite.getWidth(), entite.getHeight());
        if (!StageManager.isClearZone(r , Move.NPC_MOVE_FILTER)) return;
        entite.setX(entite.getX() + vel.x * entite.speed);
        entite.setY(entite.getY() + vel.y * entite.speed);

    }

}
