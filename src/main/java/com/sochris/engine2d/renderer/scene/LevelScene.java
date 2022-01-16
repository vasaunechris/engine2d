package com.sochris.engine2d.renderer.scene;

import com.sochris.engine2d.renderer.Window;

public class LevelScene extends Scene {

    public LevelScene() {
        System.out.println("In Level Scene");
        Window.get().r = 1.0f;
        Window.get().g = 1.0f;
        Window.get().b = 1.0f;
    }

    @Override
    public void update(float dt) {
        
    }
    
}
