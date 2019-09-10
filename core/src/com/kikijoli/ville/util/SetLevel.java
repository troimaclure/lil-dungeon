package com.kikijoli.ville.util;

import com.badlogic.gdx.math.Rectangle;
import com.kikijoli.ville.manager.StageManager;
import com.kikijoli.ville.maps.Tmap;
import static com.kikijoli.ville.maps.Tmap.setLevel;

/**
 *
 * @author ajosse
 */
public class SetLevel {

    public int level;
    private int count = 0;
    private int delay = 60;
    public Rectangle rectangle = new Rectangle(0, 0, 0, 0);

    public SetLevel(int level) {
        this.level = level;

    }

    public void setting() {
        if (count++ >= delay) {
            StageManager.setLevel(setLevel.level);
            setLevel = null;
            Tmap.settingLevel = false;
        }
        rectangle.setWidth(count * 30);
        rectangle.setHeight(count * 30);
    }
}
