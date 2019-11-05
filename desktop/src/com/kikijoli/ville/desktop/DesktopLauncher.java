package com.kikijoli.ville.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.kikijoli.ville.Ville;

public class DesktopLauncher {

    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        config.foregroundFPS = 60;
        config.samples = 4;
        config.vSyncEnabled = true;
        config.allowSoftwareMode = true;
        config.useHDPI = true;
        config.setFromDisplayMode(LwjglApplicationConfiguration.getDesktopDisplayMode());
        new LwjglApplication(new Ville(), config);
    }
}
