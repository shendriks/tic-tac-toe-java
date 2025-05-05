package dev.shendriks.tictactoe.scenes.game.player;

import dev.shendriks.tictactoe.scenes.game.exception.GameAlreadyFinishedException;
import dev.shendriks.tictactoe.scenes.game.model.GameInput;
import dev.shendriks.tictactoe.scenes.game.model.Grid;
import dev.shendriks.tictactoe.scenes.game.model.Position;

import java.util.ArrayList;
import java.util.random.RandomGenerator;

public class CpuEasy implements Player {
    private final Grid grid;
    private final RandomGenerator rand;

    public CpuEasy(Grid grid, RandomGenerator rand) {
        this.grid = grid;
        this.rand = rand;
    }

    @Override
    public GameInput readInput() {
        System.out.println("Making move level \"easy\"");

        ArrayList<Position> emptyPositions = grid.getEmptyPositions();
        if (emptyPositions.isEmpty()) {
            throw new GameAlreadyFinishedException();
        }
        int positionIndex = rand.nextInt(emptyPositions.size());
        return new GameInput(emptyPositions.get(positionIndex));
    }
}
