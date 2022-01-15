package com.sochris.engine2d.renderer;

import java.awt.event.KeyEvent;

import com.sochris.engine2d.listener.KeyListener;

public class LevelEditorScene extends Scene {

    private boolean changingScene = false;
    private float timeToChangeScene = 2.0f;

    public LevelEditorScene() {
        System.out.println("In Level Editor");
    }

    @Override
    public void update(float dt) {

        if(!changingScene && KeyListener.isKeyPressed(KeyEvent.VK_SPACE)){
            changingScene = true;
        }

        if(changingScene && timeToChangeScene > 0){
            timeToChangeScene -= dt; 
            Window.get().r -= dt * 5.0f;
            Window.get().g -= dt * 5.0f;
            Window.get().b -= dt * 5.0f;
        }else if(changingScene){
            Window.changeScene(1);
        }
        
    }
    
}
