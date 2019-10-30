package com.kikijoli.ville.drawable.entite.projectile;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.util.MathUtils;
import java.util.List;

/**
 *
 * @author ajosse
 */
public abstract class Projectile extends Rectangle {

    public Vector2 destination;
    public int scope;
    public int distance = 0;
    public Entite author;
    public int speed = 1;

    private Vector2 vel;

    public Projectile(Vector2 destination, int scope, Entite author, float x, float y, float width, float height) {
        super(x, y, width, height);
        this.destination = destination;
        this.destination.y -= 20;
        this.scope = scope;
        this.author = author;
    }

    public void move() {
        if (vel == null) {
            vel = MathUtils.destination(this.destination, new Vector2(this.getX(), this.getY()));
        }
        this.setX(x + vel.x * this.speed);
        this.setY(y + vel.y * this.speed);
        this.distance += this.speed;

    }

    public abstract List<Rectangle> getMouvementFilter();
}
