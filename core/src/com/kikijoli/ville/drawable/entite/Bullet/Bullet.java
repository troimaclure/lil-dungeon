package com.kikijoli.ville.drawable.entite.Bullet;

import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.drawable.entite.Entite;

/**
 *
 * @author ajosse
 */
public abstract class Bullet extends Entite {

    public Vector2 destination;
    public int scope;
    public int distance = 0;

    public Bullet(String path, int srcX, int srcY, int srcWidth, int srcHeight, Vector2 destination) {
        super(path, srcX, srcY, srcWidth, srcHeight);
        this.destination = destination;
    }

}
