package com.kikijoli.ville.pathfind;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;

/**
 *
 * @author admin
 */
public class PathFinder {

    private Search fin = null;
    private Tile target = null;
    private int compte = 0;
    private final ArrayList<Search> indexes = new ArrayList<>();
    private int incremental = 0;
    private final ArrayList<Search> good = new ArrayList<>();
    private Search debut = null;
    private String filters = "";

    /**
     *
     * @param source
     * @param target
     * @return
     */
    public ArrayList<Tile> getPathFor(Tile source, Tile target, String filters) {
        this.filters = filters;
        this.target = target;
        if (target == null || source == null) {
            return null;
        }
        if (filters.contains(target.state) || filters.contains(source.state)) {
            return null;
        }
        Search[][] searchs = new Search[GridManager.COLUMNCOUNT][GridManager.ROWCOUNT];
        Tile[][] grille = GridManager.grid;

        debut = null;
        for (Tile[] grille1 : grille) {
            for (Tile g : grille1) {
                searchs[g.col][g.row] = new Search(g, 1000);
                if (source.col == g.col && source.row == g.row) {
                    debut = searchs[g.col][g.row];
                    debut.index = 0;
                }
            }
        }
        if (debut == null) {
            return null;
        }
        search(debut, searchs);
        if (fin == null) {

            return null;
        }
        constructPath(fin, searchs);

        while (incremental < GridManager.COLUMNCOUNT * GridManager.ROWCOUNT) {
            for (Search indexe : indexes) {
                if (indexe.index == 0) {
                    return getGoodPath(searchs, indexes);
                }
            }
            for (Search[] search : searchs) {
                for (Search search1 : search) {
                    if (search1.close) {
                        search1.index += 1;
                        search1.close = false;
                    }
                }
            }

            indexes.clear();
            incremental++;

            constructPath(fin, searchs);

        }

        return null;
    }

    /**
     *
     * @param current
     * @param searchs
     * @return
     */
    private ArrayList<Search> constructPath(Search current, Search[][] searchs) {

        current.close = true;
        indexes.add(current);
        ArrayList<Search> child = new ArrayList<>();
        getNext(current, searchs, child);
        child.stream().forEach((child1) -> {
            constructPath(child1, searchs);
        });

        return child;
    }

    /**
     *
     * @param current
     * @param searchs
     * @param childs
     * @return
     */
    private void getNext(Search current, Search[][] searchs, ArrayList<Search> childs) {
        if (current.c.col + 1 < GridManager.COLUMNCOUNT) {
            if (searchs[current.c.col + 1][current.c.row].index == current.index - 1 && !searchs[current.c.col + 1][current.c.row].close) {
                childs.add(searchs[current.c.col + 1][current.c.row]);
            }
        }
        if (current.c.col - 1 >= 0) {
            if (searchs[current.c.col - 1][current.c.row].index == current.index - 1 && !searchs[current.c.col - 1][current.c.row].close) {
                childs.add(searchs[current.c.col - 1][current.c.row]);
            }
        }
        if (current.c.row + 1 < GridManager.ROWCOUNT) {
            if (searchs[current.c.col][current.c.row + 1].index == current.index - 1 && !searchs[current.c.col][current.c.row + 1].close) {
                childs.add(searchs[current.c.col][current.c.row + 1]);
            }
        }

        if (current.c.row - 1 >= 0) {
            if (searchs[current.c.col][current.c.row - 1].index == current.index - 1 && !searchs[current.c.col][current.c.row - 1].close) {
                childs.add(searchs[current.c.col][current.c.row - 1]);
            }
        }
    }
//    private Index LookFor(int index, Search current) {
//
//    }

    /**
     *
     * @param current
     * @param grille
     */
    private void search(Search current, Search[][] grille) {

        //au dessus
        if (current.c.row + compte < GridManager.ROWCOUNT) {
            if (!filters.contains(grille[current.c.col][current.c.row + compte].c.state)) {
                grille[current.c.col][current.c.row + compte].index = compte;
            }
            complete(current.c.row + compte, current.c.col, compte, grille);
        }
        //ne dessous
        if (current.c.row - compte >= 0) {
            if (!filters.contains(grille[current.c.col][current.c.row - compte].c.state)) {
                grille[current.c.col][current.c.row - compte].index = compte;
            }
            complete(current.c.row - compte, current.c.col, compte, grille);
        }
        if (compte < GridManager.COLUMNCOUNT) {
            compte += 1;
            search(current, grille);
        }
    }

    /**
     *
     * @param y
     * @param x
     * @param index
     * @param grille
     */
    private void complete(int y, int x, int index, Search[][] grille) {
        int number = 1;
        int increm = index;
        if (target.col == x && target.row == y) {
            fin = grille[x][y];
        }
        while (x + number < GridManager.COLUMNCOUNT) {
            ++increm;
            if (!filters.contains(grille[x + number][y].c.state)) {
                grille[x + number][y].index = increm;
            }

            if (target.col == (x + number) && target.row == y) {
                fin = grille[x + number][y];
            }
            number++;

        }
        number = 1;
        increm = index;
        while (x - number >= 0) {

            ++increm;
            if (!filters.contains(grille[x - number][y].c.state)) {
                grille[x - number][y].index = increm;
            }
            if (target.col == (x - number) && target.row == y) {
                fin = grille[x - number][y];
            }
            number++;

        }
    }

    private ArrayList<Tile> getGoodPath(Search[][] searchs, ArrayList<Search> indexes) {
        good.add(debut);
        Search current = debut;
        boolean finOk = false;
        while (!finOk) {
            if (current != null) {
                current = getNextGood(current, searchs, indexes);
                good.add(current);
                if (current == fin) {
                    finOk = true;
                }
            } else {
//                print(searchs, indexes);
//                print(searchs, good);
                return new ArrayList<>();
            }
        }
        ArrayList<Tile> tiles = new ArrayList<>();

        good.stream().forEach((g) -> {
            tiles.add(g.c);
        });
        return tiles;
    }

    private Search getNextGood(Search current, Search[][] searchs, ArrayList<Search> indexes) {
        if (current.c.col + 1 < GridManager.COLUMNCOUNT) {
            if (searchs[current.c.col + 1][current.c.row].index == current.index + 1 && indexes.contains(searchs[current.c.col + 1][current.c.row])) {
                return (searchs[current.c.col + 1][current.c.row]);
            }
        }
        if (current.c.col - 1 >= 0) {
            if (searchs[current.c.col - 1][current.c.row].index == current.index + 1 && indexes.contains(searchs[current.c.col - 1][current.c.row])) {
                return (searchs[current.c.col - 1][current.c.row]);
            }
        }
        if (current.c.row + 1 < GridManager.ROWCOUNT) {
            if (searchs[current.c.col][current.c.row + 1].index == current.index + 1 && indexes.contains(searchs[current.c.col][current.c.row + 1])) {
                return (searchs[current.c.col][current.c.row + 1]);
            }
        }
        if (current.c.row - 1 >= 0) {
            if (searchs[current.c.col][current.c.row - 1].index == current.index + 1 && indexes.contains(searchs[current.c.col][current.c.row - 1])) {
                return (searchs[current.c.col][current.c.row - 1]);
            }
        }
        return null;
    }

    private class Search {

        public Tile c;
        public int index;
        public boolean close = false;

        /**
         *
         * @param c
         * @param index
         */
        public Search(Tile c, int index) {
            this.c = c;
            this.index = index;
        }

    }

}
