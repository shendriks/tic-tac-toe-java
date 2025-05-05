package dev.shendriks.tictactoe.scenes.menu;

import dev.shendriks.tictactoe.scenes.Scene;
import dev.shendriks.tictactoe.scenes.game.Game;
import dev.shendriks.tictactoe.scenes.game.player.PlayerType;

import java.util.Scanner;

public class Menu extends Scene {
    private final Scanner input = new Scanner(System.in);

    @Override
    public void update() {
        boolean done = false;
        do {
            System.out.print("Input command (type help for help): ");

            String command = input.nextLine();
            String[] commandParts = command.split("\\s+");

            switch (commandParts[0]) {
                case "help":
                    printHelp();
                    break;
                case "start":
                    if (commandParts.length < 3) {
                        System.out.println("I need 2 player types!");
                        break;
                    }

                    try {
                        PlayerType player1Type = PlayerType.valueOf(commandParts[1].toUpperCase());
                        PlayerType player2Type = PlayerType.valueOf(commandParts[2].toUpperCase());
                        Game game = new Game(player1Type, player2Type);
                        super.switchToScene(game);
                        done = true;
                    } catch (IllegalArgumentException e) {
                        System.out.println("Unknown player type! Allowed types are: user, easy, medium, hard");
                    }

                    break;
                case "exit":
                    done = true;
                    System.out.println("Goodbye!");
                    exitGame();
                    break;
                default:
                    System.out.println("Unknown command! Allowed commands are: help, start, exit");
                    break;
            }
        } while (!done);
    }

    private void printHelp() {
        System.out.println("Available commands:");
        System.out.println("help - print this help");
        System.out.println("start <player_1> <player_2> - start game with <player_n> being one of: user, easy, medium, hard; <player_1> starts");
        System.out.println("exit - exit the game");
    }
}
