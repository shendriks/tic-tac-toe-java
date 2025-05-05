package dev.shendriks.tictactoe.scenes.game.model;

import dev.shendriks.tictactoe.scenes.game.exception.CellOccupiedException;
import dev.shendriks.tictactoe.scenes.game.exception.GameAlreadyFinishedException;

import java.util.ArrayList;

public class Grid {
    private final Cell[][] cells = new Cell[3][3];
    private int countX = 0;
    private int countO = 0;

    private Grid() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                cells[i][j] = new Cell();
            }
        }
    }

    public static Grid createEmpty() {
        return new Grid();
    }
    
    public static Grid createFromString(String input) {
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
                        grid.countX++;
                        break;
                    case 'O':
                        grid.cells[i][j].setSymbol(Symbol.O);
                        grid.countO++;
                        break;
                    case '-':
                    case ' ':
                        grid.cells[i][j].setSymbol(Symbol.EMPTY);
                }
            }
        }
        return grid;
    }
    
    public boolean isEmptyAt(Position pos) {
        return cells[pos.row()][pos.col()].isEmpty();
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

    public void setCell(Position pos) throws GameAlreadyFinishedException, CellOccupiedException {
        if (isGameFinished()) {
            throw new GameAlreadyFinishedException();
        }

        int row = pos.row();
        int col = pos.col();

        if (cells[row][col].isOccupied()) {
            throw new CellOccupiedException();
        }

        if (isItXsTurn()) {
            cells[row][col].setSymbol(Symbol.X);
            countX++;
            return;
        }

        cells[row][col].setSymbol(Symbol.O);
        countO++;
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
        return countO == countX;
    }

    public boolean isFull() {
        return countX + countO >= 9;
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
        if (cells[0][0].isOccupied() && cells[0][0].getSymbol() == cells[1][1].getSymbol() && cells[1][1].getSymbol() == cells[2][2].getSymbol()) {
            return cells[0][0].isX() ? GameState.XWins : GameState.OWins;
        }
        if (cells[0][2].isOccupied() && cells[0][2].getSymbol() == cells[1][1].getSymbol() && cells[1][1].getSymbol() == cells[2][0].getSymbol()) {
            return cells[0][2].isX() ? GameState.XWins : GameState.OWins;
        }

        if (isFull()) {
            return GameState.Draw;
        }

        return GameState.GameNotFinished;
    }

    public void clearCell(Position pos) {
        int row = pos.row();
        int col = pos.col();

        if (cells[row][col].isX()) {
            countX--;
        } else if (cells[row][col].isO()) {
            countO--;
        }

        cells[row][col].setSymbol(Symbol.EMPTY);
    }

    public boolean isGameFinished() {
        GameState state = getGameState();
        return state.equals(GameState.Draw) || state.equals(GameState.XWins) || state.equals(GameState.OWins);
    }

    public boolean isOccupiedAt(Position position) {
        return cells[position.row()][position.col()].isOccupied();
    }

    public Symbol getSymbol(Position position) {
        return cells[position.row()][position.col()].getSymbol();
    }
}
