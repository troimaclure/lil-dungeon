/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kikijoli.ville.manager;

import com.kikijoli.ville.util.CountWithEffect;
import java.util.ArrayList;

/**
 *
 * @author Arthur
 */
public class CountManager {

    public static ArrayList<CountWithEffect> counts = new ArrayList<>();

    public static void tour() {
        ArrayList<CountWithEffect> remove = new ArrayList<>();
        counts.stream().filter((count) -> (count.stepAndComplete())).forEachOrdered((count) -> {
            remove.add(count);
            count.run();
        });
        counts.removeAll(remove);
    }
}
