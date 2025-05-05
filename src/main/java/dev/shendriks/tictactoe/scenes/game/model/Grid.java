package dev.shendriks.tictactoe.scenes.game.model;

import dev.shendriks.tictactoe.scenes.game.exception.CellOccupiedException;
import dev.shendriks.tictactoe.scenes.game.exception.GameAlreadyFinishedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grid {
    private final Cell[][] cells = new Cell[3][3];
    private final Map<Symbol, Integer> symbolCounts = new HashMap<>(2);
    private final List<Map<Symbol, List<Position>>> symbolPositions = new ArrayList<>(8);
    private boolean areSymbolPositionsDirty = true;

    private Grid() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cells[i][j] = new Cell();
            }
        }

        symbolCounts.put(Symbol.X, 0);
        symbolCounts.put(Symbol.O, 0);

        for (int i = 0; i < 8; i++) {
            HashMap<Symbol, List<Position>> positions = new HashMap<>();
            positions.put(Symbol.X, new ArrayList<>());
            positions.put(Symbol.O, new ArrayList<>());
            positions.put(Symbol.EMPTY, new ArrayList<>());
            symbolPositions.add(positions);
        }
    }

    public static Grid createEmpty() {
        return new Grid();
    }

    public static Grid createFromString(String input) throws IllegalArgumentException {
        if (input.length() != 9) {
            throw new IllegalArgumentException("Input must be 9 characters");
        }

        Grid grid = new Grid();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                char c = input.charAt(i * 3 + j);
                switch (c) {
                    case 'X':
                        grid.cells[i][j].setSymbol(Symbol.X);
                        grid.symbolCounts.put(Symbol.X, grid.symbolCounts.get(Symbol.X) + 1);
                        break;
                    case 'O':
                        grid.cells[i][j].setSymbol(Symbol.O);
                        grid.symbolCounts.put(Symbol.O, grid.symbolCounts.get(Symbol.O) + 1);
                        break;
                    case '-':
                    case ' ':
                        grid.cells[i][j].setSymbol(Symbol.EMPTY);
                }
            }
        }
        return grid;
    }

    public void setCell(Position pos) throws GameAlreadyFinishedException, CellOccupiedException {
        if (isGameFinished()) {
            throw new GameAlreadyFinishedException();
        }

        int row = pos.row();
        int col = pos.col();

        if (cells[row][col].isOccupied()) {
            throw new CellOccupiedException();
        }

        Symbol symbol = isItXsTurn() ? Symbol.X : Symbol.O;
        cells[row][col].setSymbol(symbol);
        symbolCounts.put(symbol, symbolCounts.get(symbol) + 1);
        areSymbolPositionsDirty = true;
    }

    public void clearCell(Position pos) {
        int row = pos.row();
        int col = pos.col();

        Symbol symbol = cells[row][col].getSymbol();
        symbolCounts.put(symbol, symbolCounts.get(symbol) - 1);
        cells[row][col].setSymbol(Symbol.EMPTY);
        areSymbolPositionsDirty = true;
    }

    public boolean isEmptyAt(Position pos) {
        return cells[pos.row()][pos.col()].isEmpty();
    }

    public boolean isFull() {
        return symbolCounts.get(Symbol.X) + symbolCounts.get(Symbol.O) >= 9;
    }

    public boolean isGameFinished() {
        GameState state = getGameState();
        return state.equals(GameState.Draw) || state.equals(GameState.XWins) || state.equals(GameState.OWins);
    }

    public boolean isOccupiedAt(Position position) {
        return cells[position.row()][position.col()].isOccupied();
    }

    public boolean equalsSymbolAt(Symbol symbol, Position pos) {
        return cells[pos.row()][pos.col()].getSymbol().equals(symbol);
    }

    public ArrayList<Position> getEmptyPositions() {
        ArrayList<Position> emptyPositions = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (this.cells[i][j].isEmpty()) {
                    emptyPositions.add(new Position(i, j));
                }
            }
        }
        return emptyPositions;
    }

    public Symbol getSymbol(Position position) {
        return cells[position.row()][position.col()].getSymbol();
    }

    public List<Map<Symbol, List<Position>>> getSymbolPositions() {
        if (areSymbolPositionsDirty) {
            calculateSymbolPositions();
            areSymbolPositionsDirty = false;
        }

        return symbolPositions;
    }

    public GameState getGameState() {
        for (Map<Symbol, List<Position>> line : getSymbolPositions()) {
            if (line.get(Symbol.X).size() == 3) {
                return GameState.XWins;
            }

            if (line.get(Symbol.O).size() == 3) {
                return GameState.OWins;
            }
        }

        if (isFull()) {
            return GameState.Draw;
        }

        return GameState.GameNotFinished;
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
                Symbol symbol = getSymbol(new Position(row, col));

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

    private boolean isItXsTurn() {
        return symbolCounts.get(Symbol.X).equals(symbolCounts.get(Symbol.O));
    }
}
