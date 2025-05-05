package dev.shendriks.tictactoe.scenes.game.player;

import dev.shendriks.tictactoe.scenes.game.model.GameInput;
import dev.shendriks.tictactoe.scenes.game.model.Grid;
import dev.shendriks.tictactoe.scenes.game.model.Position;
import dev.shendriks.tictactoe.scenes.game.model.Symbol;

import java.util.random.RandomGenerator;

public class CpuMedium implements Player {
    private final Grid grid;
    private final Symbol ownPlaySymbol;
    private final Symbol otherPlaySymbol;
    private final RandomGenerator rand;

    public CpuMedium(Grid grid, Symbol ownPlaySymbol, RandomGenerator rand) {
        this.grid = grid;
        this.ownPlaySymbol = ownPlaySymbol;
        this.rand = rand;
        if (ownPlaySymbol.equals(Symbol.X)) {
            this.otherPlaySymbol = Symbol.O;
        } else {
            this.otherPlaySymbol = Symbol.X;
        }
    }

    @Override
    public GameInput readInput() {
        System.out.println("Making move level \"medium\"");

        GameInput nextMove = tryGetNextMove(ownPlaySymbol);
        if (nextMove != null) return nextMove;

        nextMove = tryGetNextMove(otherPlaySymbol);
        if (nextMove != null) return nextMove;

        // 3. Fallback move: If neither of the above applies, make a random move.
        return getRandomMove();
    }

    /**
     * Try calculating the next move.
     * <p>
     * Winning move: If the AI has two in a row and can win with one more move, it takes that move.
     * Blocking move: If the opponent has two in a row and can win with one more move, the AI blocks it.
     */
    private GameInput tryGetNextMove(Symbol symbol) {
        Position emptyCellPosition;
        int cellCount;

        // rows
        for (int row = 0; row < 3; row++) {
            emptyCellPosition = null;
            cellCount = 0;
            for (int col = 0; col < 3; col++) {
                Position position = new Position(row, col);
                if (this.grid.equalsSymbolAt(symbol, position)) {
                    cellCount++;
                } else if (this.grid.isEmptyAt(position)) {
                    emptyCellPosition = position;
                }
            }
            if (cellCount == 2 && emptyCellPosition != null) {
                return new GameInput(emptyCellPosition);
            }
        }

        // cols
        for (int col = 0; col < 3; col++) {
            emptyCellPosition = null;
            cellCount = 0;
            for (int row = 0; row < 3; row++) {
                Position position = new Position(row, col);
                if (this.grid.equalsSymbolAt(symbol, position)) {
                    cellCount++;
                } else if (this.grid.isEmptyAt(position)) {
                    emptyCellPosition = position;
                }
            }
            if (cellCount == 2 && emptyCellPosition != null) {
                return new GameInput(emptyCellPosition);
            }
        }

        // diagonals
        emptyCellPosition = null;
        cellCount = 0;
        for (int diagonalPos = 0; diagonalPos < 3; diagonalPos++) {
            Position position = new Position(diagonalPos, diagonalPos);
            if (this.grid.equalsSymbolAt(symbol, position)) {
                cellCount++;
            } else if (this.grid.isEmptyAt(position)) {
                emptyCellPosition = position;
            }
        }
        if (cellCount == 2 && emptyCellPosition != null) {
            return new GameInput(emptyCellPosition);
        }

        emptyCellPosition = null;
        cellCount = 0;
        for (int diagonalPos = 0; diagonalPos < 3; diagonalPos++) {
            Position position = new Position(diagonalPos, 2 - diagonalPos);
            if (this.grid.equalsSymbolAt(symbol, position)) {
                cellCount++;
            } else if (this.grid.isEmptyAt(position)) {
                emptyCellPosition = position;
            }
        }
        if (cellCount == 2 && emptyCellPosition != null) {
            return new GameInput(emptyCellPosition);
        }

        return null;
    }

    private GameInput getRandomMove() {
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
