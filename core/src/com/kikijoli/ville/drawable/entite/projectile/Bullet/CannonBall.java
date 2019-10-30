package com.kikijoli.ville.drawable.entite.projectile.Bullet;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.util.Constantes;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 *
 * @author ajosse
 */
public class CannonBall extends Bullet {

    public CannonBall(int srcX, int srcY, Vector2 destination, Entite author) {
        super("sprite/cannonball.png", destination, 2000, author, srcX, srcY, Constantes.TILESIZE / 2, Constantes.TILESIZE / 2);
        this.speed = 5;
    }

    @Override
    public boolean isTouching(Rectangle rectangle) {
        return this.overlaps(rectangle);
    }

    @Override
    public List<Rectangle> getMouvementFilter() {
        return new ArrayList<>();
    }
}
