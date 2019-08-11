package com.kikijoli.ville.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.kikijoli.ville.Ville;

public class DesktopLauncher {

    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.height = 1050;
        config.width = 1680;
        config.foregroundFPS = 60;
//        config.fullscreen = true;
        config.samples = 4;
        config.vSyncEnabled = true;
        config.allowSoftwareMode = true;
        new LwjglApplication(new Ville(), config);
    }
}
