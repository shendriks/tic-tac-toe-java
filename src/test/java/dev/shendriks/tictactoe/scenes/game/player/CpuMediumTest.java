package dev.shendriks.tictactoe.scenes.game.player;

import dev.shendriks.tictactoe.scenes.game.model.GameInput;
import dev.shendriks.tictactoe.scenes.game.model.Grid;
import dev.shendriks.tictactoe.scenes.game.model.Symbol;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.random.RandomGenerator;

import static org.junit.jupiter.api.Assertions.*;

class CpuMediumTest {
    @Test
    void testReadInputMakesRandomMove() {
        Grid grid = Grid.createEmpty();
        RandomGenerator random = Mockito.mock(RandomGenerator.class);
        Mockito.when(random.nextInt(9)).thenReturn(1);
        CpuMedium cpu = new CpuMedium(grid, Symbol.X, random);
        
        GameInput actualInput = cpu.readInput();
        
        GameInput expectedInput = GameInput.from(0, 0);
        assertEquals(expectedInput, actualInput);
    }

    @Test
    void testReadInputMakesWinningMoveIfPossible() {
        Grid grid = Grid.createFromString("XX-------");
        RandomGenerator random = Mockito.mock(RandomGenerator.class);
        CpuMedium cpu = new CpuMedium(grid, Symbol.X, random);

        GameInput actualInput = cpu.readInput();

        GameInput expectedInput = GameInput.from(0, 2);
        assertEquals(expectedInput, actualInput);
    }
    
    @Test
    void testReadInputMakesBlockingMoveIfPossible() {
        Grid grid = Grid.createFromString("-------OO");
        RandomGenerator random = Mockito.mock(RandomGenerator.class);
        CpuMedium cpu = new CpuMedium(grid, Symbol.X, random);

        GameInput actualInput = cpu.readInput();

        GameInput expectedInput = GameInput.from(2, 0);
        assertEquals(expectedInput, actualInput);
    }
}