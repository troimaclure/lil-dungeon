package com.kikijoli.ville.manager;

import com.kikijoli.ville.state.AbstractState;
import java.util.ArrayList;

/**
 *
 * @author ajosse
 */
public class StateManager {

    public static ArrayList<AbstractState> states = new ArrayList<>();

    public static void tour() {
        ArrayList<AbstractState> removes = new ArrayList<>();
        states.stream().map((state) -> {
            state.step();
            return state;
        }).filter((state) -> (state.isFinish())).forEachOrdered((state) -> {
            state.stop();
            removes.add(state);
        });
        states.removeAll(removes);
    }
}
