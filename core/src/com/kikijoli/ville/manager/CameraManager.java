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
import com.kikijoli.ville.util.Constantes;

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
        target.scl(Tmap.delta * 2);
        cameraPosition.scl(1.0f - Tmap.delta * 2);
        cameraPosition.add(target);
        camera.position.set(cameraPosition);
        handleBorder();
        camera.update(true);
    }

    private static void handleBorder() {

        int gapX = Gdx.graphics.getWidth() / 2;
        int gapY = Gdx.graphics.getHeight() / 2;
        int borderX = Gdx.graphics.getWidth() / 2 + StageManager.widthd * Constantes.TILESIZE;
        int borderY = Gdx.graphics.getHeight() / 2 + StageManager.heightd * Constantes.TILESIZE;
        int viewBorderX = (int) (camera.position.x + Gdx.graphics.getWidth());
        int viewBorderY = (int) (camera.position.y + Gdx.graphics.getHeight());

        if (Gdx.graphics.getWidth() > StageManager.widthd * Constantes.TILESIZE) {
            camera.position.x = gapX;
        } else if (camera.position.x < gapX) {
            camera.position.x = gapX;
        } else if (viewBorderX > borderX) {
            camera.position.x = borderX - Gdx.graphics.getWidth();
        }
        if (Gdx.graphics.getHeight() > StageManager.heightd * Constantes.TILESIZE) {
            camera.position.y = gapX;
        } else if (camera.position.y < gapY) {
            camera.position.y = Gdx.graphics.getHeight() / 2;
        } else if (viewBorderY > borderY) {
            camera.position.y = borderY - Gdx.graphics.getHeight();
        }
    }

}
