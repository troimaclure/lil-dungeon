/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.manager;

import com.kikijoli.ville.util.Time;

/**
 *
 * @author Arthur
 */
public class RankManager {

    public static int point = 0;
    public static int currentStagePoint = 0;
    public static int TIME_POINT = 250;
    public static int CAUGHT_POINT = 500;
    public static int CAUGHTCOUNT = 0;

    public static void caught() {
        if (CAUGHTCOUNT <= 0) {
            currentStagePoint -= CAUGHT_POINT;
            CAUGHTCOUNT = Time.SECONDE;
        }
    }

    public static void tour() {
        CAUGHTCOUNT -= CAUGHTCOUNT > 0 ? 1 : 0;
    }
}
