package dev.shendriks.tictactoe.scenes.game.model;

import dev.shendriks.tictactoe.scenes.game.exception.CellOccupiedException;
import dev.shendriks.tictactoe.scenes.game.exception.GameAlreadyFinishedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class GridTest {
    public static Stream<Arguments> provideGridStringsAndGameStates() {
        return Stream.of(
                Arguments.of("---------", GameState.GameNotFinished),
                Arguments.of("XOXOXOOX-", GameState.GameNotFinished),
                Arguments.of("XOXOXOOXO", GameState.Draw),
                Arguments.of("XXX------", GameState.XWins),
                Arguments.of("---XXX---", GameState.XWins),
                Arguments.of("------XXX", GameState.XWins),
                Arguments.of("X--X--X--", GameState.XWins),
                Arguments.of("-X--X--X-", GameState.XWins),
                Arguments.of("--X--X--X", GameState.XWins),
                Arguments.of("X---X---X", GameState.XWins),
                Arguments.of("--X-X-X--", GameState.XWins),
                Arguments.of("OOO------", GameState.OWins),
                Arguments.of("---OOO---", GameState.OWins),
                Arguments.of("------OOO", GameState.OWins),
                Arguments.of("O--O--O--", GameState.OWins),
                Arguments.of("-O--O--O-", GameState.OWins),
                Arguments.of("--O--O--O", GameState.OWins),
                Arguments.of("O---O---O", GameState.OWins),
                Arguments.of("--O-O-O--", GameState.OWins)
        );
    }

    @Test
    void testCreateGrid() {
        Grid grid = Grid.createEmpty();

        assertFalse(grid.isFull());
        assertFalse(grid.isGameFinished());
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Position position = new Position(i, j);
                assertTrue(grid.isEmptyAt(position));
                assertFalse(grid.isOccupiedAt(position));
            }
        }
    }

    @Test
    void testGetEmptyCells() {
        Grid grid = Grid.createEmpty();

        ArrayList<Position> emptyPositions = grid.getEmptyPositions();

        assertEquals(9, emptyPositions.size());
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals(new Position(i, j), emptyPositions.get(i * 3 + j));
            }
        }
    }

    @Test
    void testSetCellAlternatesSymbol() {
        Grid grid = Grid.createEmpty();

        Position pos = new Position(0, 0);
        grid.setCell(pos);
        assertEquals(Symbol.X, grid.getSymbol(pos));

        pos = new Position(0, 1);
        grid.setCell(pos);
        assertEquals(Symbol.O, grid.getSymbol(pos));
    }

    @Test
    void testSetCellThrowsGameFinishedExceptionIfGameIsFinished() {
        Grid grid = Grid.createFromString("XXXOO----");
        assertThrows(GameAlreadyFinishedException.class, () -> grid.setCell(new Position(0, 0)));
    }

    @Test
    void testSetCellThrowsCellOccupiedExceptionIfCellIsOccupied() {
        Grid grid = Grid.createEmpty();

        grid.setCell(new Position(0, 0));

        assertThrows(CellOccupiedException.class, () -> grid.setCell(new Position(0, 0)));
    }

    @ParameterizedTest
    @MethodSource("provideGridStringsAndGameStates")
    void testGetGameState(String gridCreationString, GameState expectedGameState) {
        Grid grid = Grid.createFromString(gridCreationString);

        assertEquals(expectedGameState, grid.getGameState(), gridCreationString);
    }

    @Test
    void testWhoseTurnIsIt() {
        Grid grid = Grid.createEmpty();

        assertEquals(Symbol.X, grid.whoseTurnIsIt());

        grid.setCell(new Position(0, 0));

        assertEquals(Symbol.O, grid.whoseTurnIsIt());
    }

    @Test
    void testWhoseTurnIsItReturnsNullIfGameIsFinished() {
        Grid grid = Grid.createFromString("XOXXOXOXO");

        assertNull(grid.whoseTurnIsIt());
    }

    @Test
    void testClearCell() {
        Grid grid = Grid.createFromString("XO-------");

        grid.clearCell(new Position(0, 0));
        grid.clearCell(new Position(0, 1));

        assertEquals(Symbol.EMPTY, grid.getSymbol(new Position(0, 0)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"", "-", "---", "----------"})
    void testCreateFromStringThrowsExceptionIfStringHasWrongLength(String invalidString) {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> Grid.createFromString(invalidString));
        assertEquals("Input must be 9 characters", exception.getMessage());
    }

    @Test
    void testEqualsSymbolAt() {
        Grid grid = Grid.createFromString("XO-------");

        assertTrue(grid.equalsSymbolAt(Symbol.X, new Position(0, 0)));
        assertTrue(grid.equalsSymbolAt(Symbol.O, new Position(0, 1)));
        assertTrue(grid.equalsSymbolAt(Symbol.EMPTY, new Position(0, 2)));
        assertFalse(grid.equalsSymbolAt(Symbol.O, new Position(0, 0)));
        assertFalse(grid.equalsSymbolAt(Symbol.EMPTY, new Position(0, 0)));
        assertFalse(grid.equalsSymbolAt(Symbol.X, new Position(0, 1)));
        assertFalse(grid.equalsSymbolAt(Symbol.EMPTY, new Position(0, 1)));
        assertFalse(grid.equalsSymbolAt(Symbol.X, new Position(0, 2)));
        assertFalse(grid.equalsSymbolAt(Symbol.O, new Position(0, 2)));
    }

    @Test
    void getSymbolPositions() {
        Grid grid = Grid.createFromString("XO-OXX---");

        List<Map<Symbol, List<Position>>> actualSymbolPositions = grid.getSymbolPositions();

        assertEquals(8, actualSymbolPositions.size());

        // 1st row
        int lineIndex = 0;
        Map<Symbol, List<Position>> line = actualSymbolPositions.get(lineIndex++);
        assertEquals(3, line.size());
        assertEquals(1, line.get(Symbol.X).size());
        assertEquals(1, line.get(Symbol.O).size());
        assertEquals(1, line.get(Symbol.EMPTY).size());
        assertEquals(new Position(0, 0), line.get(Symbol.X).getFirst());
        assertEquals(new Position(0, 1), line.get(Symbol.O).getFirst());
        assertEquals(new Position(0, 2), line.get(Symbol.EMPTY).getFirst());

        // 2nd row
        line = actualSymbolPositions.get(lineIndex++);
        assertEquals(3, line.size());
        assertEquals(2, line.get(Symbol.X).size());
        assertEquals(1, line.get(Symbol.O).size());
        assertEquals(0, line.get(Symbol.EMPTY).size());
        assertEquals(new Position(1, 1), line.get(Symbol.X).getFirst());
        assertEquals(new Position(1, 2), line.get(Symbol.X).get(1));
        assertEquals(new Position(1, 0), line.get(Symbol.O).getFirst());

        // 3rd rowline
        line = actualSymbolPositions.get(lineIndex++);
        assertEquals(3, line.size());
        assertEquals(0, line.get(Symbol.X).size());
        assertEquals(0, line.get(Symbol.O).size());
        assertEquals(3, line.get(Symbol.EMPTY).size());
        assertEquals(new Position(2, 0), line.get(Symbol.EMPTY).getFirst());
        assertEquals(new Position(2, 1), line.get(Symbol.EMPTY).get(1));
        assertEquals(new Position(2, 2), line.get(Symbol.EMPTY).get(2));

        // 1st col
        line = actualSymbolPositions.get(lineIndex++);
        assertEquals(3, line.size());
        assertEquals(1, line.get(Symbol.X).size());
        assertEquals(1, line.get(Symbol.O).size());
        assertEquals(1, line.get(Symbol.EMPTY).size());
        assertEquals(new Position(0, 0), line.get(Symbol.X).getFirst());
        assertEquals(new Position(1, 0), line.get(Symbol.O).getFirst());
        assertEquals(new Position(2, 0), line.get(Symbol.EMPTY).getFirst());

        // 2nd col
        line = actualSymbolPositions.get(lineIndex++);
        assertEquals(3, line.size());
        assertEquals(1, line.get(Symbol.X).size());
        assertEquals(1, line.get(Symbol.O).size());
        assertEquals(1, line.get(Symbol.EMPTY).size());
        assertEquals(new Position(1, 1), line.get(Symbol.X).getFirst());
        assertEquals(new Position(0, 1), line.get(Symbol.O).getFirst());
        assertEquals(new Position(2, 1), line.get(Symbol.EMPTY).getFirst());

        // 3rd col
        line = actualSymbolPositions.get(lineIndex++);
        assertEquals(3, line.size());
        assertEquals(1, line.get(Symbol.X).size());
        assertEquals(0, line.get(Symbol.O).size());
        assertEquals(2, line.get(Symbol.EMPTY).size());
        assertEquals(new Position(1, 2), line.get(Symbol.X).getFirst());
        assertEquals(new Position(0, 2), line.get(Symbol.EMPTY).getFirst());
        assertEquals(new Position(2, 2), line.get(Symbol.EMPTY).get(1));

        // 1st diag
        line = actualSymbolPositions.get(lineIndex++);
        assertEquals(3, line.size());
        assertEquals(2, line.get(Symbol.X).size());
        assertEquals(0, line.get(Symbol.O).size());
        assertEquals(1, line.get(Symbol.EMPTY).size());
        assertEquals(new Position(0, 0), line.get(Symbol.X).getFirst());
        assertEquals(new Position(1, 1), line.get(Symbol.X).get(1));
        assertEquals(new Position(2, 2), line.get(Symbol.EMPTY).getFirst());

        // 2nd diag
        line = actualSymbolPositions.get(lineIndex);
        assertEquals(3, line.size());
        assertEquals(1, line.get(Symbol.X).size());
        assertEquals(0, line.get(Symbol.O).size());
        assertEquals(2, line.get(Symbol.EMPTY).size());
        assertEquals(new Position(1, 1), line.get(Symbol.X).getFirst());
        assertEquals(new Position(0, 2), line.get(Symbol.EMPTY).getFirst());
        assertEquals(new Position(2, 0), line.get(Symbol.EMPTY).get(1));
    }
}