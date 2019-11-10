/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import java.util.HashMap;

/**
 *
 * @author Arthur
 */
public class SoundManager {

    public static final String ARROW_TOUCH = "ARROW_TOUCH";
    public static final String BOW = "BOW";
    public static final String CANNON = "CANNON";
    public static final String CANNON_BALL = "CANNON_BALL";
    public static final String CANNON_BALL_TOUCH = "CANNON_BALL_TOUCH";
    public static final String DASH = "DASH";
    public static final String DEATH = "DEATH";
    public static final String END_OF_LEVEL = "END_OF_LEVEL";
    public static final String FIRE_SPELL = "FIRE_SPELL";
    public static final String IN_FIRE = "IN_FIRE";
    public static final String KILL = "KILL";
    public static final String LEVEL_BEGIN_ZOOM = "LEVEL_BEGIN_ZOOM";
    public static final String LEVEL_END_ANIM_SCREEN = "LEVEL_END_ANIM_SCREEN";
    public static final String OPEN_DOOR = "OPEN_DOOR";
    public static final String POISON_SPELL = "POISON_SPELL";
    public static final String PREPARE_SPELL = "PREPARE_SPELL";
    public static final String SHIELD_CRASH = "SHIELD_CRASH";
    public static final String SWORD = "SWORD";
    public static final String TAKE_KEY = "TAKE_KEY";
    public static final String TRAP_CLIC = "TRAP_CLIC";
    public static final String TRAP_EXPLODE = "TRAP_EXPLODE";

    private static final HashMap<String, Sound> sounds = new HashMap<>();

    private static Sound getSound(String key) {
        if (!sounds.containsKey(key))
            sounds.put(key, Gdx.audio.newSound(Gdx.files.internal("sounds/" + key.toLowerCase() + ".mp3")));
        return sounds.get(key);
    }

    public static void playSound(String key) {
//        getSound(key).play();
    }

}
