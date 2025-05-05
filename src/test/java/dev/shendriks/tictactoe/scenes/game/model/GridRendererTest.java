package dev.shendriks.tictactoe.scenes.game.model;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class GridRendererTest {

    @Test
    void testRender() {
        OutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        Grid grid = Grid.createFromString("X-XOO-OXX");
        GridRenderer gridRenderer = new GridRenderer(grid, printStream);

        gridRenderer.render();

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        printWriter.println("+-------+");
        printWriter.println("| X   X |");
        printWriter.println("| O O   |");
        printWriter.println("| O X X |");
        printWriter.println("+-------+");
        printWriter.close();
        String expectedOutput = stringWriter.toString();
        
        assertEquals(expectedOutput, outputStream.toString());
    }
}