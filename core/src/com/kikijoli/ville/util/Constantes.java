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
    public static int SCREENWIDTH = 600;
    public static int SCREENHEIGHT = 800;
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
    public static String NPC_MOVEMENT_OK = Constantes.TRAP + Constantes.KEY + Constantes.EMPTY + Constantes.GUARD + Constantes.PLAYER;
    public static String BULLET_MOVEMENT_OK = Constantes.TURRET + Constantes.KEY + Constantes.EMPTY + Constantes.GUARD + Constantes.PLAYER + Constantes.WATER;
    public static String CANNONBALL_MOVEMENT_OK = Constantes.TURRET + Constantes.KEY + Constantes.EMPTY + Constantes.GUARD + Constantes.PLAYER + Constantes.WATER + Constantes.WALL + Constantes.DOOR + Constantes.LOCK + Constantes.KEY;
    public static String NPCFILTERBUILD = Constantes.WALL + Constantes.LOCK + Constantes.WATER;

}
