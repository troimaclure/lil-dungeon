package com.kikijoli.ville.drawable.entite.Bullet;

import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.util.MathUtils;

/**
 *
 * @author ajosse
 */
public abstract class Bullet extends Entite {

	public Vector2 destination;
	public int scope;
	public int distance = 0;
	public Entite author;

	private Vector2 vel;

	public Bullet(String path, int srcX, int srcY, int srcWidth, int srcHeight, Vector2 destination, Entite author) {
		super(path, srcX, srcY - 15, srcWidth, srcHeight);
		this.destination = destination;
		this.destination.y -= 20;
		this.author = author;
	}

	public void move() {
		if (vel == null) {
			vel = MathUtils.destination(this.destination, new Vector2(this.getX(), this.getY()));
		}
		this.setX(this.getX() + vel.x * this.speed);
		this.setY(this.getY() + vel.y * this.speed);
		this.distance += this.speed;

	}

}
