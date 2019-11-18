package com.kikijoli.ville.util;

/**
 *
 * @author ajosse
 */
public class Count {

    private int count;
    private final int delay;

    public Count(int count, int delay) {
        this.count = count;
        this.delay = delay;
    }

    public void increment() {
        count++;
    }

    public boolean step() {
        return count++ >= delay;
    }

    public void reset() {
        count = 0;
    }

    public boolean isComplete() {
        return count >= delay;
    }

    public boolean stepAndComplete() {
        boolean ok = count++ >= delay;
        if (ok) count = 0;
        return ok;
    }

    public int stepAndGet() {
        return count++;
    }

    public int getCount() {
        return count;
    }
}
