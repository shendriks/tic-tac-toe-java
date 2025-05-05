package dev.shendriks.tictactoe.scenes;

import java.util.ArrayList;

public abstract class Scene implements Updateable {
    private final ArrayList<SceneSwitchHandler> switchHandler = new ArrayList<>();
    
    public abstract void update();

    protected void switchToScene(Scene newScene)
    {
        for (SceneSwitchHandler handler : switchHandler) {
            handler.handle(newScene);
        }
    }

    public void addSwitchHandler(SceneSwitchHandler switchHandler) {
        this.switchHandler.add(switchHandler);
    }
    
    public void removeSwitchHandler(SceneSwitchHandler switchHandler) {
        this.switchHandler.remove(switchHandler);
    }
}
