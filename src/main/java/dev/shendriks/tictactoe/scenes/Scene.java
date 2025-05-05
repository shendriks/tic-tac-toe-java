package dev.shendriks.tictactoe.scenes;

import java.util.ArrayList;

public abstract class Scene implements Updateable {
    private final ArrayList<SceneSwitchHandler> switchHandler = new ArrayList<>();
    private final ArrayList<GameExitHandler> exitHandlers = new ArrayList<>();

    public abstract void update();

    protected void switchToScene(Scene newScene)
    {
        for (SceneSwitchHandler handler : switchHandler) {
            handler.handle(newScene);
        }
    }

    protected void exitGame() {
        for (GameExitHandler handler : exitHandlers) {
            handler.handleExit();
        }
    }

    public void addSwitchHandler(SceneSwitchHandler switchHandler) {
        this.switchHandler.add(switchHandler);
    }

    public void removeSwitchHandler(SceneSwitchHandler switchHandler) {
        this.switchHandler.remove(switchHandler);
    }

    public void addExitHandler(GameExitHandler exitHandler) {
        this.exitHandlers.add(exitHandler);
    }

    public void removeExitHandler(GameExitHandler exitHandler) {
        this.exitHandlers.remove(exitHandler);
    }
}
