package dev.shendriks.tictactoe.scenes.game.player;

import dev.shendriks.tictactoe.scenes.game.model.GameInput;
import dev.shendriks.tictactoe.scenes.game.model.Grid;
import dev.shendriks.tictactoe.scenes.game.model.Position;
import dev.shendriks.tictactoe.scenes.game.model.Symbol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.random.RandomGenerator;

public class CpuMedium implements Player {
    private final Grid grid;
    private final Symbol ownSymbol;
    private final Symbol opponentSymbol;
    private final RandomGenerator rand;
    private final List<Map<Symbol, List<Position>>> symbolPositions = new ArrayList<>(8);

    public CpuMedium(Grid grid, Symbol ownSymbol, RandomGenerator rand) {
        this.grid = grid;
        this.ownSymbol = ownSymbol;
        this.rand = rand;
        this.opponentSymbol = ownSymbol.equals(Symbol.X) ? Symbol.O : Symbol.X;

        for (int i = 0; i < 8; i++) {
            HashMap<Symbol, List<Position>> positions = new HashMap<>();
            positions.put(Symbol.X, new ArrayList<>());
            positions.put(Symbol.O, new ArrayList<>());
            positions.put(Symbol.EMPTY, new ArrayList<>());
            symbolPositions.add(positions);
        }
    }

    /**
     * 1. Try to make a winning move: If we have two in a row and can win with one more move, we take that move.
     * 2. Try to make a blocking move: If the opponent has two in a row and can win with one more move, we block it.
     * 3. Fallback move: If neither of the above applies, make a random move.
     *
     * @return GameInput The next move
     */
    @Override
    public GameInput readInput() {
        System.out.println("Making move level \"medium\"");

        GameInput nextMove = tryGetNextMove(ownSymbol);
        if (nextMove != null) {
            return nextMove;
        }

        nextMove = tryGetNextMove(opponentSymbol);
        if (nextMove != null) {
            return nextMove;
        }

        return generateRandomMove();
    }

    /**
     * Try calculating the next move by finding a line with two cells with identical symbols and one empty cell.
     *
     * @return GameInput A winning or blocking move, or null, if no such move is possible.
     */
    private GameInput tryGetNextMove(Symbol symbol) {
        calculateSymbolPositions();
        for (Map<Symbol, List<Position>> line : symbolPositions) {
            if (line.get(symbol).size() == 2 && line.get(Symbol.EMPTY).size() == 1) {
                Position emptyPosition = line.get(Symbol.EMPTY).getFirst();
                return new GameInput(emptyPosition);
            }
        }
        return null;
    }


    /**
     * For each row, column and diagonal, calculate the positions of Xs, Os, and empty cells.
     */
    private void calculateSymbolPositions() {
        for (Map<Symbol, List<Position>> line : symbolPositions) {
            line.get(Symbol.X).clear();
            line.get(Symbol.O).clear();
            line.get(Symbol.EMPTY).clear();
        }

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Symbol symbol = grid.getSymbol(new Position(row, col));

                symbolPositions.get(row).get(symbol).add(new Position(row, col));
                symbolPositions.get(col + 3).get(symbol).add(new Position(row, col));

                boolean isDiag1 = row == col;
                if (isDiag1) {
                    symbolPositions.get(6).get(symbol).add(new Position(row, col));
                }

                boolean isDiag2 = row == 2 - col;
                if (isDiag2) {
                    symbolPositions.get(7).get(symbol).add(new Position(row, col));
                }
            }
        }
    }

    /**
     * @return GameInput A random move
     */
    private GameInput generateRandomMove() {
        int row, col;

        Position position;
        do {
            row = rand.nextInt(3);
            col = rand.nextInt(3);
            position = new Position(row, col);
        } while (grid.isOccupiedAt(position));

        return new GameInput(position);
    }
}
