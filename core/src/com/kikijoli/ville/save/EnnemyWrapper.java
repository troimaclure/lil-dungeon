package com.kikijoli.ville.save;

import java.io.Serializable;

/**
 *
 * @author ajosse
 */
public class EnnemyWrapper extends EntiteWrapper implements Serializable {

    public int pv;

    public EnnemyWrapper(int pv, float x, float y, String classDestination, String methodDestination, String className) {
        super(x, y, classDestination, methodDestination, className);
        this.pv = pv;
    }

    
    public int getPv() {
        return pv;
    }

    public void setPv(int pv) {
        this.pv = pv;
    }

}
