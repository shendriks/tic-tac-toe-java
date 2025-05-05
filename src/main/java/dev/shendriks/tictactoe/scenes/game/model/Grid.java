package dev.shendriks.tictactoe.scenes.game.model;

import dev.shendriks.tictactoe.scenes.game.exception.CellOccupiedException;
import dev.shendriks.tictactoe.scenes.game.exception.GameAlreadyFinishedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Grid {
    private final Cell[][] cells = new Cell[3][3];
    private final Map<Symbol, Integer> symbolCounts = new HashMap<>(2);

    private Grid() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cells[i][j] = new Cell();
            }
        }

        symbolCounts.put(Symbol.X, 0);
        symbolCounts.put(Symbol.O, 0);
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

        Symbol symbol = whoseTurnIsIt();
        if (symbol == null) {
            throw new GameAlreadyFinishedException();
        }

        int row = pos.row();
        int col = pos.col();

        if (cells[row][col].isOccupied()) {
            throw new CellOccupiedException();
        }

        cells[row][col].setSymbol(symbol);
        symbolCounts.put(symbol, symbolCounts.get(symbol) + 1);
    }

    public void clearCell(Position pos) {
        int row = pos.row();
        int col = pos.col();

        Symbol symbol = cells[row][col].getSymbol();
        symbolCounts.put(symbol, symbolCounts.get(symbol) - 1);
        cells[row][col].setSymbol(Symbol.EMPTY);
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

    public GameState getGameState() {
        // check rows
        for (int i = 0; i < 3; i++) {
            if (cells[i][0].isEmpty()) {
                continue;
            }
            if (cells[i][0].isX() && cells[i][1].isX() && cells[i][2].isX()) {
                return GameState.XWins;
            }
            if (cells[i][0].isO() && cells[i][1].isO() && cells[i][2].isO()) {
                return GameState.OWins;
            }
        }

        // check cols
        for (int i = 0; i < 3; i++) {
            if (cells[0][i].isEmpty()) {
                continue;
            }
            if (cells[0][i].isX() && cells[1][i].isX() && cells[2][i].isX()) {
                return GameState.XWins;
            }
            if (cells[0][i].isO() && cells[1][i].isO() && cells[2][i].isO()) {
                return GameState.OWins;
            }
        }

        // check diagonals
        if (cells[0][0].isX() && cells[1][1].isX() && cells[2][2].isX()) {
            return GameState.XWins;
        }
        if (cells[0][0].isO() && cells[1][1].isO() && cells[2][2].isO()) {
            return GameState.OWins;
        }
        if (cells[0][2].isX() && cells[1][1].isX() && cells[2][0].isX()) {
            return GameState.XWins;
        }
        if (cells[0][2].isO() && cells[1][1].isO() && cells[2][0].isO()) {
            return GameState.OWins;
        }
        
        if (isFull()) {
            return GameState.Draw;
        }

        return GameState.GameNotFinished;
    }

    public Symbol whoseTurnIsIt() {
        if (isGameFinished()) {
            return null;
        }

        if (isItXsTurn()) {
            return Symbol.X;
        }

        return Symbol.O;
    }

    private boolean isItXsTurn() {
        return symbolCounts.get(Symbol.X).equals(symbolCounts.get(Symbol.O));
    }
}
