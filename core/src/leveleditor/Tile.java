package leveleditor;

import java.awt.Rectangle;
import java.io.Serializable;

/**
 *
 * @author ajosse
 */
public class Tile extends Rectangle implements Serializable {

    public String code;
    public String data;

    public Tile() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
