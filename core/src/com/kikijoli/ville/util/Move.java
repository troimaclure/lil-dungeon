package com.kikijoli.ville.util;

import com.badlogic.gdx.math.Rectangle;
import com.kikijoli.ville.manager.LockManager;
import com.kikijoli.ville.manager.StageManager;
import java.util.ArrayList;
import java.util.stream.Stream;

/**
 *
 * @author ajosse
 */
public class Move {

    public static Stream<ArrayList<Rectangle>> NPC_MOVE_FILTER;
    public static Stream<ArrayList<Rectangle>> BULLET_MOVE_FILTER;
    public static Stream<ArrayList<Rectangle>> SPELL_MOVE_FILTER;
    public static Stream<ArrayList<Rectangle>> TRAP_MOVE_FILTER;

    public static void initialize() {
        LockManager.refeshLocksRectangle();
        NPC_MOVE_FILTER = Stream.of(StageManager.walls, LockManager.lockRectangles);
        BULLET_MOVE_FILTER = Stream.of(StageManager.walls, LockManager.lockRectangles);
        SPELL_MOVE_FILTER = BULLET_MOVE_FILTER;
        TRAP_MOVE_FILTER = Stream.of(StageManager.walls);
    }

}
