/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.manager;

import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.pathfind.GridManager;
import com.kikijoli.ville.pathfind.PathFinder;
import com.kikijoli.ville.pathfind.Tile;
import java.util.ArrayList;

/**
 *
 * @author troïmaclure
 */
public class PathFinderManager {

    public static ArrayList<Tile> getPath(Entite source, Entite target, String filters) {
        return new PathFinder().getPathFor(GridManager.getCaseFor(source.getBoundingRectangle(), filters), GridManager.getCaseFor(target.getBoundingRectangle(), filters), filters);
    }

}
