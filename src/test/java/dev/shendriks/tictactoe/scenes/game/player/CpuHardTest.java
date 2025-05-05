package dev.shendriks.tictactoe.scenes.game.player;

import dev.shendriks.tictactoe.scenes.game.model.GameInput;
import dev.shendriks.tictactoe.scenes.game.model.Grid;
import dev.shendriks.tictactoe.scenes.game.model.Symbol;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CpuHardTest {
    @Test
    void testReadInput() {
        Grid grid = Grid.createEmpty();
        CpuHard cpu = new CpuHard(grid, Symbol.X);
        
        GameInput actualInput = cpu.readInput();
        GameInput expectedInput = GameInput.from(0, 0);
        
        assertEquals(expectedInput, actualInput);
    }
}