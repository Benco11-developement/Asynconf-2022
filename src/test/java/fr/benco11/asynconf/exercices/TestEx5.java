package fr.benco11.asynconf.exercices;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TestEx5 {
    @Test
    void testBoardFromInput() {
        Ex5 exo = new Ex5();

        byte n = Ex5.Board.NAVIGABLE;
        byte o = Ex5.Board.OBSTACLE;

        Ex5.Board b = new Ex5.Board(new byte[][] {{n, n, o, n, n}, {n, n, o, o, n}, {n, o, n, o, o}, {n, n, o, n, n}, {o, n, n, o, o}, {o, o, n, n, n},
                {n, o, o, n, n}, {n,o, o, n, o}, {o, o, n, n, n}, {n, n, n, o, o}, {n, n, o, o, o}, {n, n, n, n, n}, {o, n, n, o, o}}, new int[13][5]);

        b.setStart(new Ex5.Coord(0, 0));
        b.setEnd(new Ex5.Coord(11, 4));

        var b2 = exo.boardFromInput(Arrays.asList(
                "X___OO__O___O",
                "__O__OOOO____",
                "OO_O__OO__O__",
                "_OO_O____OO_O",
                "__O_O__O_OOVO"));

        assertArrayEquals(b.distanceBoard, b2.distanceBoard);
        assertArrayEquals(b.mazeBoard, b2.mazeBoard);
        assertEquals(b.getStart(), b2.getStart());
        assertEquals(b.getEnd(), b2.getEnd());
    }
}
