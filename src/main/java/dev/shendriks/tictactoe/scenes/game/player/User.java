package dev.shendriks.tictactoe.scenes.game.player;

import dev.shendriks.tictactoe.scenes.game.model.GameInput;
import dev.shendriks.tictactoe.scenes.game.exception.OutOfGridException;
import dev.shendriks.tictactoe.scenes.game.model.Position;

import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.Scanner;

public class User implements Player {
    private final Scanner scanner;

    public User(InputStream inputStream) {
        scanner = new Scanner(inputStream);
    }

    @Override
    public GameInput readInput() {
        boolean done = false;
        int row = -1;
        int col = -1;

        do {
            System.out.print("Enter the coordinates [row column]: ");
            try {
                row = scanner.nextInt() - 1;
                col = scanner.nextInt() - 1;

                if (row < 0 || row > 2 || col < 0 || col > 2) {
                    throw new OutOfGridException();
                }

                done = true;
            } catch (InputMismatchException exception) {
                System.out.println("You should enter numbers!");
            } catch (OutOfGridException exception) {
                System.out.println("Coordinates should be from 1 to 3!");
            } finally {
                scanner.nextLine();
            }
        } while (!done);

        return new GameInput(new Position(row, col));
    }
}
