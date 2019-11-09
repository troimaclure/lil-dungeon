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
    public static final ArrayList<ParticleEffect> particleEffectsFixed = new ArrayList<>();

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

    public static ParticleEffect addParticleFixed(String path, float x, float y, float scale) {
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
        particleEffectsFixed.add(efft);
        return efft;
    }

    public static ArrayList<ParticleEffect> getParticleEffects() {
        return (ArrayList<ParticleEffect>) particleEffects.clone();
    }

    public static ArrayList<ParticleEffect> getParticleEffectsFixed() {
        return (ArrayList<ParticleEffect>) particleEffectsFixed.clone();
    }

    public static void draw(float delta) {
        Color c = spriteBatch.getColor();
        for (ParticleEffect par : ParticleManager.getParticleEffects()) {

            par.update(delta);
            par.getEmitters().first().getTransparency().setHigh(1.0f);
            par.draw(Tmap.spriteBatch);
            if (par.isComplete()) {
                particleEffects.remove(par);
            }
        }

        spriteBatch.setColor(c);
    }

    public static void drawFixed(float delta) {
        Color c = Tmap.hudBatch.getColor();

        for (ParticleEffect par : ParticleManager.getParticleEffectsFixed()) {
            par.update(delta);
            par.getEmitters().first().getTransparency().setHigh(1.0f);
            par.draw(Tmap.hudBatch);
            if (par.isComplete()) {
                particleEffectsFixed.remove(par);
            }
        }

        Tmap.hudBatch.setColor(c);
    }
}
