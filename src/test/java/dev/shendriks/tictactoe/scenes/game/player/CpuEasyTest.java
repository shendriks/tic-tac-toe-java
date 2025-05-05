package dev.shendriks.tictactoe.scenes.game.player;

import dev.shendriks.tictactoe.scenes.game.exception.GameAlreadyFinishedException;
import dev.shendriks.tictactoe.scenes.game.model.GameInput;
import dev.shendriks.tictactoe.scenes.game.model.Grid;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.random.RandomGenerator;

import static org.junit.jupiter.api.Assertions.*;

class CpuEasyTest {
    @Test
    void testReadInput() {
        Grid grid = Grid.createEmpty();
        RandomGenerator random = Mockito.mock(RandomGenerator.class);
        Mockito.when(random.nextInt(9)).thenReturn(1, 0, 8, 7, 2, 4, 6, 5, 3);
        CpuEasy cpu = new CpuEasy(grid, random);

        ArrayList<GameInput> actualGameInputs = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            actualGameInputs.add(cpu.readInput());
        }

        ArrayList<GameInput> expectedGameInputs = new ArrayList<>();
        expectedGameInputs.add(GameInput.from(0, 1));
        expectedGameInputs.add(GameInput.from(0, 0));
        expectedGameInputs.add(GameInput.from(2, 2));
        expectedGameInputs.add(GameInput.from(2, 1));
        expectedGameInputs.add(GameInput.from(0, 2));
        expectedGameInputs.add(GameInput.from(1, 1));
        expectedGameInputs.add(GameInput.from(2, 0));
        expectedGameInputs.add(GameInput.from(1, 2));
        expectedGameInputs.add(GameInput.from(1, 0));
        
        assertEquals(expectedGameInputs, actualGameInputs);
    }

    @Test
    void testReadInputThrowsExceptionIfGameIsFinished() {
        Grid grid = Grid.createFromString("XOXOXOOXO");
        RandomGenerator random = Mockito.mock(RandomGenerator.class);
        CpuEasy cpu = new CpuEasy(grid, random);

        assertThrows(GameAlreadyFinishedException.class, cpu::readInput);
    }
}