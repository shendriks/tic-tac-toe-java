package dev.shendriks.tictactoe.scenes.game;

import dev.shendriks.tictactoe.scenes.game.player.PlayerType;
import org.junit.jupiter.api.Test;

class GameTest {

    @Test
    void testUpdate() {
        Game game = new Game(PlayerType.EASY, PlayerType.EASY);
        
        game.update();
    }
}