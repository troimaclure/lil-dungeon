package com.kikijoli.ville.pathfind;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kikijoli.ville.drawable.entite.Entite;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author troïmaclure
 */
public class Tile extends Entite {

    public int row, col;
    public String state = "0";

    public Tile(int row, int col, int x, int y, int width, int height) {
        super("sprite/floor.png", x, y, width, height);
        this.row = row;
        this.col = col;
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }

}
