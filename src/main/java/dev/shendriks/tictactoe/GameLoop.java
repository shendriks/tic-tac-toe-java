package dev.shendriks.tictactoe;

import dev.shendriks.tictactoe.scenes.menu.Menu;
import dev.shendriks.tictactoe.scenes.Scene;

public class GameLoop {
    private Scene currentScene = new Menu();
    
    public void run() {
        currentScene.addSwitchHandler(this::switchToScene);

        //noinspection InfiniteLoopStatement
        while (true) {
            currentScene.update();
        }
    }

    private void switchToScene(Scene newScene) {
        currentScene.removeSwitchHandler(this::switchToScene);
        currentScene = newScene;
        currentScene.addSwitchHandler(this::switchToScene);
    }
}
