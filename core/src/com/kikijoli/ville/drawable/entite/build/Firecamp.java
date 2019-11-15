package com.kikijoli.ville.drawable.entite.build;

import box2dLight.PointLight;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.kikijoli.ville.drawable.entite.Entite;
import com.kikijoli.ville.manager.CheckpointManager;
import com.kikijoli.ville.manager.ParticleManager;
import com.kikijoli.ville.maps.Tmap;
import com.kikijoli.ville.util.Constantes;
import com.kikijoli.ville.save.Gamestate;

/**
 *
 * @author ajosse
 */
public class Firecamp extends Entite {

    public boolean touch;
    public Gamestate gameState;
    private PointLight pointLight;
    private float distance = 100;
    private boolean up;
    private float distanceMax = 125;
    private float distanceMin = 75;

    public Firecamp(float srcX, float srcY) {
        super("sprite/firecamp.png", srcX, srcY, Constantes.TILESIZE, Constantes.TILESIZE);
    }

    public void touch() {
        if (!touch) {
            touch = true;
            Vector2 center = getCenter();
            ParticleManager.addParticle("particle/firecamp.p", center.x, center.y, 1.0f);
            CheckpointManager.currentCheckpoint = this;
            gameState = new Gamestate();
            gameState.write();
            addLight();
            this.talk("Checkpoint :)", Color.CYAN);
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch); //To change body of generated methods, choose Tools | Templates.
        if (pointLight != null) {
            if (distance == distanceMax) up = false;
            if (distance == distanceMin) up = true;
            this.pointLight.setDistance(up ? distance++ : distance--);
        }
    }

    public void addLight() {
        Vector2 center = getCenter();
        pointLight = new PointLight(Tmap.getRay(), 50, Color.ORANGE, 100, center.x, center.y);
    }
}
