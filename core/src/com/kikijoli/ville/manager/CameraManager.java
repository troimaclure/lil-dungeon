/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import com.kikijoli.ville.maps.Tmap;
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
        camera.setToOrtho(false);
        camera.update();

    }

    public static void tour() {
        camera.unproject(worldCoordinates);
        Vector3 target = new Vector3((int) EntiteManager.player.getX(), (int) EntiteManager.player.getY(), 0);
        Vector3 cameraPosition = camera.position;
        target.scl(Tmap.delta * 5);
        cameraPosition.scl(1.0f - Tmap.delta * 5);
        cameraPosition.add(target);
        camera.position.set(cameraPosition);
        camera.update(true);
    }

}
