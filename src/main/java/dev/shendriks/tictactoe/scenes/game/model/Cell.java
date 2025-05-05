package dev.shendriks.tictactoe.scenes.game.model;

public class Cell  {
    private Symbol symbol = Symbol.EMPTY;

    public Symbol getSymbol() {
        return symbol;
    }

    public void setSymbol(Symbol symbol) {
        this.symbol = symbol;
    }

    public boolean isEmpty() {
        return symbol.equals(Symbol.EMPTY);
    }
    
    public boolean isOccupied() {
        return !isEmpty();
    }
    
    public boolean isX() {
        return symbol.equals(Symbol.X);
    }
    
    public boolean isO() {
        return symbol.equals(Symbol.O);
    }

    @Override
    public String toString() {
        return String.valueOf(symbol.label);
    }
}
