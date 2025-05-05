package dev.shendriks.tictactoe.scenes.game.player;

import dev.shendriks.tictactoe.scenes.game.model.Grid;
import dev.shendriks.tictactoe.scenes.game.model.Symbol;

import java.util.Random;

public class PlayerFactory {
    private final Grid grid;

    public PlayerFactory(Grid grid) {
        this.grid = grid;
    }

    public Player createPlayer(PlayerType playerType, Symbol symbol) {
        if (symbol != Symbol.X && symbol != Symbol.O) {
            throw new IllegalArgumentException("Symbol must be X or O");
        }
        
        return switch (playerType) {
            case PlayerType.USER -> new User(System.in);
            case PlayerType.EASY -> new CpuEasy(this.grid, new Random());
            case PlayerType.MEDIUM -> new CpuMedium(this.grid, symbol, new Random());
            case PlayerType.HARD -> new CpuHard(this.grid, symbol);
        };
    }
}
 