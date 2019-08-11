/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import static com.kikijoli.ville.maps.Tmap.worldCoordinates;

/**
 *
 * @author troïmaclure
 */
public class CameraManager {

    public static OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    public static void initialize(int x, int y) {
        camera.position.x = x;
        camera.position.y = y;
        camera.update();
    }

    public static void tour() {
        camera.unproject(worldCoordinates);
        if (!EntiteManager.playedBall)
            camera.position.set(EntiteManager.player.getX(), EntiteManager.player.getY(), 0);
        else
            camera.position.set(EntiteManager.currentBallPosition.x, EntiteManager.currentBallPosition.y, 0);
        camera.update();
    }

}
