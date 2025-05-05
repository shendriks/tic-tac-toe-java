package dev.shendriks.tictactoe.scenes.game;

import dev.shendriks.tictactoe.scenes.Scene;
import dev.shendriks.tictactoe.scenes.game.exception.CellOccupiedException;
import dev.shendriks.tictactoe.scenes.game.model.*;
import dev.shendriks.tictactoe.scenes.game.player.Player;
import dev.shendriks.tictactoe.scenes.game.player.PlayerFactory;
import dev.shendriks.tictactoe.scenes.menu.Menu;
import dev.shendriks.tictactoe.scenes.game.player.PlayerType;

public class Game extends Scene {
    private final Grid grid;
    private final GridRenderer gridRenderer;
    private final Player player1;
    private final Player player2;

    public Game(PlayerType player1Type, PlayerType player2Type) {
        this.grid = Grid.createEmpty();
        this.gridRenderer = new GridRenderer(grid, System.out);
        
        PlayerFactory playerFactory = new PlayerFactory(this.grid);

        this.player1 = playerFactory.createPlayer(player1Type, Symbol.X);
        this.player2 = playerFactory.createPlayer(player2Type, Symbol.O);

        gridRenderer.render();
    }

    @Override
    public void update() {
        doMove(player1);
        gridRenderer.render();
        checkGameState();
        
        if (grid.isGameFinished()) {
            return;
        }
        
        doMove(player2);
        gridRenderer.render();
        checkGameState();
    }

    private void doMove(Player player) {
        boolean done = false;
        while (!done) {
            try {
                GameInput input = player.readInput();
                grid.setCell(input.position());
                done = true;
            } catch (CellOccupiedException exception) {
                System.out.println("This cell is occupied! Choose another one!");
            }
        }
    }

    private void checkGameState() {
        switch (grid.getGameState()) {
            case GameState.XWins:
                System.out.println("X wins!\n");
                switchToMenu();
                break;
            case GameState.OWins:
                System.out.println("O wins!\n");
                switchToMenu();
                break;
            case GameState.Draw:
                System.out.println("Draw!\n");
                switchToMenu();
                break;
        }
    }

    private void switchToMenu() {
        this.switchToScene(new Menu());
    }
}


