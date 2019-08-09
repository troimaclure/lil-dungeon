package com.kikijoli.ville;

import com.badlogic.gdx.Game;
import com.kikijoli.ville.maps.Tmap;
import com.kotcrab.vis.ui.VisUI;

public class Ville extends Game {

    @Override
    public void create() {
        VisUI.load();
        setScreen(new Tmap());
    }

    @Override
    public void render() {
        super.render();
    }

}
