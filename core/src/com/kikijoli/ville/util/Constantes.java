/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.util;

/**
 *
 * @author troïmaclure
 */
public class Constantes {

    public static int TILESIZE = 64;
    public final static String EMPTY = "0";
    public final static String WALL = "1";
    public final static String WATER = "2";
    public final static String KEY = "3";
    public final static String LOCK = "4";
    public final static String GUARD = "5";
    public final static String PLAYER = "6";
    public final static String DOOR = "7";
    public final static String TURRET = "8";
    public final static String TRAP = "9";
    public final static String ARCHER = "a";
    public final static String MAGICIAN = "b";
    public final static String GUARD_WITH_KEY = "c";
    public static String NPC_MOVEMENT_OK = GUARD_WITH_KEY + Constantes.MAGICIAN + Constantes.ARCHER + Constantes.TRAP + Constantes.KEY + Constantes.EMPTY + Constantes.GUARD + Constantes.PLAYER;
    public static String BULLET_MOVEMENT_OK = NPC_MOVEMENT_OK + Constantes.TURRET + Constantes.WATER;
    public static String CANNONBALL_MOVEMENT_OK = BULLET_MOVEMENT_OK + Constantes.WALL + Constantes.DOOR + Constantes.LOCK;

}
