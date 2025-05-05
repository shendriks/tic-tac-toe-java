package dev.shendriks.tictactoe.scenes.game.player;

import dev.shendriks.tictactoe.scenes.game.model.Grid;
import dev.shendriks.tictactoe.scenes.game.model.Symbol;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class PlayerFactoryTest {

    public static Stream<Arguments> providePlayerTypesAndPlayers() {
        return Stream.of(
                Arguments.of(PlayerType.USER, User.class, Symbol.X),
                Arguments.of(PlayerType.EASY, CpuEasy.class, Symbol.X),
                Arguments.of(PlayerType.MEDIUM, CpuMedium.class, Symbol.X),
                Arguments.of(PlayerType.HARD, CpuHard.class, Symbol.X),
                Arguments.of(PlayerType.USER, User.class, Symbol.O),
                Arguments.of(PlayerType.EASY, CpuEasy.class, Symbol.O),
                Arguments.of(PlayerType.MEDIUM, CpuMedium.class, Symbol.O),
                Arguments.of(PlayerType.HARD, CpuHard.class, Symbol.O)
        );
    }

    @ParameterizedTest
    @MethodSource("providePlayerTypesAndPlayers")
    void testCreatePlayer(PlayerType playerType, Class<Player> playerClass, Symbol symbol) {
        Grid grid = Grid.createEmpty();
        PlayerFactory playerFactory = new PlayerFactory(grid);

        Player player = playerFactory.createPlayer(playerType, symbol);

        assertInstanceOf(playerClass, player);
    }

    @Test
    void testCreatePlayerThrowsExceptionIfInvalidSymbolGiven() {
        Grid grid = Grid.createEmpty();
        PlayerFactory playerFactory = new PlayerFactory(grid);

        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> playerFactory.createPlayer(PlayerType.USER, Symbol.EMPTY)
        );
        assertEquals("Symbol must be X or O", exception.getMessage());
    }
}