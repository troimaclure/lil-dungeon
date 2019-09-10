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
import com.kikijoli.ville.pathfind.GridManager;
import com.kikijoli.ville.pathfind.Tile;
import com.kikijoli.ville.shader.AbstractShader;
import com.kikijoli.ville.shader.WalkShader;
import com.kikijoli.ville.util.Constantes;
import java.util.ArrayList;
import net.dermetfan.gdx.math.MathUtils;

/**
 *
 * @author troïmaclure
 */
public class GoTo extends AbstractAction {

    private final Entite entite;
    private final Entite target;
    public static ArrayList<Tile> path = null;
    private Vector2 goal = new Vector2();
    private int index = 0;
    private int count = 50;
    private int delay = 50;
    private AbstractShader old;

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
        count++;

        if (count >= delay) {
            count = 0;
            index = 0;
            path = PathFinderManager.getPath(entite, target, Constantes.NPCFILTERBUILD);
        }
        return path != null;
    }

    private void getGoal() {
        if (index < path.size()) {
            goal = path.get(index).getBoundingRectangle().getCenter(goal);
        }
    }

    private void goToGoal() {

        Vector2 center = new Vector2();
        center = entite.getBoundingRectangle().getCenter(center);
        if (!GridManager.isClearZone(goal, Constantes.NPC_MOVEMENT_OK)) return;
        if (!com.badlogic.gdx.math.MathUtils.isEqual(entite.getX(), goal.x, 5)) entite.setX(entite.getX() + (goal.x < center.x ? (-entite.speed) : center.x == goal.x ? 0 : entite.speed));
        if (!com.badlogic.gdx.math.MathUtils.isEqual(entite.getY(), goal.y, 5)) entite.setY(entite.getY() + (goal.y < entite.getY() ? (-entite.speed) : entite.getY() == goal.y ? 0 : entite.speed));
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
