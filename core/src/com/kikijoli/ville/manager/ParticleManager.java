/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.kikijoli.ville.maps.Tmap;
import static com.kikijoli.ville.maps.Tmap.spriteBatch;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author troïmaclure
 */
public class ParticleManager {

    private static final HashMap<String, ParticleEffect> particles = new HashMap<>();
    public static final ArrayList<ParticleEffect> particleEffects = new ArrayList<>();

    public static ParticleEffect addParticle(String path, float x, float y, float scale) {
        ParticleEffect efft = null;
        if (!particles.containsKey(path)) {
            efft = new ParticleEffect();
            efft.load(Gdx.files.internal(path), Gdx.files.internal(""));
            particles.put(path, efft);
        } else {
            efft = new ParticleEffect(particles.get(path));
        }

        efft.reset(true);
        efft.scaleEffect(scale);

        efft.getEmitters().first().setPosition(x, y);
        particleEffects.add(efft);
        return efft;
    }

    public static ArrayList<ParticleEffect> getParticleEffects() {
        return (ArrayList<ParticleEffect>) particleEffects.clone();
    }

    public static void tour(float delta) {
        Color c = spriteBatch.getColor();
        for (ParticleEffect par : ParticleManager.getParticleEffects()) {
//			Color col = ColorManager.getTextureColor();
//			float[] colors = par.getEmitters().first().getTint().getColors();

//			if (colors.length >= 6) {
//				colors[0] = col.r;
//				colors[1] = col.g;
//				colors[2] = col.b;
//				colors[3] = col.r;
//				colors[4] = col.g;
//				colors[5] = col.b;
//			}
//			par.getEmitters().first().getTint().setColors(colors);
            par.update(delta);
            par.getEmitters().first().getTransparency().setHigh(1.0f);
            par.draw(Tmap.spriteBatch);
            if (par.isComplete()) {
                particleEffects.remove(par);
            }
        }
        spriteBatch.setColor(c);
    }
}
