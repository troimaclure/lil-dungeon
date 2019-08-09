/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.automation;

import com.kikijoli.ville.abstracts.AbstractAction;
import com.kikijoli.ville.drawable.entite.npc.Guard;
import com.kikijoli.ville.manager.EntiteManager;
import com.kikijoli.ville.manager.PathFinderManager;
import com.kikijoli.ville.pathfind.Tile;
import com.kikijoli.ville.util.Constantes;
import java.util.ArrayList;

/**
 *
 * @author Arthur
 */
public class AttackPlayer extends AbstractAction {

    Guard guard;

    public AttackPlayer(Guard guard) {
        this.guard = guard;
    }

    @Override
    public void act() {
        ArrayList<Tile> path = PathFinderManager.getPath(guard, EntiteManager.player.getBoundingRectangle(), Constantes.WALL);
//        if (path != null)
//            System.out.println(path.size());
    }

}
