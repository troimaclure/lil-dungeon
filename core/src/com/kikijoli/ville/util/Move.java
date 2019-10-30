package com.kikijoli.ville.util;

import com.badlogic.gdx.math.Rectangle;
import com.kikijoli.ville.manager.LockManager;
import com.kikijoli.ville.manager.StageManager;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author ajosse
 */
public class Move {

    public static List<Rectangle> NPC_MOVE_FILTER;
    public static List<Rectangle> BULLET_MOVE_FILTER;
    public static List<Rectangle> SPELL_MOVE_FILTER;
    public static List<Rectangle> TRAP_MOVE_FILTER;

    public static void initialize() {
        LockManager.refeshLocksRectangle();
        NPC_MOVE_FILTER = Stream.concat(StageManager.walls.stream(), LockManager.lockRectangles.stream()).collect(Collectors.toList());
        BULLET_MOVE_FILTER = Stream.concat(StageManager.walls.stream(), LockManager.lockRectangles.stream()).collect(Collectors.toList());
        SPELL_MOVE_FILTER = BULLET_MOVE_FILTER;
        TRAP_MOVE_FILTER = StageManager.walls;
    }

}
