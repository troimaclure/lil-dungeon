package com.kikijoli.ville.automation.prevent;

import com.kikijoli.ville.util.Count;

/**
 *
 * @author ajosse
 */
public class Prevent {

    public Count count;
    public Runnable runnable;

    public Prevent(Count count, Runnable runnable) {
        this.count = count;
        this.runnable = runnable;
    }

}
