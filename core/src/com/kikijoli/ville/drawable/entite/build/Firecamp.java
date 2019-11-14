package com.kikijoli.ville.drawable.entite.build;

import box2dLight.PointLight;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.manager.CheckpointManager;
import com.kikijoli.ville.manager.ParticleManager;
import com.kikijoli.ville.maps.Tmap;
import com.kikijoli.ville.util.Constantes;
import com.kikijoli.ville.util.Gamestate;

/**
 *
 * @author ajosse
 */
public class Firecamp extends Entite {

    public boolean touch;
    public Gamestate gameState;

    public Firecamp(float srcX, float srcY) {
        super("sprite/firecamp.png", srcX, srcY, Constantes.TILESIZE, Constantes.TILESIZE);
    }

    public void touch() {
        if (!touch) {
            touch = true;
            Vector2 center = getCenter();
            ParticleManager.addParticle("particle/firecamp.p", center.x, center.y, 1.0f);
            new PointLight(Tmap.getRay(), 50, Color.ORANGE, 100, center.x, center.y);
            CheckpointManager.currentCheckpoint = this;
            gameState = new Gamestate();
            gameState.write();
        }
    }
}
