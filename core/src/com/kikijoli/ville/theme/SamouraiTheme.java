package com.kikijoli.ville.theme;

import com.badlogic.gdx.graphics.Color;
import com.kikijoli.ville.drawable.hud.BowTile;
import com.kikijoli.ville.drawable.hud.SwordTile;
import com.kikijoli.ville.util.TextureUtil;
import java.util.Arrays;

/**
 *
 * @author ajosse
 */
public class SamouraiTheme extends AbstractTheme {

    public SamouraiTheme() {
        super(
            Arrays.asList(new BowTile(), new SwordTile()),
            TextureUtil.getTexture("sprite/bush.png"),
            Color.valueOf("#27ae60")
        );
    }

}
