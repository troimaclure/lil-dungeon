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

    public static Tile[][] load(String name) {
        FileHandle internal = Gdx.files.internal("stage/" + name + ".xml");
        String str = internal.readString();
        try (XMLDecoder d = new XMLDecoder(new ByteArrayInputStream(str.getBytes()))) {
            return (Tile[][]) d.readObject();
        }
    }
}
