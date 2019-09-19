package com.kikijoli.ville.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import leveleditor.Tile;
import java.beans.XMLDecoder;
import java.io.ByteArrayInputStream;

/**
 *
 * @author ajosse
 */
public class XmlManager {

    private static final String XML = ".xml";
    private static final String STAGE_PATH = "stage/";

    public static Tile[][] load(String name) {
        FileHandle internal = Gdx.files.internal(STAGE_PATH + name + XML);
        String str = internal.readString();
        try (XMLDecoder d = new XMLDecoder(new ByteArrayInputStream(str.getBytes()))) {
            return (Tile[][]) d.readObject();
        }
    }
}
