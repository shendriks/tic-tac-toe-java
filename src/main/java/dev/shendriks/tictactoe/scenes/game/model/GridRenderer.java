package dev.shendriks.tictactoe.scenes.game.model;

import java.io.PrintStream;

public class GridRenderer {
    private final Grid grid;
    private final PrintStream printStream;

    public GridRenderer(Grid grid, PrintStream printStream) {
        this.grid = grid;
        this.printStream = printStream;
    }
    
    public void render() {
        printStream.println("+-------+");
        for (int i = 0; i < 3; i++) {
            printStream.print("| ");
            for (int j = 0; j < 3; j++) {
                printStream.printf("%s ", grid.getSymbol(new Position(i, j)).label);
            }
            printStream.println("|");
        }
        printStream.println("+-------+");
    }
}
