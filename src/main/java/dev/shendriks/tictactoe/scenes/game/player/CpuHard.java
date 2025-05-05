package dev.shendriks.tictactoe.scenes.game.player;

import dev.shendriks.tictactoe.scenes.game.model.*;

import java.util.ArrayList;

public class CpuHard implements Player {
    private final Grid grid;
    private final Symbol ownSymbol;
    private final int maxDepth = 9;
    private GameInput nextMove = null;

    public CpuHard(Grid grid, Symbol ownSymbol) {
        this.grid = grid;
        this.ownSymbol = ownSymbol;
    }

    @Override
    public GameInput readInput() {
        System.out.println("Making move level \"hard\"");

        nextMove = null;
        max(maxDepth);
        return nextMove;
    }

    private int max(int depth) {
        if (depth == 0 || grid.isGameFinished()) {
            return evaluate();
        }

        int maxValue = Integer.MIN_VALUE;

        ArrayList<Position> positions = grid.getEmptyPositions();
        for (Position position : positions) {
            grid.setCell(position);
            int value = min(depth - 1);
            grid.clearCell(position);
            if (value > maxValue) {
                maxValue = value;
                if (depth == maxDepth) {
                    nextMove = new GameInput(position);
                }
            }
        }

        return maxValue;
    }

    private int min(int depth) {
        if (depth == 0 || grid.isGameFinished()) {
            return evaluate();
        }

        int minValue = Integer.MAX_VALUE;

        ArrayList<Position> positions = grid.getEmptyPositions();
        for (Position position : positions) {
            grid.setCell(position);
            int value = max(depth - 1);
            grid.clearCell(position);
            if (value < minValue) {
                minValue = value;
            }
        }

        return minValue;
    }

    private int evaluate() {
        GameState whoWins = grid.getGameState();

        if (whoWins.equals(GameState.Draw)) return 1;
        else if (whoWins.equals(GameState.XWins) && ownSymbol.equals(Symbol.X)) return 2;
        else if (whoWins.equals(GameState.OWins) && ownSymbol.equals(Symbol.O)) return 2;

        return 0;
    }
}
