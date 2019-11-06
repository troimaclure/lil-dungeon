/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.automation.common;

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.abstracts.AbstractAction;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.manager.PathFinderManager;
import com.kikijoli.ville.pathfind.Tile;
import com.kikijoli.ville.shader.AbstractShader;
import com.kikijoli.ville.util.Constantes;
import java.util.ArrayList;

/**
 *
 * @author troïmaclure
 */
public class GoTo extends AbstractAction {

    public static ArrayList<Tile> path = null;
    public static Vector2 goal = new Vector2();
    public static final String EMPTY = "0";

    private final Entite entite;
    private final Entite target;
    private int index = 0;
    private int count = 30;
    private final int delay = 50;
    private final AbstractShader old;

    public GoTo(Entite entite, Entite target) {
        this.entite = entite;
        this.target = target;
        old = entite.shader;
    }

    @Override
    public void act() {

        if (checkPath()) {
            getGoal();
            goToGoal();
            checkGoal();
        }
    }

    private boolean checkPath() {
        if (entite.getBoundingRectangle().overlaps(target.getBoundingRectangle())) {
            return false;
        }
        count++;
        if (count >= delay) {
            count = 0;
            index = 0;
            path = PathFinderManager.getPath(entite, target, EMPTY);
        }
        return path != null;
    }

    private void getGoal() {
        if (index < path.size()) {
            goal = new Vector2(path.get(index).getX(), path.get(index).getY());
        }
    }

    private void goToGoal() {
        Vector2 center = new Vector2();
        center = entite.getBoundingRectangle().getPosition(center);
        for (int i = 0; i < entite.speed; i++) {
            if (!com.badlogic.gdx.math.MathUtils.isEqual(entite.getX(), goal.x, 5))
                entite.setX((int) entite.getX() + (goal.x < center.x ? (-1) : 1));
            if (!com.badlogic.gdx.math.MathUtils.isEqual(entite.getY(), goal.y, 5))
                entite.setY((int) entite.getY() + (goal.y < entite.getY() ? (-1) : 1));
        }

        if (Intersector.overlaps(target.getBoundingRectangle(), entite.getBoundingRectangle())) {
            path = null;
        }
    }

    private void checkGoal() {
        if (entite.getBoundingRectangle().overlaps(new Rectangle(goal.x, goal.y, Constantes.TILESIZE, Constantes.TILESIZE))) {
            index++;
        }
    }

}
