package dev.shendriks.tictactoe.scenes.game.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CellTest {
    public static Stream<Arguments> provideSetSymbolParameters() {
        return Stream.of(
                Arguments.of(Symbol.X, true, false, true, false, "X"),
                Arguments.of(Symbol.O, true, false, false, true, "O"),
                Arguments.of(Symbol.EMPTY, false, true, false, false, " ")
        );
    }

    @Test
    void testCreateEmptyCell() {
        Cell cell = new Cell();
        assertEquals(Symbol.EMPTY, cell.getSymbol());
        assertTrue(cell.isEmpty());
        assertFalse(cell.isOccupied());
        assertEquals(" ", cell.toString());
    }

    @ParameterizedTest
    @MethodSource("provideSetSymbolParameters")
    void testSetSymbol(
            Symbol symbol,
            boolean expectedIsOccupied,
            boolean expectedIsEmpty,
            boolean expectedIsX,
            boolean expectedIsO,
            String expectedString
    ) {
        Cell cell = new Cell();
        cell.setSymbol(symbol);
        assertEquals(symbol, cell.getSymbol());
        assertEquals(expectedIsEmpty, cell.isEmpty());
        assertEquals(expectedIsOccupied, cell.isOccupied());
        assertEquals(expectedIsX, cell.isX());
        assertEquals(expectedIsO, cell.isO());
        assertEquals(expectedString, cell.toString());
    }
}