package dev.shendriks.tictactoe;

import dev.shendriks.tictactoe.scenes.Scene;
import dev.shendriks.tictactoe.scenes.menu.Menu;

public class GameLoop {
    private Scene currentScene = new Menu();
    private boolean isRunning = true;

    public void run() {
        currentScene.addSwitchHandler(this::switchToScene);
        currentScene.addExitHandler(this::handleExit);

        while (isRunning) {
            currentScene.update();
        }
    }

    private void switchToScene(Scene newScene) {
        currentScene.removeSwitchHandler(this::switchToScene);
        currentScene.removeExitHandler(this::handleExit);
        currentScene = newScene;
        currentScene.addSwitchHandler(this::switchToScene);
        currentScene.addExitHandler(this::handleExit);
    }

    private void handleExit() {
        isRunning = false;
    }
}
