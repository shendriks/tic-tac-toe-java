package dev.shendriks.tictactoe.scenes.game.model;

public record GameInput(Position position) {
    public static GameInput from(int row, int col) {
        return new GameInput(new Position(row, col));
    }
}
