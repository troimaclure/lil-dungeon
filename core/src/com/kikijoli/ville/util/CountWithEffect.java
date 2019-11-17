package com.kikijoli.ville.util;

/**
 *
 * @author ajosse
 */
public class CountWithEffect {

    private int count;
    private final int delay;
    private Runnable run;

    public CountWithEffect(int delay, Runnable run) {
        this.delay = delay;
        this.run = run;
    }

    public void increment() {
        count++;
    }

    public boolean step() {
        return count++ >= delay;
    }

    public void complete() {
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

    public void run() {
        this.run.run();
    }
}
