package dev.shendriks.tictactoe.scenes.game.model;

public enum Symbol {
    EMPTY(' '),
    X('X'),
    O('O');

    public final char label;

    Symbol(char label) {
        this.label = label;
    }
}
