package com.sochris.engine2d.renderer.scene;

import com.sochris.engine2d.renderer.camera.Camera;

public abstract class Scene {

    protected Camera camera;

    public Scene() {

    }

    public void init(){
        
    }

    public abstract void update(float dt);
    
}
