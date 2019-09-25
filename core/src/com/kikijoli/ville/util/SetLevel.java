package com.kikijoli.ville.util;

import com.badlogic.gdx.math.Rectangle;
import com.kikijoli.ville.manager.CameraManager;
import com.kikijoli.ville.manager.SoundManager;
import com.kikijoli.ville.manager.StageManager;
import com.kikijoli.ville.maps.Tmap;
import static com.kikijoli.ville.maps.Tmap.setLevel;

/**
 *
 * @author ajosse
 */
public class SetLevel {

    public String level;
    private int count = 0;
    private int countX = 0;
    private final int delay = 50;
    private boolean end;
    public Rectangle rectangle = new Rectangle(0, 0, 0, 0);
    boolean soundPlayed = false;

    public SetLevel(String level) {
        this.level = level;
    }

    public void setting() {
        if (end == false) {

            if (count++ >= delay) {
                end = true;
                StageManager.setLevel(setLevel.level);
            }
            rectangle.setWidth(count * 50);
            rectangle.setHeight(count * 50);
        } else {
            if (!soundPlayed) {
                SoundManager.playSound(SoundManager.LEVEL_BEGIN_ZOOM);
                soundPlayed = true;
            }
            CameraManager.camera.zoom = count;
            if (count-- <= 0) {
                setLevel = null;
                CameraManager.camera.zoom = 1;
                Tmap.settingLevel = false;
            }
            rectangle.setX(countX++ * 50);
            rectangle.setY(countX * 50);
        }
    }
}
