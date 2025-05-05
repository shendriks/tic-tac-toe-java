package dev.shendriks.tictactoe.scenes;

@FunctionalInterface
public interface SceneSwitchHandler {
    void handle(Scene newScene);
}
